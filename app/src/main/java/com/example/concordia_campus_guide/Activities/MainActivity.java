package com.example.concordia_campus_guide.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Fragments.InfoCardFragment.InfoCardFragment;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragment;
import com.example.concordia_campus_guide.Fragments.POIFragment.POIFragment;
import com.example.concordia_campus_guide.Fragments.SmallInfoCardFragment.SmallInfoCardFragment;
import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.CurrentLocation;
import com.example.concordia_campus_guide.Helper.Notification;
import com.example.concordia_campus_guide.Helper.StartActivityHelper;
import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.Models.Floors;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Rooms;
import com.example.concordia_campus_guide.Models.Shuttles;
import com.example.concordia_campus_guide.Models.WalkingPoints;
import com.example.concordia_campus_guide.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    InfoCardFragment infoCardFragment;
    SmallInfoCardFragment smallInfoCardFragment;
    LocationFragment locationFragment;
    POIFragment poiFragment;
    MainActivityViewModel mViewModel;
    private DrawerLayout drawer;
    private BottomSheetBehavior swipeableInfoCard;
    private BottomSheetBehavior swipeablePOICard;
    private Notification notification;
    Toolbar myToolbar;

    private CurrentLocation currentLocation;

    // Side Menu Toggle Buttons
    private CompoundButton staffToggle;
    private CompoundButton accessibilityToggle;
    private CompoundButton translationToggle;
    private HashMap<CompoundButton, String> toggleButtonAndCorrespondingToggleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDb();
        setContentView(R.layout.activity_main);
        mViewModel = new MainActivityViewModel();

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        for (CompoundButton toggleButton : toggleButtonAndCorrespondingToggleType.keySet()) {
            String value = sharedPreferences.getString(toggleButtonAndCorrespondingToggleType.get(toggleButton), ClassConstants.FALSE);
            toggleButton.setChecked(value.equals(ClassConstants.TRUE));
        }

        // TODO: US #50: add logic for calendar integration  by retrieving from the shared preferences if the user hclicked on the "calendar integration" button or not.
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        for (CompoundButton toggleButton : toggleButtonAndCorrespondingToggleType.keySet()) {
            String value = toggleButton.isChecked() ? ClassConstants.TRUE : ClassConstants.FALSE;
            String toggleType = toggleButtonAndCorrespondingToggleType.get(toggleButton);
            myEdit.putString(toggleType, value);
        }

        myEdit.commit();
    }

    private void initComponents() {
        MainActivity.this.setTitle("ConUMaps");

        currentLocation = new CurrentLocation(this);
        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.locationFragment);

        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        fragmentManager = getSupportFragmentManager();

        setupNotifications();
        setupSwipeableCards();
        setupToolBar();
        setUpToggleButtonsMap();
    }

    private void setUpToggleButtonsMap() {
        toggleButtonAndCorrespondingToggleType = new HashMap();
        toggleButtonAndCorrespondingToggleType.put(accessibilityToggle, ClassConstants.ACCESSIBILITY_TOGGLE);
        toggleButtonAndCorrespondingToggleType.put(staffToggle, ClassConstants.STAFF_TOGGLE);
        toggleButtonAndCorrespondingToggleType.put(translationToggle, ClassConstants.TRANSLATION_TOGGLE);
    }

    private void setupNotifications() {
        notification = new Notification(this, AppDatabase.getInstance(this));
        notification.checkUpCalendarEvery5Minutes();
    }

    private void setupSwipeableCards() {
        View infoCard = findViewById(R.id.bottom_card_scroll_view);
        View poiCard = findViewById(R.id.explore_bottom_card_scroll_view);
        swipeableInfoCard = BottomSheetBehavior.from(infoCard);
        swipeablePOICard = BottomSheetBehavior.from(poiCard);
        showPOICard();
    }

    private void setupToolBar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(myToolbar);
        setupDrawerToggle();
    }

    private void setupDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.whiteBackgroundColor));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupSideMenuItemListeners();

    }

    private void setupSideMenuItemListeners() {
        NavigationView navigationView = findViewById(R.id.side_nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            setupSwitchesListener(navigationView);
        }
    }

    private void setupSwitchesListener(NavigationView navigationView) {
        MenuItem translateItem = navigationView.getMenu().findItem(R.id.nav_translate);
        MenuItem staffItem = navigationView.getMenu().findItem(R.id.nav_staff);
        MenuItem accessibilityItem = navigationView.getMenu().findItem(R.id.nav_accessibility);

        translationToggle = (CompoundButton) translateItem.getActionView();
        staffToggle = (CompoundButton) staffItem.getActionView();
        accessibilityToggle = (CompoundButton) accessibilityItem.getActionView();

        setupOnChangeListenerForSwitch(staffToggle);
        setupOnChangeListenerForSwitch(translationToggle);
        setupOnChangeListenerForSwitch(accessibilityToggle);
    }

    private void setupOnChangeListenerForSwitch(CompoundButton switchView) {
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO: US #158 and #160, add action when changed
                Toast.makeText(MainActivity.this, isChecked + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void popUp(CalendarEvent calendarEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        Date eventDate = new Date((Long.parseLong(calendarEvent.getStartTime())));
        long differenceInMillis = eventDate.getTime() - System.currentTimeMillis();
        if (notification.validateCalendarEvent(calendarEvent) || !notification.roomExistsInDb(calendarEvent.getLocation())) {
            builder.setTitle("Location Format is Wrong");
            builder.setMessage("The location of your upcoming event hasn't been inserted correctly" +
                    "(e.g. 'H-9, 963')");
        } else if (differenceInMillis > 0) {
            builder.setTitle("Heads up... Your next class is in " + mViewModel.displayTimeToNextClass(calendarEvent.getStartTime()));
            builder.setMessage("You have " + calendarEvent.getTitle() + " in " + mViewModel.displayTimeToNextClass(calendarEvent.getStartTime()) + " at " +
                    calendarEvent.getLocation() + " ! Please choose to either get directions to your class or to ignore this message");
            setupShowMeDirectionsBtn(builder, calendarEvent.getLocation());
        } else {
            String eventPassedBy = mViewModel.displayTimeToNextClass(calendarEvent.getStartTime());
            eventPassedBy = eventPassedBy.replace('-', ' ');
            builder.setTitle("Heads up... Your class has already started before " + eventPassedBy);
            builder.setMessage("Your " + calendarEvent.getTitle() + " has started before " + eventPassedBy + " at " +
                    calendarEvent.getLocation() + " ! Please choose to either get directions to your class or to ignore this message");
            setupShowMeDirectionsBtn(builder, calendarEvent.getLocation());
        }

        setupCancelBtn(builder);

        builder.show();
    }

    private void setupCancelBtn(AlertDialog.Builder builder) {
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void setupShowMeDirectionsBtn(AlertDialog.Builder builder, String location) {
        final String locationTemp = location;
        final Activity mainActivity = this;
        builder.setPositiveButton("Show me Directions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RoomModel room = notification.getRoom(locationTemp);
                StartActivityHelper.openRoutesPage(room, mainActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent openSearch = new Intent(MainActivity.this,
                    SearchActivity.class);

            SelectingToFromState.setMyCurrentLocation(getMyCurrentLocation());

            startActivity(openSearch);
            return false;
        }

        return true;
    }

    /**
     * Show the info card fragment in the view
     *
     * @param buildingCode: the Building code
     */
    public void showInfoCard(String buildingCode) {
        resetBottomCard();
        infoCardFragment = new InfoCardFragment(buildingCode);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.bottom_card_frame, infoCardFragment);
        fragmentTransaction.commit();

        (findViewById(R.id.bottom_card_frame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swipeableInfoCard.getState() != BottomSheetBehavior.STATE_EXPANDED)
                    swipeableInfoCard.setState(BottomSheetBehavior.STATE_EXPANDED);
                else
                    swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    public void showPlaceSmallCard(RoomModel room){
        resetBottomCard();
        smallInfoCardFragment = new SmallInfoCardFragment(room);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.bottom_card_frame, smallInfoCardFragment);
        fragmentTransaction.commit();
    }

    public void showPOICard() {
        resetBottomCard();
        poiFragment = new POIFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.explore_bottom_card_frame, poiFragment);
        fragmentTransaction.commit();


        (findViewById(R.id.explore_bottom_card_frame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swipeablePOICard.getState() != BottomSheetBehavior.STATE_EXPANDED)
                    swipeablePOICard.setState(BottomSheetBehavior.STATE_EXPANDED);
                else
                    swipeablePOICard.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    public void resetBottomCard(){
        for (Fragment fragment : fragmentManager.getFragments()){
            if (fragment instanceof InfoCardFragment || fragment instanceof SmallInfoCardFragment) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
        swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    /**
     * Defines the desired behavior on backpress
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = fragmentManager.findFragmentById(R.id.bottom_card_frame);
            if (fragment != null) {
                showPOICard();
            } else {
                super.onBackPressed();
            }
        }

        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.locationFragment);
        locationFragment.deselectAll();
    }

    private void setUpDb() {
        if (!ApplicationState.getInstance(this).isDbIsSet()) {
            //delete previous db
            getApplication().getApplicationContext().deleteDatabase(AppDatabase.DB_NAME);

            //load buildings
            Buildings buildings = ApplicationState.getInstance(this).getBuildings();
            AppDatabase appDb = AppDatabase.getInstance(this);
            appDb.buildingDao().insertAll(buildings.getBuildings());

            //load floors
            Floors floors = ApplicationState.getInstance(this).getFloors();
            appDb.floorDao().insertAll(floors.getFloors());

            //load rooms
            Rooms rooms = ApplicationState.getInstance(this).getRooms();
            appDb.roomDao().insertAll(rooms.getRooms());

            // Load shuttle schedule
            Shuttles shuttles = ApplicationState.getInstance(this).getShuttles();
            appDb.shuttleDao().insertAll(shuttles.getShuttles());

            // Load walking points
            WalkingPoints walkingPoints = ApplicationState.getInstance(this).getWalkingPoints();
            appDb.walkingPointDao().insertAll(walkingPoints.getWalkingPoints());

            ApplicationState.getInstance(this).setDbIsSetToTrue();
        }
    }

    public Location getMyCurrentLocation() {
        return currentLocation.getCurrentLocation();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        if (itemId == R.id.nav_calendar) {
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            String value = item.isEnabled() ? ClassConstants.TRUE : ClassConstants.FALSE;
            myEdit.putString(ClassConstants.CALENDAR_INTEGRATION_BUTTON, value);

            myEdit.commit();
        }
        return false;
    }
}
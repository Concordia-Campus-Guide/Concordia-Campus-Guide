package com.example.concordia_campus_guide.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.fragments.InfoCardFragment;
import com.example.concordia_campus_guide.fragments.LocationFragment;
import com.example.concordia_campus_guide.fragments.POIFragment;
import com.example.concordia_campus_guide.fragments.SmallInfoCardFragment;
import com.example.concordia_campus_guide.global.ApplicationState;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.helper.CurrentLocation;
import com.example.concordia_campus_guide.helper.LocaleHelper;
import com.example.concordia_campus_guide.helper.Notification;
import com.example.concordia_campus_guide.models.CalendarEvent;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.helpers.CalendarViewModel;
import com.example.concordia_campus_guide.view_models.MainActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.concordia_campus_guide.helper.StartActivityHelper.openRoutesPage;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AppDatabase appDb;
    ApplicationState applicationState;
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
    private CalendarViewModel calendarViewModel;
    // Side Menu Toggle Buttons
    private CompoundButton staffToggle;
    private CompoundButton accessibilityToggle;
    private CompoundButton translationToggle;
    private HashMap<CompoundButton, String> toggleButtonAndCorrespondingToggleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationState = ApplicationState.getInstance(this);
        appDb = AppDatabase.getInstance(this);
        setUpDb();

        setContentView(R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        initComponents();
        acceptOpenFromCalendarIntent();
    }

    private void acceptOpenFromCalendarIntent() {
        Intent intent = getIntent();
        try{
            Uri data = intent.getData();
            String roomCode = data.getQueryParameter("room");
            String floorCode = data.getQueryParameter("floor");
            RoomModel room = appDb.roomDao().getRoomByRoomCodeAndFloorCode(roomCode, floorCode);

            if(room!=null){
                SelectingToFromState.setMyCurrentLocation(currentLocation.getMyLocation());
 //               openRoutesPage(room, this);
            }
        }
        catch(Exception e){
            //do nothing
            //the application was opened without the calendar link
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        for (Map.Entry<CompoundButton, String> entry : toggleButtonAndCorrespondingToggleType.entrySet()) {
            boolean value = sharedPreferences.getBoolean(entry.getValue(), false);
            entry.getKey().setChecked(value);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        for (Map.Entry<CompoundButton, String> entry: toggleButtonAndCorrespondingToggleType.entrySet()) {
            boolean value = entry.getKey().isChecked();
            String toggleType = entry.getValue();
            myEdit.putBoolean(toggleType, value);
        }

        myEdit.commit();
    }

    private void initComponents() {
        MainActivity.this.setTitle("ConUMaps");

        currentLocation = new CurrentLocation(this);
        locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentById(R.id.locationFragment);
        calendarViewModel = new CalendarViewModel(getApplication());
        fragmentManager = getSupportFragmentManager();
        currentLocation.updateLocationEvery5Seconds();
        SelectingToFromState.setMyCurrentLocation(currentLocation.getMyLocation());
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
        notification = new Notification(this, appDb);
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
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.whiteBackgroundColor));
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
        SharedPreferences sharedPreferences = getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switch(buttonView.getId()){
                case R.id.nav_translate:
                    switchLanguage(isChecked);
                    break;
                case R.id.nav_accessibility:
                    sharedPreferences.edit().putBoolean(ClassConstants.ACCESSIBILITY_TOGGLE, isChecked).commit();
                    break;
                case R.id.nav_staff:
                    sharedPreferences.edit().putBoolean(ClassConstants.STAFF_TOGGLE, isChecked).commit();
                    break;            }
        });
    }

    private void switchLanguage(boolean isChecked) {
        Locale current = getBaseContext().getResources().getConfiguration().getLocales().get(0);
        Configuration newConfig = new Configuration();
        newConfig.locale = isChecked? Locale.FRENCH : Locale.ENGLISH;
        // this check is required to ensure that we recreate only if the language set is different
        // otherwise it enters an infinite loop
        if(!current.equals(newConfig.locale)){
            LocaleHelper.setLocale(this, newConfig.locale.toString());
            recreate();
        }
    }

    public void popUp(CalendarEvent calendarEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        Date eventDate = new Date((Long.parseLong(calendarEvent.getStartTime())));
        long differenceInMillis = eventDate.getTime() - System.currentTimeMillis();
        if(notification.validateCalendarEvent(calendarEvent) || !notification.roomExistsInDb(calendarEvent.getLocation())) {
            builder.setTitle(getResources().getString(R.string.location_format_wrong));
            builder.setMessage(getResources().getString(R.string.location_format_wrong_description));
        }

        else if(differenceInMillis > 0 ){
            builder.setTitle(getResources().getString(R.string.calendar_text_header) +  mViewModel.displayTimeToNextClass(calendarEvent.getStartTime()));
            builder.setMessage(getResources().getString(R.string.you_have) + calendarEvent.getTitle() + getResources().getString(R.string.in) + mViewModel.displayTimeToNextClass(calendarEvent.getStartTime())  + getResources().getString(R.string.at) +
                    calendarEvent.getLocation() + getResources().getString(R.string.calendar_text_end));
            setupShowMeDirectionsBtn(builder, calendarEvent.getLocation());
        }
        else {
            String eventPassedBy = mViewModel.displayTimeToNextClass(calendarEvent.getStartTime());
            eventPassedBy = eventPassedBy.replace('-', ' ');
            builder.setTitle(getResources().getString(R.string.calendar_text_header_late) + eventPassedBy);
            builder.setMessage(getResources().getString(R.string.your) + calendarEvent.getTitle() + getResources().getString(R.string.already_started) + eventPassedBy + getResources().getString(R.string.at) +
                    calendarEvent.getLocation() + getResources().getString(R.string.calendar_text_end));
            setupShowMeDirectionsBtn(builder, calendarEvent.getLocation());
        }

        setupCancelBtn(builder);
        builder.show();
    }

    private void setupCancelBtn(AlertDialog.Builder builder) {
        builder.setNegativeButton(getResources().getString(R.string.ignore), (dialog, which) -> dialog.cancel());
    }

    private void setupShowMeDirectionsBtn(AlertDialog.Builder builder, String location) {
        final String locationTemp = location;
        final Activity mainActivity = this;
        builder.setPositiveButton(getResources().getString(R.string.show_me_direction), (dialog, which) -> {
            SelectingToFromState.setMyCurrentLocation(currentLocation.getMyLocation());
            RoomModel room = notification.getRoom(locationTemp);
            openRoutesPage(room, mainActivity);
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
            SelectingToFromState.reset();
            Intent openSearch = new Intent(MainActivity.this, SearchActivity.class);
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

        (findViewById(R.id.bottom_card_frame)).setOnClickListener(view -> {
            if (swipeableInfoCard.getState() != BottomSheetBehavior.STATE_EXPANDED)
                swipeableInfoCard.setState(BottomSheetBehavior.STATE_EXPANDED);
            else
                swipeableInfoCard.setState(BottomSheetBehavior.STATE_COLLAPSED);
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


        (findViewById(R.id.explore_bottom_card_frame)).setOnClickListener(view -> {
            if (swipeablePOICard.getState() != BottomSheetBehavior.STATE_EXPANDED)
                swipeablePOICard.setState(BottomSheetBehavior.STATE_EXPANDED);
            else
                swipeablePOICard.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

        locationFragment.deselectAll();
    }

    private void setUpDb() {
        if (!applicationState.isDbIsSet()) {
            //delete previous db
            getApplication().getApplicationContext().deleteDatabase(AppDatabase.DB_NAME);
            applicationState.setUpDb(appDb);
        }
    }

    public Location getMyCurrentLocation() {
        return currentLocation.getMyLocation();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_calendar) {
            if(!calendarViewModel.hasReadPermission() && !calendarViewModel.hasWritePermission()){
                calendarViewModel.askForPermission(this);
            }
            else {
                Toast.makeText(MainActivity.this, "You have already given permission", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}

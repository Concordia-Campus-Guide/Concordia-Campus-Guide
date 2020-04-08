package com.example.concordia_campus_guide.Activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.Adapters.PlaceToSearchResultAdapter;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;
import com.example.concordia_campus_guide.Models.MyCurrentPlace;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.R;

public class SearchActivity extends AppCompatActivity {

    ListView searchResults;
    EditText searchText;
    View nextClassRow;
    View currentLocationRow;
    TextView nextClassText;
    ImageView nextClassArrow;
    SearchActivityViewModel mViewModel;
    CalendarViewModel calendarViewModel;

    private PlaceToSearchResultAdapter adapter;
    private Boolean shouldSetNextClassClickListener = false;
    private Place nextClassPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up activity
        setContentView(R.layout.search_activity);
        setBackButtonOnClick();
        initComponents();
        setPlaceToSearchResultAdapter();
        setEvents();
    }

    private void initComponents() {
        searchResults = findViewById(R.id.searchResults);
        searchText = findViewById(R.id.searchText);
        nextClassText = findViewById(R.id.next_class_text);
        nextClassArrow = findViewById(R.id.next_class_arrow);
        nextClassRow = findViewById(R.id.next_class_row_container);
        currentLocationRow = findViewById(R.id.current_location_row_container);
        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication())).get(SearchActivityViewModel.class);
        setupNextClassString();
    }

    private void setPlaceToSearchResultAdapter() {
        // Android adapter for list view
        adapter = new PlaceToSearchResultAdapter(this, R.layout.list_item_layout, mViewModel.getAllPlaces());
        searchResults.setAdapter(adapter);
    }

    private void setEvents() {
        setBackButtonOnClick();
        setTextListener();
        setOnClickListeners();
    }

    private void setTextListener() {
        //search results filtering according to search text
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setOnClickListeners() {
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean onFocus) {
                if (onFocus) {
                    searchResults.setVisibility(View.VISIBLE);
                }
            }
        });

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = adapter.getItem(position);
                openRoutesPage(place);
            }
        });

        currentLocationRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location myCurrentLocation = SelectingToFromState.getMyCurrentLocation();
                if(myCurrentLocation != null){
                    openRoutesPage(new MyCurrentPlace(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude()));
                }
            }
        });

        if(shouldSetNextClassClickListener){
            nextClassArrow.setVisibility(View.VISIBLE);
            nextClassRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Place place = nextClassPlace;
                    openRoutesPage(place);
                }
            });
        }

    }

    private void setupNextClassString() {
        final CalendarEvent calendarEvent = calendarViewModel.getEvent(this);
        final String eventString;
        final String eventLocation;

        if(calendarEvent != null){
            eventLocation = calendarEvent.getLocation();
            Place place = mViewModel.getRoomFromDB(eventLocation);

            if(place == null)
                populateNextClassStringForNullPlace();
            else
                populateNextClassString(calendarEvent, place);
        }
    }

    private void populateNextClassStringForNullPlace() {
        nextClassText.setText(getResources().getString(R.string.location_not_found));
        shouldSetNextClassClickListener = false;
    }

    private void populateNextClassString(CalendarEvent calendarEvent, Place place) {
        String eventString;
        eventString = calendarViewModel.getNextClassString((calendarEvent));
        nextClassText.setText(eventString);

        if(eventString.equals(getResources().getString(R.string.incorrect_format_event))){
            shouldSetNextClassClickListener = false;
        }
        else{
            shouldSetNextClassClickListener = true;
            nextClassPlace = place;
        }
    }

    public void openRoutesPage(Place place) {
        Intent openRoutes = new Intent(SearchActivity.this, RoutesActivity.class);

        if (SelectingToFromState.isQuickSelect()) {
            SelectingToFromState.setTo(place);
            if (SelectingToFromState.getMyCurrentLocation() != null) {
                Location myCurrentLocation = SelectingToFromState.getMyCurrentLocation();
                SelectingToFromState.setFrom(new MyCurrentPlace(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude()));
            } else {
                SelectingToFromState.setFrom(new MyCurrentPlace());
            }
        }
        if (SelectingToFromState.isSelectFrom()) {
            SelectingToFromState.setFrom(place);
        }
        if (SelectingToFromState.isSelectTo()) {
            SelectingToFromState.setTo(place);
        }

        startActivity(openRoutes);
    }


    private void setBackButtonOnClick() {
        ImageButton backButton = this.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
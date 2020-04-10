package com.example.concordia_campus_guide.activities;

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
import com.example.concordia_campus_guide.adapters.PlaceToSearchResultAdapter;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.helper.ViewModelFactory;
import com.example.concordia_campus_guide.models.CalendarEvent;
import com.example.concordia_campus_guide.models.helpers.CalendarViewModel;
import com.example.concordia_campus_guide.models.MyCurrentPlace;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.view_models.SearchActivityViewModel;

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
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });
    }

    private void setOnClickListeners() {
        searchText.setOnFocusChangeListener((view, onFocus) -> {
            if (onFocus) {
                searchResults.setVisibility(View.VISIBLE);
            }
        });

        searchResults.setOnItemClickListener((parent, view, position, id) -> {
            Place place = adapter.getItem(position);
            openRoutesPage(place);
        });

        currentLocationRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location myCurrentLocation = SelectingToFromState.getMyCurrentLocation();
                if(myCurrentLocation != null){
                    openRoutesPage(new MyCurrentPlace(SearchActivity.this, myCurrentLocation.getLongitude(), myCurrentLocation.getLatitude()));
                }
            }
        });

        if(Boolean.TRUE.equals(shouldSetNextClassClickListener)){
            nextClassArrow.setVisibility(View.VISIBLE);
            nextClassRow.setOnClickListener(view -> {
                Place place = nextClassPlace;
                openRoutesPage(place);
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
        eventString = calendarViewModel.getNextClassString(SearchActivity.this, (calendarEvent));
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
                SelectingToFromState.setFrom(new MyCurrentPlace(SearchActivity.this, myCurrentLocation.getLongitude(), myCurrentLocation.getLatitude()));
            } else {
                SelectingToFromState.setFrom(new MyCurrentPlace(SearchActivity.this));
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
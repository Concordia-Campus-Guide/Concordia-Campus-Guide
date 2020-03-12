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
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Adapters.PlaceToSearchResultAdapter;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.R;

public class SearchActivity extends AppCompatActivity {

    ListView searchResults;
    EditText searchText;
    SearchActivityViewModel mViewModel;

    private PlaceToSearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting up activity
        setContentView(R.layout.search_activity);
        setBackButtonOnClick();
        searchResults = (ListView) findViewById(R.id.searchResults);
        searchText = (EditText) findViewById(R.id.searchText);
        mViewModel = ViewModelProviders.of(this).get(SearchActivityViewModel.class);

        //get passed parameters
        mViewModel.setMyCurrentLocation((Location) getIntent().getParcelableExtra("myCurrentLocation"));
        mViewModel.setFromId(getIntent().getLongExtra("fromId", 0));
        mViewModel.setToId(getIntent().getLongExtra("toId", 0));
        mViewModel.setSelectingToOrFrom(getIntent().getStringExtra("selectingToOrFrom"));

        //android adapter for list view
        adapter = new PlaceToSearchResultAdapter(this, R.layout.list_item_layout, mViewModel.getAllPlaces());
        searchResults.setAdapter(adapter);

        //search results filtering according to search text
        searchText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable){ }
        });

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = adapter.getItem(position);
                openRoutesPage(place.getId(), getPlaceType(place));
            }
        });
    }

    private void openRoutesPage(long placeId, String classExtendsPlaceType){
        Intent openRoutes= new Intent(SearchActivity.this,
                RoutesActivity.class);

        openRoutes.putExtra("selectingToOrFrom", mViewModel.getSelectingToOrFrom());
        openRoutes.putExtra()
        openRoutes.putExtra("classExtendsPlaceType", classExtendsPlaceType);
        openRoutes.putExtra("placeId", placeId);
        openRoutes.putExtra("myCurrentLocation", mViewModel.getMyCurrentLocation());

        startActivity(openRoutes);
    }

    private void setBackButtonOnClick(){
        ImageButton backButton = (ImageButton)this.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getPlaceType(Place place){
        if(place instanceof RoomModel) return RoomModel.class.getSimpleName();
        if(place instanceof Floor) return Floor.class.getSimpleName();
        if(place instanceof Building) return Building.class.getSimpleName();

        return null;
    }
}
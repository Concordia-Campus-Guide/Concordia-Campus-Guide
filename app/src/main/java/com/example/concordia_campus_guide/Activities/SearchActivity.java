package com.example.concordia_campus_guide.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Adapters.PlaceToSearchResultAdapter;
import com.example.concordia_campus_guide.R;

public class SearchActivity extends AppCompatActivity {

    ListView searchResults;
    EditText searchText;
    SearchActivityViewModel mViewModel;
    private PlaceToSearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        setBackButtonOnClick();

        searchResults = (ListView) findViewById(R.id.searchResults);
        searchText = (EditText) findViewById(R.id.searchText);
        mViewModel = ViewModelProviders.of(this).get(SearchActivityViewModel.class);
        mViewModel.importBuildings(getApplicationContext());

        adapter = new PlaceToSearchResultAdapter(this, R.layout.list_item_layout, mViewModel.getBuildings().getPlaces());
        searchResults.setAdapter(adapter);

        searchText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable){

            }
        });
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
}
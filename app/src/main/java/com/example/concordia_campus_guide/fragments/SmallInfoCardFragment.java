package com.example.concordia_campus_guide.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.activities.MainActivity;
import com.example.concordia_campus_guide.activities.RoutesActivity;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.helper.ViewModelFactory;
import com.example.concordia_campus_guide.models.MyCurrentPlace;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.viewModels.SmallInfoCardFragmentViewModel;

public class SmallInfoCardFragment extends Fragment {

    private SmallInfoCardFragmentViewModel mViewModel;

    private TextView infoCardTitle;

    private Button directionsBt;
    private Place place;

    public SmallInfoCardFragment(Place place){
        this.place = place;
    }

    /**
     * Defines the view and initializes text views of the view
     *
     * @param inflater: the LayoutInflater as specified in Android docs
     * @param container: the ViewGroup as specified in Android docs
     * @param savedInstanceState: the Bundle as specified in Android docs
     *
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_info_card_fragment, container, false);

        directionsBt = view.findViewById(R.id.directions);
        infoCardTitle = view.findViewById(R.id.info_card_title);
        return view;
    }

    /**
     * Defines the behavior expected after the activity is created, such as initialization of
     * the view
     *
     * @param savedInstanceState: the Bundle as specified in Android docs
     *
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getActivity().getApplication())).get(SmallInfoCardFragmentViewModel.class);
        mViewModel.setPlace(this.place);
        setInfoCard();
        setOnClickListeners();
    }

    /**
     * Initializes specifics of info card view such as building name, address, building image,
     * services, departements
     */
    private void setInfoCard(){
        infoCardTitle.setText(place.getDisplayName());
    }

    private void setOnClickListeners(){
        this.directionsBt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onClickDirections(v);
            }
        });
    }

    public void onClickDirections(View v){
        Intent openRoutes = new Intent(getActivity(), RoutesActivity.class);

        SelectingToFromState.setQuickSelectToTrue();
        SelectingToFromState.setMyCurrentLocation(((MainActivity) getActivity()).getMyCurrentLocation());

        Location myCurrentLocation = SelectingToFromState.getMyCurrentLocation();
        if(myCurrentLocation != null){
            SelectingToFromState.setFrom(new MyCurrentPlace(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude()));
        }
        else{
            SelectingToFromState.setFrom(new MyCurrentPlace());
        }
        SelectingToFromState.setTo(mViewModel.getPlace());

        startActivity(openRoutes);
    }
}
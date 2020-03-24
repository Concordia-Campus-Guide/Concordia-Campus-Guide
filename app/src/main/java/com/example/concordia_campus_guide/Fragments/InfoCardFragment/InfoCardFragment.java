package com.example.concordia_campus_guide.Fragments.InfoCardFragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Activities.RoutesActivity;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataRetrieval;
import com.example.concordia_campus_guide.Helper.RoutesHelpers.UrlBuilder;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.Models.MyCurrentPlace;
import com.example.concordia_campus_guide.R;

import java.io.InputStream;

public class InfoCardFragment extends Fragment {

    private InfoCardFragmentViewModel mViewModel;
    private String buildingCode;

    private TextView infoCardTitle;
    private TextView buildingAddress;
    private TextView departmentsList;
    private TextView servicesList;
    private ImageView buildingImage;

    private LinearLayout services;
    private LinearLayout departments;

    private Button directionsBt;
    private Button indoorMapBt;

    public InfoCardFragment(String buildingCode){
        this.buildingCode = buildingCode;
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
        View view = inflater.inflate(R.layout.info_card_fragment, container, false);

        infoCardTitle = view.findViewById(R.id.info_card_title);
        buildingAddress = view.findViewById(R.id.building_address);
        departmentsList = view.findViewById(R.id.departments_list);
        servicesList = view.findViewById(R.id.services_list);
        buildingImage = view.findViewById(R.id.building_image);
        services = view.findViewById(R.id.services);
        departments = view.findViewById(R.id.departments);
        directionsBt = view.findViewById(R.id.directions);
        indoorMapBt = view.findViewById(R.id.indoor_map);
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
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getActivity().getApplication())).get(com.example.concordia_campus_guide.Fragments.InfoCardFragment.InfoCardFragmentViewModel.class);
        mViewModel.setBuilding(this.buildingCode);
        setInfoCard();
        setOnClickListeners();
    }

    /**
     * Initializes specifics of info card view such as building name, address, building image,
     * services, departements
     */
    private void setInfoCard(){
        infoCardTitle.setText(mViewModel.getBuilding().getBuilding_Long_Name());
        buildingAddress.setText(mViewModel.getBuilding().getAddress());
        setDepartmentsList();
        setServicesList();
        setBuildingImage(buildingCode);
    }

    /**
     * Intializes the department list text to be displayed
     */
    private void setDepartmentsList(){
        String text = mViewModel.getBuilding().getDepartmentsString();

        if(text.isEmpty())
            departments.setVisibility(View.GONE);
        else
            departmentsList.setText(text);
    }

    /**
     * Intializes the services list text to be displayed
     */
    private void setServicesList() {
        String text = mViewModel.getBuilding().getServicesString();
        System.out.println("Text is: " + text);
        if(text.isEmpty())
            services.setVisibility(View.GONE);
        else
            servicesList.setText(text);
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
        SelectingToFromState.setTo(mViewModel.getBuilding());

        startActivity(openRoutes);
    }

    /**
     * Intializes the building image to the image view
     *
     * @param buildingCode: the Building code
     */
    private void setBuildingImage(String buildingCode){
        try{
            InputStream inputStream = getActivity().getAssets().open("buildings_images/"+ buildingCode +".jpg");
            Drawable d = Drawable.createFromStream(inputStream, null);
            buildingImage.setImageDrawable(d);
        }
        catch(Exception e){
            try{
                InputStream inputStream = getActivity().getAssets().open("buildings_images/"+ buildingCode +".PNG");
                Drawable d = Drawable.createFromStream(inputStream, null);
                buildingImage.setImageDrawable(d);
            }
            catch(Exception e2){
                Log.e("InfoCardFragment", "jpg image cannot be shown in the info card: " + e.getMessage());
                Log.e("InfoCardFragment", "PNG image cannot be shown in the info card: " + e2.getMessage());
            }
        }

    }
}
package com.example.concordia_campus_guide.InfoCardFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Global.ApplicationState;
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
    private Button inddorMapBt;

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
        inddorMapBt = view.findViewById(R.id.indoor_map);
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
        mViewModel = ViewModelProviders.of(this).get(com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragmentViewModel.class);
        mViewModel.setBuilding(ApplicationState.getInstance(getContext()).getBuildings().getBuilding(this.buildingCode));
        setInfoCard();
    }

    public void setBuildingCode(String buildingCode){
        this.buildingCode = buildingCode;
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
                System.out.println(e.getMessage());
                System.out.println(e2.getMessage());
            }
        }

    }



}
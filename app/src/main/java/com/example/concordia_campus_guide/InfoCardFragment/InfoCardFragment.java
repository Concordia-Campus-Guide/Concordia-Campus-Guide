package com.example.concordia_campus_guide.InfoCardFragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Models.BuildingInfo;
import com.example.concordia_campus_guide.R;

import java.io.InputStream;

// This class is the view (UI) related things
public class InfoCardFragment extends Fragment {

    private InfoCardFragmentViewModel mViewModel;
    private String buildingCode;
    private BuildingInfo building;

    private TextView infoCardTitle;
    private TextView buildingAddress;
    private TextView departmentsList;
    private TextView servicesList;
    private ImageView buildingImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        buildingCode = this.getArguments().getString("buildingCode");
        View view = inflater.inflate(R.layout.info_card_fragment, container, false);

        infoCardTitle = (TextView) view.findViewById(R.id.info_card_title);
        buildingAddress = (TextView) view.findViewById(R.id.building_address);
        departmentsList = (TextView) view.findViewById(R.id.departments_list);
        servicesList = (TextView) view.findViewById(R.id.services_list);
        buildingImage = (ImageView) view.findViewById(R.id.building_image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragmentViewModel.class);
        mViewModel.setBuildings(mViewModel.readJsonFile(getContext()));
        building = mViewModel.getBuildings().getBuilding(buildingCode);
        setInfoCard();
    }

    private void setInfoCard(){
        infoCardTitle.setText(building.getBuilding_Long_Name());
        buildingAddress.setText(building.getAddress());
        setDepartmentsList();
        setServicesList();
        setBuildingImage(buildingCode);
    }

    private void setDepartmentsList(){
        String text = building.getDepartmentsString();
        departmentsList.setText(text);
    }

    private void setServicesList() {
        String text = building.getServicesString();
        servicesList.setText(text);
    }

    private void setBuildingImage(String buildingCode){
        try{
            InputStream inputStream = getActivity().getAssets().open("buildings_images/"+buildingCode+".jpg");
            Drawable d = Drawable.createFromStream(inputStream, null);
            buildingImage.setImageDrawable(d);
        }
        catch(Exception e){
            try{
                InputStream inputStream = getActivity().getAssets().open("buildings_images/"+buildingCode+".PNG");
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
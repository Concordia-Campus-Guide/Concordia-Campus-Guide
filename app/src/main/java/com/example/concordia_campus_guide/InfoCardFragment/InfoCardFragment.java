package com.example.concordia_campus_guide.InfoCardFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.R;

// This class is the view (UI) related things
public class InfoCardFragment extends Fragment {

    private Buildings buildings;
    private InfoCardFragmentViewModel mViewModel;
    private String buildingCode;
    private TextView infoCardTitle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        buildingCode = this.getArguments().getString("buildingCode");
        View view = inflater.inflate(R.layout.info_card_fragment, container, false);
        infoCardTitle = (TextView) view.findViewById(R.id.info_card_title);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(com.example.concordia_campus_guide.InfoCardFragment.InfoCardFragmentViewModel.class);
        buildings = mViewModel.readJsonFile(getContext());

        infoCardTitle.setText(buildings.getBuilding(buildingCode).getBuilding_Long_Name());

    }

}

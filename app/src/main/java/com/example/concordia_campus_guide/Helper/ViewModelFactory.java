package com.example.concordia_campus_guide.Helper;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.Activities.PathsViewModel;
import com.example.concordia_campus_guide.Activities.RoutesActivityViewModel;
import com.example.concordia_campus_guide.Activities.SearchActivityViewModel;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Fragments.InfoCardFragment.InfoCardFragmentViewModel;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Fragments.SmallInfoCardFragment.SmallInfoCardFragmentViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private AppDatabase appDb;


    public ViewModelFactory(Application application) {
        this.appDb = AppDatabase.getInstance(application.getApplicationContext());
    }

    //all view models need to be registered in the ViewModelFactory if they want to benefit from
    //the creation without the need for context
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == InfoCardFragmentViewModel.class) {
            return (T) new InfoCardFragmentViewModel(appDb);
        } else if (modelClass == SearchActivityViewModel.class) {
            return (T) new SearchActivityViewModel(appDb);
        } else if (modelClass == RoutesActivityViewModel.class) {
            return (T) new RoutesActivityViewModel(appDb);
        } else if (modelClass == LocationFragmentViewModel.class) {
            return (T) new LocationFragmentViewModel(appDb);
        } else if (modelClass == PathsViewModel.class) {
            return (T) new PathsViewModel(appDb);
        } else if(modelClass == SmallInfoCardFragmentViewModel.class){
            return (T) new SmallInfoCardFragmentViewModel(appDb);
        }
        return null;
    }
}

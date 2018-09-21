package com.example.android.quakereport.screens.earthquakesListActivity;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class EarthquakesListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private String mParam;


    public EarthquakesListViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new EarthquakesListViewModel(mApplication, mParam);
    }
}

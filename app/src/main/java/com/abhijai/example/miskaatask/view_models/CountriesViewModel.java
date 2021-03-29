package com.abhijai.example.miskaatask.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.abhijai.example.miskaatask.models.CountryResponse;
import com.abhijai.example.miskaatask.repositories.CountriesRepository;
import com.abhijai.example.miskaatask.util.Helper;
import com.abhijai.example.miskaatask.util.Resource;

import java.util.List;

public class CountriesViewModel extends AndroidViewModel
{
    private static final String TAG = "NetworkBoundResourceO";
    private final CountriesRepository repository;
    private final MediatorLiveData<Resource<List<CountryResponse>>> result = new MediatorLiveData<>();

    public CountriesViewModel(@NonNull Application application) {
        super(application);
        repository = CountriesRepository.getRepositoryInstance(application);
        getData(Helper.isNetworkAvailable(application));
    }

    private void getData(boolean isNetworkAvailable){
        try {
            LiveData<Resource<List<CountryResponse>>> source = repository.getGeneralData(isNetworkAvailable);
            result.addSource( source, new Observer<Resource<List<CountryResponse>>>() {
                @Override
                public void onChanged(Resource<List<CountryResponse>> listResource) {
                    result.setValue(listResource);
                    //result.removeSource(source); if we are removing resource here we are not able to observe anything in MainActivity
                }
            });
        }
        catch (Exception exp){
            exp.printStackTrace();
            Log.e(TAG, "Exception: in CountryViewModel----->"+exp.getMessage());
        }
    }

    public LiveData<Resource<List<CountryResponse>>> observeResult(){
        return result;
    }

    public void deleteAllData(){
        repository.deleteAllDataFromLocalDB();
    }



    public LiveData<CountryResponse> getSingleCountry(String name){
        return repository.getSingleCountryDetails(name);
    }
}

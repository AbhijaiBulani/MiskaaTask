package com.abhijai.example.miskaatask.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhijai.example.miskaatask.models.CountryResponse;
import com.abhijai.example.miskaatask.network.ServiceGenerator;
import com.abhijai.example.miskaatask.persistence.CountryDAO;
import com.abhijai.example.miskaatask.persistence.CountryDatabase;
import com.abhijai.example.miskaatask.util.ApiResponse;
import com.abhijai.example.miskaatask.util.AppExecutors;
import com.abhijai.example.miskaatask.util.NetworkBoundResource;
import com.abhijai.example.miskaatask.util.Resource;

import java.util.List;

public class CountriesRepository
{
    private static final String TAG = "NetworkBoundResourceO";
    private static CountriesRepository instance;
    private static CountryDAO dao;
    private final AppExecutors appExecutors;
    private MutableLiveData<Resource<List<CountryResponse>>> result = new MutableLiveData<>();
    private CountriesRepository(Context context){
        dao = CountryDatabase.getDbInstance(context).getCountryDAO();
        appExecutors = AppExecutors.getInstance();
    }
    public static CountriesRepository getRepositoryInstance(Context context){
        if (instance==null)
            instance = new CountriesRepository(context);
        return instance;
    }


    public LiveData<Resource<List<CountryResponse>>> getGeneralData(boolean isNetworkFound){
        result =  new NetworkBoundResource< List<CountryResponse>, List<CountryResponse> >(isNetworkFound){
            @Override
            protected void saveCallResultIntoDb(@NonNull List<CountryResponse> item) {
                Log.e(TAG, "REPO => saveCallResultIntoDb: " );
                dao.insertCountries(item);
            }

            @Override
            protected boolean shouldFetchFromNetworkOrNot(@Nullable List<CountryResponse> data) {
                return isNetworkFound;
            }

            @NonNull
            @Override
            protected LiveData<List<CountryResponse>> loadFromDb() {
                Log.d(TAG, "REPO => loadFromDb");
                return dao.getCountriesFromLocalDatabase();
            }

            @Override
            protected boolean doesDbAlreadyHasData()
            {
                CountryResponse[] data = dao.doesDbHasAnyCountry();
                boolean temp = data.length>0;
                Log.e(TAG, "REPO ==> doesDbAlreadyHasData: "+temp);
                return data!=null && data.length>0;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<CountryResponse>>> createNetworkApiCall() {
                Log.e(TAG, "REPO => createNetworkApiCall");
                return ServiceGenerator.getCountryApi().getCountriesFromNetwork();
            }

        }.getAsLiveData();
        return result;
    }

    public void deleteAllDataFromLocalDB(){
        appExecutors.getDiskIo().execute(()->{
            int totalRowDeleted = dao.deleteAllData();
            Log.e(TAG, "Total "+totalRowDeleted+" Row Deleted");
            appExecutors.getMainThreadExecutor().execute(()-> result.setValue(Resource.error("All Data Deleted",null)));
        });
    }

    public LiveData<CountryResponse> getSingleCountryDetails(String countryName){
         return dao.getCountryByName(countryName);
    }

}





















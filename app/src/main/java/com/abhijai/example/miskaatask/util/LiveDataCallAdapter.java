package com.abhijai.example.miskaatask.util;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>>
{
    private static final String TAG = "LiveDataCallAdapterO";
    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        Log.d(TAG, "ResponseType in constructor : "+responseType.getClass());
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            @Override
            protected void onActive() {
                super.onActive();
                final ApiResponse apiResponse = new ApiResponse();
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        Log.d(TAG, "onResponse: "+response.body());
                        postValue(apiResponse.create(response));
                        //above postValue will execute the onChange() of NetworkBoundResource inside fetchDataFromNetwork() because of createCall() at line 84.
                    }
                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        postValue(apiResponse.create(t));
                    }
                });
            }
        };
    }
}


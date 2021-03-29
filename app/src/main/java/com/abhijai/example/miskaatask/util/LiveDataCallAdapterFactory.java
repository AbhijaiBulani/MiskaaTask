package com.abhijai.example.miskaatask.util;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

//This class produce the objects of LiveDataCallAdapter
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    private static final String TAG = "LiveDataCallAdapterFacO";
    /**
     * This method performs a number of checks and then returns the Response type for the Retrofit requests
     * (@bodyType is the ResponseType. It can be RecipeResponse or RecipeSearchResponse)
     *
     * CHECK #1) returnType returns LIVE DATA
     * CHECK #2) Type LiveData<T> is of ApiResponse.class
     * CHECK #3) Make sure ApiResponse is Parameterized. AKA: ApiResponse<T> exists.
     *
     *
     * @param returnType
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // Check #1
        // Make sure the CallAdapter is returning a type of LiveData
        Log.d(TAG, "get: ReturnType : "+CallAdapter.Factory.getRawType(returnType));
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class){
            return null;
        }
        // Check #2
        // Type that LiveData is wrapping i.e in liveData<T> we have to check is <T> is of ApiResponse.class
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        Log.d(TAG, "get: ObservableType : "+observableType);
        Log.d(TAG, "get: RawObservableType : "+CallAdapter.Factory.getRawType(observableType));
        // Check if it's of Type ApiResponse
        if (CallAdapter.Factory.getRawType(observableType)!= ApiResponse.class){
            throw new IllegalArgumentException("Type must be a ApiResponse type");
        }
        // Check #3
        // Check if ApiResponse is parameterized. AKA: Does ApiResponse<T> exist? (must wrap around T)
        // FYI: T is either RecipeResponse or RecipeSearchResponse in this app. But T can be anything theoretically.

        if (!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("Response must be of some Type");
        }
        Type typeBody = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
        Log.d(TAG, "get: TypeBody : "+typeBody);
        return new LiveDataCallAdapter<Type>(typeBody);
    }
}
package com.abhijai.example.miskaatask.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;




public abstract class NetworkBoundResource<CacheObject, RemoteObject>
{
    private static final String TAG = "NetworkBoundResourceO";
    private final AppExecutors appExecutors;
    private final boolean isNetworkAvailable ;
    private final MediatorLiveData<Resource<CacheObject>> mediatorLiveData = new MediatorLiveData<>();
    public NetworkBoundResource(boolean networkAvailable){
        appExecutors = AppExecutors.getInstance();
        isNetworkAvailable = networkAvailable;
        init();
    }
    
    private void init(){
        mediatorLiveData.setValue(Resource.loading(null));
        if (isNetworkAvailable)
            startRemoteProcess();
        else 
            startDatabaseProcess();
    }
    
    private void startRemoteProcess()
    {
        Log.d(TAG, "startRemoteProcess: ");
        appExecutors.getDiskIo().execute(()->{
            if (doesDbAlreadyHasData())
            {
                // if db already has data, we don't want to hit api again and again
                Log.e(TAG, "-------------- Db already has data----------------");
                startDatabaseProcess();
            }
            else {
                appExecutors.getMainThreadExecutor().execute(()->{
                    LiveData<ApiResponse<RemoteObject>> apiSource = createNetworkApiCall();
                    mediatorLiveData.addSource(apiSource,remoteObjectApiResponse -> {
                        if (remoteObjectApiResponse instanceof ApiResponse.ApiSuccessResponse){
                            processApiSuccessResponse((ApiResponse.ApiSuccessResponse<RemoteObject>) remoteObjectApiResponse);
                        }
                        else if (remoteObjectApiResponse instanceof ApiResponse.ApiErrorResponse){
                            processApiErrorResponse((ApiResponse.ApiErrorResponse<RemoteObject>) remoteObjectApiResponse);
                        }
                        else if (remoteObjectApiResponse instanceof ApiResponse.ApiEmptyResponse){
                            processApiEmptyResponse();
                        }
                        mediatorLiveData.removeSource(apiSource);
                    });
                });
            }
        });
    }
    
    private void startDatabaseProcess(){
        Log.d(TAG, "startDatabaseProcess: ");
        appExecutors.getDiskIo().execute(()->{
            LiveData<CacheObject> dbSource = loadFromDb();
            appExecutors.getMainThreadExecutor().execute(()->{
                mediatorLiveData.addSource(dbSource,cacheObject -> {
                    mediatorLiveData.setValue(Resource.success(cacheObject));
                    mediatorLiveData.removeSource(dbSource);
                });
            });
        });
    }



    private void processApiSuccessResponse(ApiResponse.ApiSuccessResponse<RemoteObject> obj){
        appExecutors.getDiskIo().execute(()->{
            saveCallResultIntoDb(obj.getBody());
            LiveData<CacheObject> dbSource = loadFromDb();
            appExecutors.getMainThreadExecutor().execute(()->{
                mediatorLiveData.addSource(dbSource,cacheObject -> {
                    mediatorLiveData.setValue(Resource.success(cacheObject));
                    mediatorLiveData.removeSource(dbSource);
                });
            });
        });
    }


    private void processApiErrorResponse(ApiResponse.ApiErrorResponse<RemoteObject> obj){
        LiveData<CacheObject> dbSource = loadFromDb();
            mediatorLiveData.addSource(dbSource,cacheObject -> {
                    mediatorLiveData.setValue(Resource.error(obj.getErrorMessage(),cacheObject));
                    mediatorLiveData.removeSource(dbSource);
            });
    }


    private void processApiEmptyResponse(){
        appExecutors.getDiskIo().execute(()->{
            LiveData<CacheObject> dbSource = loadFromDb();
            mediatorLiveData.addSource(dbSource,cacheObject -> {
                appExecutors.getMainThreadExecutor().execute(()->{
                    mediatorLiveData.setValue(Resource.success(cacheObject));
                    mediatorLiveData.removeSource(dbSource);
                });
            });
        });
    }





    //--------------------- abstract methods are below ----------------------------

    private RemoteObject processResponse(ApiResponse.ApiSuccessResponse response)
    {
        Log.d(TAG, " -> processResponse");
        return (RemoteObject) response.getBody();
    }

    @MainThread
    private void setValue(Resource<CacheObject> newValue){
        if (mediatorLiveData.getValue() != newValue){
            mediatorLiveData.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResultIntoDb(@NonNull RemoteObject item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetchFromNetworkOrNot(@Nullable CacheObject data);

    // Called to get the cached data from the database.
    @NonNull @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    @WorkerThread
    protected abstract boolean doesDbAlreadyHasData();

    // Called to create the API call.
    @NonNull @MainThread
    protected abstract LiveData<ApiResponse<RemoteObject>> createNetworkApiCall();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final MutableLiveData<Resource<CacheObject>> getAsLiveData(){
        return mediatorLiveData;
    };
    
    
}

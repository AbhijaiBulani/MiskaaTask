package com.abhijai.example.miskaatask.util;

import android.util.Log;

import retrofit2.Response;

//This class is for handling different type of responses.
public class ApiResponse<T> {

    private static final String TAG = "ApiResponseO";
    public ApiResponse<T> create(Throwable error){
        Log.d(TAG, "create: ERROR -> "+error.getMessage());
        return new ApiErrorResponse<T>(!error.getMessage().equalsIgnoreCase("")?error.getMessage():"Unknown Error\nCheck your connection");
    }

    public ApiResponse<T> create(Response<T> response){
        Log.d(TAG, "create: SUCCESS");
        if (response.isSuccessful()){
            T body = response.body();
            Log.d(TAG, "create: BODY -> "+response.body());
            Log.d(TAG, "create: CODE -> "+response.code());
            if (body==null || response.code()==204)
            {
                return new ApiEmptyResponse<>();
            }
            else {
                return new ApiSuccessResponse<>(body);
            }
        }
        else {
            String errorMessage="";
            try {
                errorMessage = response.errorBody().string();
            }
            catch (Exception exp){
                exp.printStackTrace();
                errorMessage = exp.getMessage();
                Log.d(TAG, "create: "+errorMessage);
            }
            return new ApiErrorResponse<>(errorMessage);
        }
    }

    public static class ApiSuccessResponse<T> extends ApiResponse<T>{
        private final T body;
        ApiSuccessResponse(T body){
            this.body = body;
        }
        public T getBody(){
            return body;
        }
    }

    public static class ApiErrorResponse<T> extends ApiResponse<T>{
        private final String errorMessage;
        ApiErrorResponse(String errorMessage){
            this.errorMessage = errorMessage;
        }
        public String getErrorMessage(){
            return errorMessage;
        }
    }

    public static class ApiEmptyResponse<T> extends ApiResponse<T>{

    }
}

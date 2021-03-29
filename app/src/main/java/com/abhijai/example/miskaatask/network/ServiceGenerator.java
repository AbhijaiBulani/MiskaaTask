package com.abhijai.example.miskaatask.network;

import com.abhijai.example.miskaatask.util.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {


    private ServiceGenerator() {
        //restriction for the instantiation of this SingleTon class.
    }

    private final static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static OkHttpClient.Builder setUpRetrofitLogger() {
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        //esatblish connection to server for eg -> handshaking with server and during this server decides server allow your application or not.
        httpClient.connectTimeout(RetrofitConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        //time between each byte read from the server
        httpClient.readTimeout(RetrofitConstants.READ_TIMEOUT,TimeUnit.SECONDS);
        // time between each byte sent to server
        httpClient.writeTimeout(RetrofitConstants.WRITE_TIMEOUT,TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(false);

        return httpClient;
    }

    private final static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(CountriesApi.BASE_URL)
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(setUpRetrofitLogger()
                    .build());
    private final static Retrofit retrofit = retrofitBuilder.build();

    private final static CountriesApi countryApi = retrofit.create(CountriesApi.class);
    public static CountriesApi getCountryApi() {
        return countryApi;
    }
}

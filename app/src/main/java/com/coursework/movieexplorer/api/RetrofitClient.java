package com.coursework.movieexplorer.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  singleton class that will create and configure
 * the Retrofit single instance to interact with
 * the TMDB  API.
 */
public class RetrofitClient {
//set the base url
    private static final String BASE_URL = "https://api.themoviedb.org/3/";


    // Singleton instance of Retrofit
    private static Retrofit retrofit = null;
    //create an object of the api interface
    // create a retrofit object if there is none and
    // set the base url
    // parse JSON responses into Java objects
    // use retrofit to crete an instance of api
    public static Api getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Api.class);
    }
}
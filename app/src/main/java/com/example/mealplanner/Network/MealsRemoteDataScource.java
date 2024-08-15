package com.example.mealplanner.Network;

import android.util.Log;

import com.example.mealplanner.Model.Meal;
import com.example.mealplanner.Model.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataScource {


    private static final String URL_STRING = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealsRemoteDataScource";
    private static MealsRemoteDataScource instance = null;
    private MealApiService mealApiService;

    private MealsRemoteDataScource() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL_STRING).build();

        mealApiService = retrofit.create(MealApiService.class);
    }

    public static MealsRemoteDataScource getInstance() {

        if (instance == null) {
            instance = new MealsRemoteDataScource();
        }
        return instance;
    }

    public void makeNetworkCall(NetworkListener listener) {


        Call<MealResponse> call = mealApiService.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {


            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful())
                {
                    Log.i(TAG, "onResponse: "+response.body());
                    MealResponse meal =  response.body();
                    Log.i(TAG, "onResponse: "+response.raw().body());

                    listener.onRandomMealSuccess(meal);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                listener.onRandomMealFailure(t.getMessage());
                t.printStackTrace();
            }
        });

    }
}

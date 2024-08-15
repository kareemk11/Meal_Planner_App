package com.example.mealplanner.Network;

import android.util.Log;

import com.example.mealplanner.Model.Area.AreaResponse;
import com.example.mealplanner.Model.Category.CategoryResponse;
import com.example.mealplanner.Model.Meal.MealResponse;
import com.example.mealplanner.Network.NetworkListeners.RandomMealNetworkListener;

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

    public void getRandomMeal(RandomMealNetworkListener listener) {


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

    public void getCategories( MealsCategoryNetworkListener listener){
        Call<CategoryResponse> call = mealApiService.getCategoriesList();
        call.enqueue(new Callback<CategoryResponse>(){
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.i(TAG, "onResponse: "+response.body());
                listener.onMealsCategorySuccess(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                listener.onMealsCategoryFailure(t.getMessage());
            }
        });

    }

    public void getAreas(AreaNetworkListener listener)
    {
        Call <AreaResponse> call = mealApiService.getAreasList();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {


                if(response.isSuccessful())
                {
                    listener.onAreaSuccess(response.body().getAreas());
                    Log.i(TAG, "onResponse / areas: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

                Log.i(TAG, "onFailure / areas: "+t.getMessage());
                listener.onAreaFailure(t.getMessage());
            }
        });
    }
}

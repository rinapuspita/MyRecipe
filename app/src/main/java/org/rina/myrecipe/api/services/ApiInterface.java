package org.rina.myrecipe.api.services;


import org.rina.myrecipe.api.models.AppVersion;
import org.rina.myrecipe.api.models.Envelope;
import org.rina.myrecipe.api.models.LoginRequest;
import org.rina.myrecipe.api.models.LoginResponse;
import org.rina.myrecipe.api.models.RecipeResponse;
import org.rina.myrecipe.api.models.RegisterRequest;
import org.rina.myrecipe.api.models.RegisterResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface{
    @GET("/")
    Call<AppVersion> getAppVersion();

    @POST("/api/auth/login")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);

    @POST("/api/auth/register")
    Call<RegisterResponse> doRegister(@Body RegisterRequest registerRequest);

    @GET("/api/recipe")
    Call<Envelope<List<RecipeResponse>>> getRecipe();

    @GET("/api/recipe")
    Call<Envelope<List<RecipeResponse>>> getMoreRecipe(@Query("page") int page);


}

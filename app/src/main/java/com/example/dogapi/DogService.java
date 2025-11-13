package com.example.dogapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogService {
    @GET("breeds/list/all")
    Call<DogResponse> getAllBreeds();
}

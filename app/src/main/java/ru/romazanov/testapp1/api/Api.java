package ru.romazanov.testapp1.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.romazanov.testapp1.api.model.AuthResponse;
import ru.romazanov.testapp1.api.model.Response;

public interface Api {
    @GET("hs/MobileClient/{imei}/form/users")
    Call<Response> getList(@Path("imei") String imei);

    @GET("hs/MobileClient/{imei}/authentication?&nfc=&copyFromDevice=false")
    Call<AuthResponse> getAuth(@Path("imei") String imei, @Query("uid") String uid,  @Query("pass") String pass);
}

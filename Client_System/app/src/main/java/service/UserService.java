package service;

import data.reqeust.RegisterFCMTokenRequest;
import data.reqeust.SignInRequest;
import data.reqeust.SignUpRequest;
import data.response.SignInResponse;
import data.response.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("api/sign-in")
    Call<SignInResponse> signIn(@Body SignInRequest request);

    @POST("api/sign-up")
    Call<SignUpResponse> signUp(@Body SignUpRequest request);

    @POST("api/users/me/fcm-tokens")
    Call<Void> registerToken(@Body RegisterFCMTokenRequest registerFCMTokenRequest);
}

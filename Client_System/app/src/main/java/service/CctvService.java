package service;

import java.util.List;

import data.response.CctvAlertResponse;
import data.response.CctvResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CctvService {

    @GET("api/cctvs/")
    Call<List<CctvResponse>> getCctvs();

    @GET("api/cctvs/{cctvId}/")
    Call<CctvResponse> getDetail(@Path("cctvId") Long cctvId);

    @GET("api/cctvs/{cctvId}/alerts/")
    Call<List<CctvAlertResponse>> getAlertsByCctvId(@Path("cctvId") Long cctvId);
}

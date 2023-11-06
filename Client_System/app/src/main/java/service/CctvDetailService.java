package service;

import data.Cctv;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CctvDetailService {

    @GET("api/cctvs/{cctvId}/")
    Call<Cctv> getDetail(@Path("cctvId") Long cctvId);
}

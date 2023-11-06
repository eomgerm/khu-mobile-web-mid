package service;

import data.CctvAlert;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CctvAlertService {

    @GET("api/cctvs/{cctvId}/alerts/")
    Call<List<CctvAlert>> getAlertsByCctvId(@Path("cctvId") Long cctvId);
}

package service;

import data.Cctv;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CctvService {

    @GET("api/cctvs/")
    Call<List<Cctv>> getCctvs();
}

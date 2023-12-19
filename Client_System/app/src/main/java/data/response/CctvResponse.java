package data.response;

import com.google.gson.annotations.SerializedName;

public class CctvResponse {

    @SerializedName("cctv_id")
    private final Long cctvId;
    @SerializedName("name")
    private final String name;
    @SerializedName("recent_image")
    private final String cctvUrl;

    public CctvResponse(Long cctvId, String name, String cctvUrl) {
        this.cctvId = cctvId;
        this.name = name;
        this.cctvUrl = cctvUrl;
    }

    public Long getCctvId() {
        return cctvId;
    }

    public String getName() {
        return name;
    }

    public String getCctvUrl() {
        return cctvUrl;
    }
}

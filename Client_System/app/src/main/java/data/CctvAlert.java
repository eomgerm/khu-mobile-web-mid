package data;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class CctvAlert {

    private static final String BASE_URL = "http://10.0.2.2:8000";

    @SerializedName("user_id")
    private final Long alertId;
    @SerializedName("cctv_id")
    private final Long cctvId;

    @SerializedName("created_date")
    private final LocalDateTime alertTime;
    @SerializedName("alert_image")
    private final String alertImage;

    public CctvAlert(Long alertId, Long cctvId, LocalDateTime alertTime, String alertImage) {
        this.alertId = alertId;
        this.cctvId = cctvId;
        this.alertTime = alertTime;
        this.alertImage = BASE_URL + alertImage;
    }

    public Long getCctvId() {
        return cctvId;
    }

    public Long getAlertId() {
        return alertId;
    }

    public LocalDateTime getAlertTime() {
        return alertTime;
    }

    public String getAlertImage() {
        return alertImage;
    }
}

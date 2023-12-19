package data.reqeust;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("username")
    private final String username;

    @SerializedName("email")
    private final String email;

    @SerializedName("password")
    private final String password;

    @SerializedName("fcm_token")
    private final String fcmToken;

    public SignUpRequest(String username, String email, String password, String fcmToken) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fcmToken = fcmToken;
    }
}

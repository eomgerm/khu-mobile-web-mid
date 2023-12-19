package data.reqeust;

import com.google.gson.annotations.SerializedName;

public class RegisterFCMTokenRequest {
    @SerializedName("token")
    private final String token;

    public RegisterFCMTokenRequest(String token) {
        this.token = token;
    }
}

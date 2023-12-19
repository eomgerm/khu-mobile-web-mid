package data.reqeust;

import com.google.gson.annotations.SerializedName;

public class SignInRequest {

    @SerializedName("email")
    private final String email;

    @SerializedName("password")
    private final String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

package service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

import data.reqeust.RegisterFCMTokenRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FCMService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        UserService userService = RetrofitClient.getRetrofitInstance(getApplicationContext()).create(UserService.class);
        userService.registerToken(new RegisterFCMTokenRequest(token)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "토큰 저장 요청 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "토큰 저장 요청 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "토큰 저장 요청 실패", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

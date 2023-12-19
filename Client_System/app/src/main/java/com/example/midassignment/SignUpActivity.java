package com.example.midassignment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.midassignment.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import data.reqeust.SignUpRequest;
import data.response.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.RetrofitClient;
import service.UserService;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        fcmToken = task.getResult();

                        // Log and toast
                        Log.d("FCM", fcmToken);
                    }
                });

        binding.signUpBtn.setOnClickListener(view -> {
            signUp();
        });
    }

    private void signUp() {
        String email = String.valueOf(binding.emailInput.getText());
        String password = String.valueOf(binding.passwordInput.getText());
        String username = String.valueOf(binding.usernameInput.getText());
        SignUpRequest request = new SignUpRequest(username, email, password, fcmToken);

        UserService userService = RetrofitClient.getRetrofitInstance(getApplicationContext()).create(UserService.class);
        userService.signUp(request).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.code() == 201) {
                    Toast.makeText(SignUpActivity.this, "회원 가입 성공!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "회원 가입 실패, 입력 필드를 확인하세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "요청 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
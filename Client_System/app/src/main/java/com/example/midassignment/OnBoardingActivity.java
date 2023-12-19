package com.example.midassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.midassignment.databinding.OnBoardingBinding;

import data.reqeust.SignInRequest;
import data.response.SignInResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.AppTerminateDetection;
import service.RetrofitClient;
import service.UserService;
import utils.SharedPreferencesManager;

public class OnBoardingActivity extends AppCompatActivity {

    private OnBoardingBinding binding;

    private UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = OnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userService = RetrofitClient.getRetrofitInstance(getApplicationContext()).create(UserService.class);

        binding.loginBtn.setOnClickListener(view -> signIn());

        binding.goToSignUpBtn.setOnClickListener(view -> goToSignUp());

        binding.loginEmailInput.setText("abc@abc.com");
        binding.loginPasswordInput.setText("password");
    }

    private void goToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.slide_right_enter, R.anim.slide_right_exit);
    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void signIn() {
        String email = String.valueOf(binding.loginEmailInput.getText());
        String password = String.valueOf(binding.loginPasswordInput.getText());
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            SignInRequest request = new SignInRequest(email, password);
            Call<SignInResponse> call = userService.signIn(request);
            call.enqueue(new Callback<SignInResponse>() {
                @Override
                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                    if (response.code() == 200 && response.body() != null) {
                        SharedPreferencesManager.setAccessToken(OnBoardingActivity.this, response.body().getAccessToken());
                        if (!binding.autoLoginCheckBox.isChecked()) {
                            Log.i("OnBoardingActivity", "자동 로그인 싫어");
                            Intent intent = new Intent(getApplicationContext(), AppTerminateDetection.class);
                            startService(intent);
                        }
                        goToHome();
                    } else {
                        Log.w("SignIn", response.message());
                        Toast.makeText(OnBoardingActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SignInResponse> call, Throwable t) {
                    Toast.makeText(OnBoardingActivity.this, "요청 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}

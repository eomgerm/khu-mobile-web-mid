package service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.SharedPreferencesManager;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:8000";

    public static Retrofit getRetrofitInstance(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new RequestInterceptor(context)).build();

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.KOREA));
        }
    }

    static class RequestInterceptor implements Interceptor {
        private final Context context;
        private final static String AUTHORIZATION = "Authorization";
        private final static String BEARER = "Bearer";

        RequestInterceptor(Context context) {
            this.context = context;
        }


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            String accessToken = SharedPreferencesManager.getAccessToken(context);
            if (!accessToken.isEmpty()) {
                builder.addHeader(AUTHORIZATION, BEARER + " " + accessToken);
            }

            return chain.proceed(builder.build());
        }

    }
}

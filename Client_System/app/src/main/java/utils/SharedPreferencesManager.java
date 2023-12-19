package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String PREFERENCES_NAME = "GLOBAL";
    private static SharedPreferences pref;

    public static void setAccessToken(Context context, String accessToken) {
        pref = getPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("accessToken", accessToken);

        editor.apply();
    }

    public static void removeAccessToken(Context context) {
        pref = getPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("accessToken");

        editor.apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences pref = getPreferences(context);
        return pref.getString("accessToken", "");
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}

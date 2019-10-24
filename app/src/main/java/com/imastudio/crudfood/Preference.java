package com.imastudio.crudfood;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

        private static SharedPreferences sharedPreferences;
        private static SharedPreferences.Editor editor;
        private static String KEY_EMAIL = "EMAIL";
        private static String KEY_PASS = "PASSWORD";

        public static void actionLogin(Context context, String email, String password){

            sharedPreferences = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASS, password);
            editor.apply();
        }

        public static void actionLogout(Context context){
            sharedPreferences = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.clear().apply();
        }

        public static String getEmail(Context context){
            sharedPreferences = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_EMAIL, null);
        }

        public static String getPassword(Context context){
            sharedPreferences = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_PASS, null);
        }
    }
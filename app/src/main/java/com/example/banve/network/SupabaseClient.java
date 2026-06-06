package com.example.banve.network;

import com.example.banve.config.SupabaseConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class SupabaseClient {
    private static Retrofit retrofit;
    private static ApiService apiService;

    private SupabaseClient() {
    }

    public static ApiService layApiService() {
        if (apiService == null) {
            apiService = layRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    public static Retrofit layRetrofit() {
        if (retrofit == null) {
            if (SupabaseConfig.SUPABASE_URL.trim().isEmpty()) {
                throw new IllegalStateException("Chưa cấu hình SUPABASE_URL trong SupabaseConfig.");
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(chuanHoaBaseUrl(SupabaseConfig.SUPABASE_URL))
                    .client(taoOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient taoOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerInterceptor = chain -> chain.proceed(
                chain.request()
                        .newBuilder()
                        .addHeader("apikey", SupabaseConfig.SUPABASE_KEY)
                        .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Prefer", "return=representation")
                        .build()
        );

        return new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private static String chuanHoaBaseUrl(String supabaseUrl) {
        String url = supabaseUrl.trim();
        return url.endsWith("/") ? url : url + "/";
    }
}

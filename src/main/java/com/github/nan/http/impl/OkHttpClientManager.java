//package com.github.nan.http.impl;
//
//import okhttp3.OkHttpClient;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author NanNan Wang
// */
//public class OkHttpClientManager {
//
//    private final OkHttpClient okHttpClient;
//
//    public OkHttpClientManager() {
//        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
////                .addInterceptor(loggingInterceptor)
//                .build();
//    }
//
//    public OkHttpClient getOkHttpClient() {
//        return okHttpClient;
//    }
//}

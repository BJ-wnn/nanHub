//package com.github.nan.http.impl;
//
//import okhttp3.*;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Consumer;
//
///**
// * @author NanNan Wang
// */
//public class ChainableHttpClient {
//
//    private String url;
//    private String method;
//    private Map<String, String> headers = new HashMap<>();
//    private Map<String, String> params = new HashMap<>();
//    private Consumer<Response> successHandler;
//    private Consumer<Exception> exceptionHandler;
//
//    private OkHttpClient okHttpClient = new OkHttpClient();
//
//    // 构造器私有化，防止外部直接实例化
//    private ChainableHttpClient() {}
//
//    public static ChainableHttpClient create() {
//        return new ChainableHttpClient();
//    }
//
//    // 设置URL
//    public ChainableHttpClient url(String url) {
//        this.url = url;
//        return this;
//    }
//
//    // 设置请求方法（GET, POST, etc.）
//    public ChainableHttpClient method(String method) {
//        this.method = method;
//        return this;
//    }
//
//
//    // 设置请求头
//    public ChainableHttpClient addHeader(String key, String value) {
//        this.headers.put(key, value);
//        return this;
//    }
//
//    // 设置请求参数
//    public ChainableHttpClient addParam(String key, String value) {
//        this.params.put(key, value);
//        return this;
//    }
//
//    // 设置成功处理器
//    public ChainableHttpClient onSuccess(Consumer<Response> handler) {
//        this.successHandler = handler;
//        return this;
//    }
//
//    // 设置异常处理器
//    public ChainableHttpClient onError(Consumer<Exception> handler) {
//        this.exceptionHandler = handler;
//        return this;
//    }
//
//    // 发送请求
//    public void execute() {
//        RequestBody requestBody = null;
//
//        // 构建请求体
//        if ("POST".equalsIgnoreCase(method)) {
//            FormBody.Builder formBodyBuilder = new FormBody.Builder();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                formBodyBuilder.add(entry.getKey(), entry.getValue());
//            }
//            requestBody = formBodyBuilder.build();
//        }
//
//
//        // 构建请求
//        Request.Builder requestBuilder = new Request.Builder().url(url);
//        for (Map.Entry<String, String> entry : headers.entrySet()) {
//            requestBuilder.addHeader(entry.getKey(), entry.getValue());
//        }
//
//        if ("POST".equalsIgnoreCase(method) && requestBody != null) {
//            requestBuilder.post(requestBody);
//        } else {
//            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
//            }
//            requestBuilder.url(urlBuilder.build());
//        }
//
//        Request request = requestBuilder.build();
//
//        // 执行请求
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (exceptionHandler != null) {
//                    exceptionHandler.accept(e);
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (successHandler != null) {
//                    successHandler.accept(response);
//                }
//            }
//        });
//    }
//
//}

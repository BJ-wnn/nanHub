package com.github.nan.http.impl;

import com.github.nan.http.AsyncHttpClient;
import com.github.nan.http.dto.RequestHeaders;
import com.github.nan.http.dto.RequestParams;
import okhttp3.*;

import java.util.Map;

/**
 * @author NanNan Wang
 */
public class OkHttpAsyncClient implements AsyncHttpClient {

    private final OkHttpClient client;

    public OkHttpAsyncClient() {
        this.client = new OkHttpClient();
    }

    @Override
    public void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void get(String url, RequestParams params, Callback callback) {
        Request request = new Request.Builder()
                .url(buildUrlWithParams(url, params))
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void get(String url, RequestHeaders headers, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers.getHeaders()))
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void get(String url, RequestHeaders headers, RequestParams params, Callback callback) {
        Request request = new Request.Builder()
                .url(buildUrlWithParams(url, params))
                .headers(Headers.of(headers.getHeaders()))
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void postJson(String url, String requestBody, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void postJson(String url, RequestHeaders headers, String requestBody, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers.getHeaders()))
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void postForm(String url, RequestParams formData, Callback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : formData.getParams().entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue().toString());
        }
        RequestBody body = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void postForm(String url, RequestHeaders headers, RequestParams formData, Callback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : formData.getParams().entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue().toString());
        }
        RequestBody body = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers.getHeaders()))
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private String buildUrlWithParams(String url, RequestParams params) {
        StringBuilder sb = new StringBuilder(url);
        boolean isFirstParam = true;
        for (Map.Entry<String, Object> entry : params.getParams().entrySet()) {
            if (isFirstParam) {
                sb.append("?");
                isFirstParam = false;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
}

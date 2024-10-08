package com.github.nan.http.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nan.http.SyncHttpClient;
import com.github.nan.http.dto.RequestHeaders;
import com.github.nan.http.dto.RequestParams;
import com.github.nan.http.exception.HttpClientException;
import okhttp3.*;
import java.io.IOException;
import java.util.Map;

/**
 * @author NanNan Wang
 */
public class OkHttpSyncClient implements SyncHttpClient {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public OkHttpSyncClient() {
        okHttpClient = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    @Override
    public <T> T get(String url, Class<T> responseType) {
        return executeGetRequest(url, null, null, responseType);
    }

    @Override
    public <T> T get(String url, RequestParams params, Class<T> responseType) {
        return executeGetRequest(url, null, params, responseType);
    }

    @Override
    public <T> T get(String url, RequestHeaders headers, Class<T> responseType) {
        return executeGetRequest(url, headers, null, responseType);
    }

    @Override
    public <T> T get(String url, RequestHeaders headers, RequestParams params, Class<T> responseType) {
        return executeGetRequest(url, headers, params, responseType);
    }

    @Override
    public <T> T postJson(String url, String requestBody, Class<T> responseType) {
        return executePostJsonRequest(url, null, requestBody, responseType);
    }

    @Override
    public <T> T postJson(String url, RequestHeaders headers, String requestBody, Class<T> responseType) {
        return executePostJsonRequest(url, headers, requestBody, responseType);
    }

    @Override
    public <T> T postForm(String url, RequestParams formData, Class<T> responseType) {
        return executePostFormRequest(url, null, formData, responseType);
    }

    @Override
    public <T> T postForm(String url, RequestHeaders headers, RequestParams formData, Class<T> responseType) {
        return executePostFormRequest(url, headers, formData, responseType);
    }

    private <T> T executeGetRequest(String url, RequestHeaders headers, RequestParams params, Class<T> responseType) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.getParams().entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build());

        if (headers != null) {
            requestBuilder.headers(Headers.of(headers.getHeaders()));
        }

        Request request = requestBuilder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new HttpClientException("请求失败: " + response.code(),null);
            }
            String responseBody = response.body().string();
            return parseResponse(responseBody, responseType);
        } catch (IOException e) {
            throw new HttpClientException("请求失败: " + e.getMessage(),e);
        }
    }

    private <T> T executePostJsonRequest(String url, RequestHeaders headers, String requestBody, Class<T> responseType) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        if (headers != null) {
            requestBuilder.headers(Headers.of(headers.getHeaders()));
        }

        Request request = requestBuilder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new HttpClientException("请求失败: " + response.code(),null);
            }
            String responseBody = response.body().string();
            return parseResponse(responseBody, responseType);
        } catch (IOException e) {
            throw new HttpClientException("请求失败: " + e.getMessage(),e);
        }
    }

    private <T> T executePostFormRequest(String url, RequestHeaders headers, RequestParams formData, Class<T> responseType) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (formData != null) {
            for (Map.Entry<String, Object> entry : formData.getParams().entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = formBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        if (headers != null) {
            requestBuilder.headers(Headers.of(headers.getHeaders()));
        }

        Request request = requestBuilder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new HttpClientException("请求失败: " + response.code(),null);
            }
            String responseBody = response.body().string();
            return parseResponse(responseBody, responseType);
        } catch (IOException e) {
            throw new HttpClientException("请求失败: " + e.getMessage(),e);
        }
    }

    private <T> T parseResponse(String responseBody, Class<T> responseType) {
        try {
            // 如果响应类型为 String，则直接返回
            if (responseType.equals(String.class)) {
                return (T) responseBody;
            }

            if (null == responseType || responseType.equals(Void.class)) {
                // 不需要做任何事情
                return null;
            }

            // 使用 Jackson 解析 JSON 响应
            return objectMapper.readValue(responseBody, responseType);
        } catch (Exception e) {
            throw new HttpClientException("JSON 解析失败: " + e.getMessage(), e);
        }
    }
}


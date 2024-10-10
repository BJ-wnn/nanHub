package com.github.nan.http.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nan.http.HttpClient;
import com.github.nan.http.constant.HttpMethod;
import com.github.nan.http.exception.HttpClientException;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NanNan Wang
 */
public class HttpClientImpl implements HttpClient {

    private String url;
    private HttpMethod method = HttpMethod.GET; // 默认是GET
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private String jsonBody;
    private Map<String, String> formBody;
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public HttpClient url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public HttpClient method(HttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpClient addHeader(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    @Override
    public HttpClient addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    @Override
    public HttpClient queryParam(String key, String value) {
        queryParams.put(key, value);
        return this;
    }

    @Override
    public HttpClient jsonBody(String json) {
        this.jsonBody = json;
        return this;
    }

    @Override
    public HttpClient formBody(Map<String, String> formData) {
        this.formBody.putAll(formData);
        return this;
    }

    @Override
    public HttpClient formBody(String key, String value) {
        this.formBody.put(key, value);
        return this;
    }

    @Override
    public <T> T execute(Class<T> responseType) {
        Request request = buildRequest();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new HttpClientException("HTTP请求失败: " + response.code(),null);
            }
            return parseResponse(response, responseType);
        } catch (IOException e) {
            throw new HttpClientException("HTTP请求失败: " + e.getMessage(),e);
        }
    }

    @Override
    public <T> void executeAsync(Class<T> responseType, Callback<T> callback) {
        Request request = buildRequest();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    callback.onFailure(new RuntimeException("HTTP请求失败: " + response.code()));
                    return;
                }
                try {
                    T parsedResponse = parseResponse(response, responseType);
                    callback.onSuccess(parsedResponse);
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }
        });
    }


    // 构建请求
    private Request buildRequest() {
        Request.Builder builder = new Request.Builder().url(buildUrlWithParams());

        if (headers != null) {
            Headers okHttpHeaders = Headers.of(headers);
            builder.headers(okHttpHeaders);
        }

        if (method == HttpMethod.POST) {
            if (jsonBody != null) {
                RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=utf-8"));
                builder.method(method.name(), body);
            } else if (formBody != null) {
                FormBody.Builder formBuilder = new FormBody.Builder();
                formBody.forEach(formBuilder::add);
                builder.method(method.name(), formBuilder.build());
            }
        } else {
            builder.method(method.name(), null);
        }

        return builder.build();
    }


    // 处理GET请求时附加查询参数
    private String buildUrlWithParams() {
        if (queryParams.isEmpty()) {
            return url;
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        queryParams.forEach(urlBuilder::addQueryParameter);
        return urlBuilder.build().toString();
    }

    // 解析响应
    private <T> T parseResponse(Response response, Class<T> responseType) throws IOException {
        String responseBody = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, responseType);
    }

}

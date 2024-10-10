package com.github.nan.http;


import com.github.nan.http.constant.HttpMethod;

import java.util.Map;

/**
 * 模仿Stream 实现一个链式调用的http客户端。
 *
 *
 * @author NanNan Wang
 */
public interface HttpClient {

    // 设置URL
    HttpClient url(String url);

    // 指定请求方法，为了代码可读性，建议调用时显示声明
    HttpClient method(HttpMethod method);

    // 添加请求头
    HttpClient addHeader(Map<String, String> headers);

    // 添加请求头
    HttpClient addHeader(String key, String value);

    // 设置请求参数（适用于 GET 请求）
    HttpClient queryParam(String key, String value);

    // 设置JSON请求体（适用于 POST、PUT 等请求）
    HttpClient jsonBody(String json);

    // 设置Form表单请求体
    HttpClient formBody(Map<String, String> formData);

    // 设置Form表单请求参数
    HttpClient formBody(String key, String value);

    // 执行请求
    <T> T execute(Class<T> responseType);

    // 异步请求执行，带有回调
    <T> void executeAsync(Class<T> responseType, Callback<T> callback);

    // 回调接口
    interface Callback<T> {
        void onSuccess(T response);
        void onFailure(Exception e);
    }
}

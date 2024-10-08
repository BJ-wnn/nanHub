package com.github.nan.http;

import com.github.nan.http.dto.RequestHeaders;
import com.github.nan.http.dto.RequestParams;
import okhttp3.Callback;

/**
 * 异步http客户端
 *
 * @author NanNan Wang
 */
public interface AsyncHttpClient {

    /**
     * 发起异步 GET 请求。
     *
     * @param url 请求 URL
     * @param callback 回调函数
     */
    void get(String url, Callback callback);

    /**
     * 发起一个带有头部的 GET 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param callback 回调函数
     */
    void get(String url, RequestHeaders headers, Callback callback);



    /**
     * 发起一个带有参数的 GET 请求。
     *
     * @param url 请求的 URL 地址
     * @param params 请求的参数
     * @param callback 回调函数
     */
    void get(String url, RequestParams params,  Callback callback);


    /**
     * 发起一个带有头部和参数的 GET 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param params 请求的参数
     * @param callback 回调函数
     */
    void get(String url, RequestHeaders headers, RequestParams params, Callback callback);


    /**
     * 发起一个带有 JSON 请求参数的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param requestBody JSON 请求体
     * @param callback 回调函数
     */
    void postJson(String url, String requestBody, Callback callback);

    /**
     * 发起一个带有 JSON 请求参数和头部信息的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param requestBody JSON 请求体
     * @param callback 回调函数
     */
    void postJson(String url, RequestHeaders headers, String requestBody, Callback callback);

    /**
     * 发起一个带有表单数据的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param formData 表单数据
     * @param callback 回调函数
     */
    void postForm(String url, RequestParams formData, Callback callback);

    /**
     * 发起一个带有表单数据和头部信息的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param formData 表单数据
     * @param callback 回调函数
     */
    void postForm(String url, RequestHeaders headers, RequestParams formData, Callback callback);


}

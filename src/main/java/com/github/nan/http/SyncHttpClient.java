package com.github.nan.http;

import com.github.nan.http.dto.RequestHeaders;
import com.github.nan.http.dto.RequestParams;

/**
 * 同步http客户端
 *
 * 如果请求没有返回结果，responseType 建议使用 Void.class
 *
 * @author NanNan Wang
 */
public interface SyncHttpClient {

    /**
     * 发起一个简单的 GET 请求。
     *
     * @param url 请求的 URL 地址
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T get(String url, Class<T> responseType);

    /**
     * 发起一个带有参数的 GET 请求。
     *
     * @param url 请求的 URL 地址
     * @param params 请求的参数
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T get(String url, RequestParams params, Class<T> responseType);

    /**
     * 发起一个带有头部的 GET 请求。
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param responseType 返回的结果类型
     * @return 泛型类型
     * @param <T> 请求的结果，类型为 T
     */
    <T> T get(String url, RequestHeaders headers, Class<T> responseType);


    /**
     * 发起一个带有头部和参数的 GET 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param params 请求的参数
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T get(String url, RequestHeaders headers,  RequestParams params, Class<T> responseType);

    /**
     * 发起一个带有 JSON 请求参数的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param requestBody JSON 请求体
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T postJson(String url, String requestBody, Class<T> responseType);



    /**
     * 发起一个带有 JSON 请求参数的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param requestBody JSON 请求体
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T postJson(String url, RequestHeaders headers, String requestBody, Class<T> responseType);


    /**
     * 发起一个带有表单数据的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param formData 表单数据
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T postForm(String url, RequestParams formData, Class<T> responseType);

    /**
     * 发起一个带有表单数据的 POST 请求。
     *
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param formData 表单数据
     * @param responseType 返回的结果类型
     * @param <T> 泛型类型
     * @return 请求的结果，类型为 T
     */
    <T> T postForm(String url, RequestHeaders headers, RequestParams formData, Class<T> responseType);


}

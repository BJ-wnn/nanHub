package com.github.nan.http;

import java.util.Map;

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
     * 发起一个带有头部的 GET 请求。
     * @param url 请求的 URL 地址
     * @param headers 请求的头部信息
     * @param responseType 返回的结果类型
     * @return 泛型类型
     * @param <T> 请求的结果，类型为 T
     */
    <T> T get(String url, RequestHeaders headers,Class<T> responseType);


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



}

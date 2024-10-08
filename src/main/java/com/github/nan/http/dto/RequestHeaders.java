package com.github.nan.http.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Http 请求请求头实体类
 *
 * 优点是提高代码的可读性和可维护性
 * 使用 Builder 模式改进，优点是 1.链式调用 2.灵活扩展 3.不可变对象
 *
 * @author NanNan Wang
 */
public class RequestHeaders {

    private final Map<String, String> headers;

    // 私有构造函数，只能通过 Builder 创建
    private RequestHeaders(Builder builder) {
        this.headers = builder.headers;
    }

    // 静态内部 Builder 类
    public static class Builder {
        private final Map<String, String> headers = new HashMap<>();

        // 提供添加单个header的方法
        public Builder addHeader(String key, String value) {
            headers.put(key, value);
            return this; // 返回自身支持链式调用
        }

        // 批量添加headers
        public Builder addHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this; // 返回自身支持链式调用
        }

        // 构建RequestHeaders对象
        public RequestHeaders build() {
            return new RequestHeaders(this);
        }
    }

    // 方便判断是否包含某个header
    public boolean containsHeader(String key) {
        return this.headers != null && this.headers.containsKey(key);
    }

    // 获取某个header的值
    public String getHeader(String key) {
        return this.headers != null ? this.headers.get(key) : null;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }
}

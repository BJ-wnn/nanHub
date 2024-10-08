package com.github.nan.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NanNan Wang
 */
public class RequestParams {

    private final Map<String, Object> params;

    private RequestParams(Builder builder) {
        this.params = new HashMap<>(builder.params);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public static class Builder {
        private Map<String, Object> params;

        public Builder() {
            this.params = new HashMap<>();
        }

        public Builder addParam(String key, Object value) {
            params.put(key, value);
            return this;
        }
        public Builder addParams(Map<String, String> params) {
            this.params.putAll(params);
            return this; // 返回自身支持链式调用
        }

        public RequestParams build() {
            return new RequestParams(this);
        }
    }

}

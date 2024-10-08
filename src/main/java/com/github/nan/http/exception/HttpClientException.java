package com.github.nan.http.exception;

/**
 * @author NanNan Wang
 */
public class HttpClientException extends RuntimeException{
    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

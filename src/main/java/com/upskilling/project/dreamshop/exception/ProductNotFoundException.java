package com.upskilling.project.dreamshop.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
         super(message);
    }
}

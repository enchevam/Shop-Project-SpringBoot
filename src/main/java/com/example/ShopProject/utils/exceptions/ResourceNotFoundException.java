package com.example.ShopProject.utils.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }


    public ResourceNotFoundException(String product, String id, Long productId) {
    }
}
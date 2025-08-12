package com.upskilling.project.dreamshop.exception;

import java.util.function.Supplier;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}

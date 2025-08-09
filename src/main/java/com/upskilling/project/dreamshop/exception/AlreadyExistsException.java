package com.upskilling.project.dreamshop.exception;

import java.util.function.Supplier;

public class AlreadyExistsException extends RuntimeException implements Supplier<@org.jetbrains.annotations.NotNull X> {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}

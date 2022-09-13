package com.mehdi;

import java.util.Optional;

public interface AbstractFactory<T> {
    Optional<T> create(String type);
}
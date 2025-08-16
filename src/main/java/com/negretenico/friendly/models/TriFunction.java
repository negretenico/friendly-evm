package com.negretenico.friendly.models;
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u , V v);
}

package com.mairwunnx.application.contracts;

@FunctionalInterface
public interface SupplierConsumer<R, T> {

    R acceptAndReturn(T t);

}

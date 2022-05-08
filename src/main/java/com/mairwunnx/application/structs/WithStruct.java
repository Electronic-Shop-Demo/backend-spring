package com.mairwunnx.application.structs;

import com.mairwunnx.application.contracts.SupplierConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class WithStruct<T> {

    @SafeVarargs
    private WithStruct(final T... t) {
        values.addAll(Arrays.asList(t));
    }

    public static final class WithStructApplier<T> {

        private final WithStruct<T> withStruct;

        @SafeVarargs
        private WithStructApplier(final T... t) {
            withStruct = new WithStruct<>(t);
        }

        @SuppressWarnings("unchecked")
        public <R> WithStruct<R> apply(@NotNull final SupplierConsumer<R, T> consumer) {
            final List<R> mappedValues = new LinkedList<>();
            for (final T element : withStruct.values) {
                mappedValues.add(consumer.acceptAndReturn(element));
            }
            return new WithStruct<>((R) mappedValues.toArray(new Object[0]));
        }

    }

    private final List<T> values = new LinkedList<>();

    public T get(final int index) {
        return values.get(index);
    }

    @SafeVarargs
    public static <T> WithStructApplier<T> with(final T... t) {
        return new WithStructApplier<>(t);
    }

}

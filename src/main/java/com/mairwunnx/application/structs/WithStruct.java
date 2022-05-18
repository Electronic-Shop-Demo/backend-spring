package com.mairwunnx.application.structs;

import com.google.common.collect.ImmutableList;
import com.mairwunnx.application.contracts.SupplierConsumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public final class WithStruct<T> {

    @SafeVarargs
    private WithStruct(final T... t) {
        values = ImmutableList.copyOf(t);
    }

    public static final class WithStructApplier<T> {

        private final WithStruct<T> withStruct;

        @SafeVarargs
        private WithStructApplier(final T... t) {
            withStruct = new WithStruct<>(t);
        }

        @Contract("_ -> new")
        @SuppressWarnings("unchecked")
        public <R> @NotNull WithStruct<R> apply(@NotNull final SupplierConsumer<@NotNull R, @NotNull T> consumer) {
            final List<R> mappedValues = new LinkedList<>();
            for (final T element : withStruct.values) {
                mappedValues.add(consumer.acceptAndReturn(element));
            }
            return new WithStruct<>((R) mappedValues.toArray(new Object[0]));
        }

    }

    private final ImmutableList<T> values;

    public @NotNull T get(final int index) {
        if (values.isEmpty() || index > values.size() - 1) {
            throw new NullPointerException("With struct has no value with index: %s but %s".formatted(index, values.size()));
        }
        return values.get(index);
    }

    public @Nullable T getOrNull(final int index) {
        if (values.isEmpty() || index > values.size() - 1) {
            return null;
        }
        return values.get(index);
    }

    @SafeVarargs
    @Contract("_ -> new")
    public static <T> @NotNull WithStructApplier<T> with(@NotNull final T... t) {
        return new WithStructApplier<>(t);
    }

}

package com.mairwunnx.application.utils;

import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public final class StringUtils {

    @ParametersAreNonnullByDefault
    public static @NotNull String getBeforeLast(final String input, final char c) {
        final StringBuilder result = new StringBuilder();
        var counter = input.length() - 1;

        while (true) {
            final var currentChar = input.charAt(counter--);
            if (currentChar == c) {
                break;
            } else {
                result.insert(0, currentChar);
                if (counter == -1) {
                    break;
                }
            }
        }

        return result.toString();
    }

}

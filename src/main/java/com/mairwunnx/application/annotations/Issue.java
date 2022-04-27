package com.mairwunnx.application.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(value = Issues.class)
public @interface Issue {

    long id();

    @NotNull String value();

}

@Documented
@Target(ElementType.TYPE)
@interface Issues {

    @NotNull Issue[] value();

}

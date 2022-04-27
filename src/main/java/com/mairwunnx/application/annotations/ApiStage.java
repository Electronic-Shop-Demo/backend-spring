package com.mairwunnx.application.annotations;

import com.mairwunnx.application.annotations.type.ApiStageType;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ApiStage {

    @NotNull ApiStageType value();

}

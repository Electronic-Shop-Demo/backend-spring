package com.mairwunnx.application.valid;

import javax.annotation.ParametersAreNonnullByDefault;

interface ValidatorBase<T> {

    @ParametersAreNonnullByDefault
    void validate(final T candidate);

}

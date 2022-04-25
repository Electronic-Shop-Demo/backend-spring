package com.mairwunnx.application.wrappers;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public record UserId(UUID id) {

}

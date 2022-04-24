package com.mairwunnx.application;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.ParametersAreNonnullByDefault;

@Log4j2
@SpringBootApplication
public class ZygoteInit {

    @ParametersAreNonnullByDefault
    public static void main(final String[] args) {
        final var context = SpringApplication.run(ZygoteInit.class, args);

        try (context) {
            log.info("Application {} has started", context.getApplicationName());
        } catch (final Exception ex) {
            log.error("Can't start application", ex);
            System.exit(-1);
        }
    }

}

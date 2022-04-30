package com.mairwunnx.application;

import io.mongock.runner.springboot.EnableMongock;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.ParametersAreNonnullByDefault;

@Log4j2
@EnableMongock
@SpringBootApplication
@SuppressWarnings("resource")
public class ZygoteInit {

    @ParametersAreNonnullByDefault
    public static void main(final String[] args) {
        try {
            final var context = SpringApplication.run(ZygoteInit.class, args);
            log.info("Application {} has started", context.getApplicationName());
        } catch (final Exception ex) {
            if (ex.getClass().getName().contains("SilentExitException")) {
                log.warn("Silent error: spring is restarting the main thread - See spring-boot-devtools");
            } else {
                log.error("Can't start application", ex);
                System.exit(-1);
            }
        }
    }

}

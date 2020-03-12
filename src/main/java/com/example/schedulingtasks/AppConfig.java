package com.example.schedulingtasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

@Configuration
@Slf4j
public class AppConfig {


    @Bean
    public ForkJoinPool customForkJoinPool() {
        return new ForkJoinPool(
                2,
                factory(),
                handler(),
                false
        );
    }

    private ForkJoinPool.ForkJoinWorkerThreadFactory factory() {
        return (pool) -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("Custom" + worker.getId());
            worker.setDaemon(false);
            return worker;
        };
    }

    private Thread.UncaughtExceptionHandler handler() {
        return (t, e) -> {
            log.error(t.getName() + e);
        };
    }


}

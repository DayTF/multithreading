package com.telecom.sensors;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ThreadInitializer implements ApplicationRunner {

    private final Configuration configuration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        for (String directory : configuration.getProcess()) {
            executor.scheduleAtFixedRate(new DirectoryScanner(directory, configuration.getTreated()), 0, 20, TimeUnit.SECONDS);
        }

        executor.scheduleAtFixedRate(new Archiver(configuration.getTreated(), configuration.getArchive()), 0, 30, TimeUnit.SECONDS);
    }
}

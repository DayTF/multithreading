package com.telecom.sensors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
public class FileProcessor implements Runnable {

    private final File file;
    private final String destination;


    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                MeasureRepository.save(sc.nextLine());
            }
            sc.close();
            Path source = Paths.get(file.getAbsolutePath());
            Path target = Paths.get(destination + "/" + file.getName());
            Files.move(source, target);
        } catch (IOException e) {
            log.error("Error processing file " + file.getAbsolutePath() + " - " + e.getMessage());
        }
    }
}

package com.telecom.sensors;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DirectoryScanner implements Runnable {

    private final File directory;
    private final String destination;
    private final ThreadPoolExecutor executor;

    public DirectoryScanner(String directory, String destination) {
        this.directory = new File(directory);
        this.destination = destination;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    }

    @Override
    public void run() {
        File[] files = directory.listFiles(new FileFilter() {
            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith(".txt");
            }
        });
        for (File file : files) {
            executor.submit(new FileProcessor(file, destination));
        }
    }

}

package com.telecom.sensors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@AllArgsConstructor
public class Archiver implements Runnable {

    private final String source;
    private final String destination;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HHmmss");

    @Override
    public void run() {
        try {
            File sourceDirectory = new File(source);
            File[] files = sourceDirectory.listFiles(new FileFilter() {
                public boolean accept(File f) {
                    return f.isFile() && f.getName().endsWith(".txt");
                }
            });
            if (files != null && files.length > 0) {
                File zip = new File(destination + "/" + formatter.format(new Date()) + ".zip");
                ZipOutputStream out = null;
                out = new ZipOutputStream(new FileOutputStream(zip));
                for (File file : files) {
                    out.putNextEntry(new ZipEntry(file.getName()));
                    byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                    out.write(data, 0, data.length);
                    out.closeEntry();
                    file.delete();
                }
                out.close();
            }
        } catch (IOException e) {
            log.error("Error creating archive : " + e.getMessage());
        }
    }
}

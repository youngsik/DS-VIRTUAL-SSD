package com.samsung.log;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
public class LogCompressor {
    private static final String LOG_DIR = "logs";

    public void compressIfNeeded() {
        File[] logFiles = new File(LOG_DIR).listFiles((dir, name) ->
                name.startsWith("until_") && name.endsWith(".log"));

        if (logFiles == null || logFiles.length < 2) return;

        Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified));
        File oldest = logFiles[0];
        File zipped = new File(oldest.getAbsolutePath().replace(".log", ".zip"));

        if (oldest.renameTo(zipped)) {
            log.info("Compressed {} → {}", oldest.getName(), zipped.getName());
        }
    }
}

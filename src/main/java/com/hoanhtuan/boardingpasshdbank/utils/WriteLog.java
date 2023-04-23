package com.hoanhtuan.boardingpasshdbank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteLog {
    private static final Logger LOG = LoggerFactory.getLogger(WriteLog.class);

    private WriteLog() {
        throw new IllegalStateException("Utility class");
    }

    public static void write(String message) {
        LOG.info(message);
    }
}

package com.hoanhtuan.boardingpasshdbank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteLog<T> {

    private static final Logger LOG = LoggerFactory.getLogger(WriteLog.class);

    private WriteLog() {
    }

    public static void write(String message) {
        LOG.info(message);
    }
}

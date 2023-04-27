package com.hoanhtuan.boardingpasshdbank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
public class WriteLog {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteLog.class);

    private WriteLog() {}

    public static void infoLog(String message){
        LOGGER.info(message);
=======
public class WriteLog<T> {

    private static final Logger LOG = LoggerFactory.getLogger(WriteLog.class);

    private WriteLog() {
>>>>>>> thanhcongdev
    }

    public static void infoLog(String className, String methodName, Object data) {
        LOGGER.info("[{}] [{}]: {}", className, methodName, data != null ? data.toString() : null);
    }

    public static void errorLog(String className, String methodName, String messageError) {
        LOGGER.error("[{}] [{}] [{}]", className, methodName, messageError);
    }

    public static void errorLog(String className, String methodName, String messageError, Throwable ex) {
        LOGGER.error("[{}] [{}] [{}]", className, methodName, messageError, ex);
    }

}

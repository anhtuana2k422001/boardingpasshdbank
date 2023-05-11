package vn.com.hdbank.boardingpasshdbank.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteLog {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteLog.class);

    private WriteLog() {}

    public static void infoLog(Class<?> clazz,  String message, Object object) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info("{} {}", message, object);
    }

    public static void infoLog(String message){
        LOGGER.info(message);
    }

    public static void infoLog(String className, String methodName, Object... data) {
        LOGGER.info("[{}] [{}]: {}", className, methodName, data);
    }

    public static void errorLog(String className, String methodName, String messageError) {
        LOGGER.error("[{}] [{}] [{}]", className, methodName, messageError);

    }

    public static void errorLog(String className, String methodName, String messageError, Throwable ex) {
        LOGGER.error("[{}] [{}] [{}]", className, methodName, messageError, ex);
    }

}

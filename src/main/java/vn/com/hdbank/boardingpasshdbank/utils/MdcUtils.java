package vn.com.hdbank.boardingpasshdbank.utils;

import org.slf4j.MDC;

import java.util.UUID;

public class MdcUtils {
    private MdcUtils(){}
    public static void setRequestId(String requestId) {
        if (Utils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put("requestId", requestId);
    }
}

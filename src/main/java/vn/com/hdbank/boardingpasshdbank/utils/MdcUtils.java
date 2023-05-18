package vn.com.hdbank.boardingpasshdbank.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

public class MdcUtils {
    private MdcUtils(){}
    public static void setRequestId(String requestId) {
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put("requestId", requestId);
    }
}

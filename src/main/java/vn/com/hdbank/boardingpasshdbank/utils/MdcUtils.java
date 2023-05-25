package vn.com.hdbank.boardingpasshdbank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import vn.com.hdbank.boardingpasshdbank.common.Constant;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MdcUtils {
    public static void setRequestId(String requestId) {
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put(Constant.REQUEST_ID, requestId);
    }
}

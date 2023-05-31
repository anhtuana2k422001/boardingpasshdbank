package vn.com.hdbank.boardingpasshdbank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import vn.com.hdbank.boardingpasshdbank.common.Constant;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtils {
    public static String logHttpGet(HttpGet request) {
        String url = StringUtils.join(Constant.URL, request.getURI().toString());
        String method = StringUtils.join(Constant.METHOD, request.getMethod());
        String headers = StringUtils.join(Constant.HEADER, Arrays.toString(request.getAllHeaders()));
        return StringUtils.join(url, Constant.COMMA_SPACE, method, Constant.COMMA_SPACE, headers);
    }

    public static String logHttpPost(HttpPost request) {
        String url = StringUtils.join(Constant.URL, request.getURI().toString());
        String method = StringUtils.join(Constant.METHOD, request.getMethod());
        String headers = StringUtils.join(Constant.HEADER, Arrays.toString(request.getAllHeaders()));
        return StringUtils.join(url, Constant.COMMA_SPACE, method, Constant.COMMA_SPACE, headers);
    }

}

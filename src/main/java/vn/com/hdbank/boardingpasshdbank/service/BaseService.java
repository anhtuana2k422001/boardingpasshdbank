package vn.com.hdbank.boardingpasshdbank.service;

import vn.com.hdbank.boardingpasshdbank.utils.ApiHttpClient;
import vn.com.hdbank.boardingpasshdbank.utils.JsonUtils;

public class BaseService {
    protected ApiHttpClient apiHttpClient = ApiHttpClient.getInstance();
    protected JsonUtils jsonUtils = JsonUtils.getInstance();
}

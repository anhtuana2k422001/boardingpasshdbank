package com.hoanhtuan.boardingpasshdbank.common;

public final class Constant {
    private Constant() {}
    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String NO_CONTENT = "204";
    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String FORBIDDEN = "403";
    public static final String NOT_FOUND = "404";
    public static final String INTERNAL_SERVER_ERROR = "500";

    public static final String REQUEST_EXCEPTION = "The server encountered an error while processing the request";
    public static final String AUTHORIZING_EXCEPTION = "Error while authenticating user";
    public static final String FORBIDDEN_EXCEPTION = "Request was denied due to lack of access rights to the resource";
    public static final String JSON_ERROR = "Error when parsing Json string from VietJet server";
    public static final String VIET_JET_ERROR = "Failed to get passenger information from Viet-jet";
}
/*
200 OK: Request was successful
201 Created: Request has successfully created a new resource
204 No Content: Request was successful but no content was returned
400 Bad Request: Request is invalid or missing necessary information
401 Unauthorized: Request was denied due to lack of authentication information or incorrect authentication information
403 Forbidden: Request was denied due to lack of access rights to the resource
404 Not Found: Requested resource was not found
500 Internal Server Error: Server encountered an error while processing the request.
*/
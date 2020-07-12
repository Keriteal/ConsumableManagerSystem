package consts;

public interface HttpStatusCode {
    int HTTP_OK = 200;
    int BAD_REQUEST = 400;
    int UNAUTHORIZED = 401;

    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int METHOD_NOT_ALLOWED = 405;
    int NOT_ACCEPTABLE = 406;
    int LENGTH_REQUIRED = 411;

    int INTERNAL_SERVER_ERROR = 500;
    int NOT_IMPLEMENTED = 501;
    int BAD_GATEWAY = 502;
    int SERVICE_UNAVAILABLE = 503;
}

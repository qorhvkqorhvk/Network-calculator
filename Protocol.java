public class Protocol {
    // STATUS CODES & MESSAGES 
    public static final String STATUS_OK = "200";
    public static final String MSG_OK = "OK";
    public static final String STATUS_BAD_REQUEST = "400";
    public static final String MSG_BAD_REQUEST = "BAD_REQUEST";
    public static final String STATUS_SERVER_ERROR = "500";
    public static final String MSG_SERVER_ERROR = "SERVER_ERROR";

    // FIELD 이름
    public static final String FIELD_VALUE = "VALUE";
    public static final String FIELD_ERROR_TYPE = "ERROR_TYPE";

    // ERROR TYPES
    public static final String ERR_TOO_MANY_ARGUMENTS = "TOO_MANY_ARGUMENTS";
    public static final String ERR_INVALID_ARGUMENTS = "INVALID_ARGUMENTS";
    public static final String ERR_NOT_A_NUMBER = "NOT_A_NUMBER";
    public static final String ERR_DIV_BY_ZERO = "DIV_BY_ZERO";
    public static final String ERR_UNKNOWN_OPERATION = "UNKNOWN_OPERATION";

    // 요청 문자열 파싱
    public static String[] parseRequest(String request) {
        return request.trim().split("\\s+");
    }

    // 성공 응답 생성 (200 OK)
    public static String createResultMessage(double result) {
        return STATUS_OK + " " + MSG_OK + "\n" + FIELD_VALUE + ": " + String.valueOf(result);
    }

    // 오류 응답 생성 (400 또는 500) 
    public static String createErrorMessage(String errorType) {
        String statusCode;
        String statusMsg;
        
        switch (errorType) {
            case ERR_DIV_BY_ZERO:
            case ERR_UNKNOWN_OPERATION:
                statusCode = STATUS_SERVER_ERROR;
                statusMsg = MSG_SERVER_ERROR;
                break;
            default: // TOO_MANY_ARGUMENTS, INVALID_ARGUMENTS, NOT_A_NUMBER
                statusCode = STATUS_BAD_REQUEST;
                statusMsg = MSG_BAD_REQUEST;
                break;
        }
       
        return statusCode + " " + statusMsg + "\n" + FIELD_ERROR_TYPE + ": " + errorType;
    }
}
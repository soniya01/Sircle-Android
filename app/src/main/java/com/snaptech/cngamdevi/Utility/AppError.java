package com.snaptech.cngamdevi.Utility;

/**
 * Created by soniya on 3/30/15.
 */
public class AppError {

    public static int WEBSERVICE_UNKNOWN = 0x111110;
    public static int WEBSERVICE_HTTP = 0x111111;
    public static int WEBSERVICE_CONVERSION = 0x111112;
    public static int WEBSERVICE_NETWORK = 0x111113;
    public static int WEBSERVICE_UNEXPECTED = 0x111114;

    public static final int NO_ERROR = 0x000000;

    private int errorCode;
    private String errorMessage;

    /**
     * Initializes the class with empty value
     */
    public AppError() {
        this.errorCode = NO_ERROR;
        this.errorMessage = "";
    }

    /**
     * Initializes the class with supplied values
     *
     * @param errorCode    - Numeric Error code
     * @param errorMessage - Error message
     */
    public AppError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {

        // User can set any error code except "AppError.NO_ERROR", Shows exception if this condition fails
        if (errorCode == NO_ERROR) {
            try {
                throw new AppErrorUsedException("This is system error code and cannot be used");
            } catch (AppErrorUsedException e) {
                e.printStackTrace();
            }
        }

        this.errorCode = errorCode;
    }

    /**
     * Custom Exception for using default error code
     */
    public class AppErrorUsedException extends Exception {
        public AppErrorUsedException(String message) {
            super(message);
        }
    }
}

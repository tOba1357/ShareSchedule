package action.response;

/**
 * @author Tatsuya Oba
 */
public class StandardResponse {
    private final boolean success;
    private final String message;

    public StandardResponse(
            final boolean success,
            final String message
    ) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public static StandardResponse SUCCESS = new StandardResponse(true, "");
}

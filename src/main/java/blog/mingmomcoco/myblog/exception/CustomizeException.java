package blog.mingmomcoco.myblog.exception;

public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

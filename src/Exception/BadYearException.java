package Exception;

public class BadYearException extends SemanticErrorException{
    public BadYearException() {

    }
    public BadYearException(String message) {
        super(message);
    }
}

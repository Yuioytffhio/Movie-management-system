package Exception;

public class BadScoreException extends SemanticErrorException{
    public BadScoreException() {

    }
    public BadScoreException(String message) {super(message);}
}

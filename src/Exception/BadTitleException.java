package Exception;

public class BadTitleException extends SemanticErrorException{
    public BadTitleException() {}
    public BadTitleException(String message) {super(message);}
}

package Exception;

public class BadGenreException extends SemanticErrorException{
    public BadGenreException(){}
    public BadGenreException (String message) {super(message);}
}

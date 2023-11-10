package baker.james.word.counter.exception;

public class WordMustNotBeNullOrEmptyException extends InvalidWordException {
    public WordMustNotBeNullOrEmptyException() {
        super("Words must not be null or empty");
    }
}

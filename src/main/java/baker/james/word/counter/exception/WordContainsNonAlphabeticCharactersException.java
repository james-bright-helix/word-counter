package baker.james.word.counter.exception;

public class WordContainsNonAlphabeticCharactersException extends InvalidWordException {

    public WordContainsNonAlphabeticCharactersException(String invalidWord) {
        super("Words must not contain non-alphabetic characters: " + invalidWord);
    }
}

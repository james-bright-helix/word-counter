package baker.james.word.counter.service;

import baker.james.word.counter.exception.InvalidWordException;
import baker.james.word.counter.exception.WordContainsNonAlphabeticCharactersException;
import baker.james.word.counter.exception.WordMustNotBeNullOrEmptyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.stream;

@Service
public class WordCounter {
    private final Translator translator;
    private final Map<String, AtomicInteger> wordCount = new ConcurrentHashMap<>();

    public WordCounter(@Autowired Translator translator) {
        this.translator = translator;
    }

    public void add(String... words) throws InvalidWordException {
        for (String word : words) {
            validate(word);
        }
        stream(words)
                .map(WordCounter::normalise)
                .map(translator::translate)
                .forEach(this::countWord);
    }

    private void validate(String word) throws InvalidWordException {
        if (StringUtils.isBlank(word)) {
            throw new WordMustNotBeNullOrEmptyException();
        }
        if (!word.matches("[a-zA-Z]+")) {
            throw new WordContainsNonAlphabeticCharactersException(word);
        }
    }

    public int count(String word) {
        return wordCount.getOrDefault(translator.translate(normalise(word)), new AtomicInteger(0)).get();
    }

    private void countWord(String word) {
        AtomicInteger currentCount = wordCount.computeIfAbsent(word, (k) -> new AtomicInteger(0));
        currentCount.incrementAndGet();
    }

    private static String normalise(String word) {
        return word.toLowerCase(Locale.UK);
    }
}

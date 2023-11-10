package baker.james.word.counter.service;

import baker.james.word.counter.exception.WordContainsNonAlphabeticCharactersException;
import baker.james.word.counter.exception.WordMustNotBeNullOrEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordCounterTest {
    private final Translator translator = mock(Translator.class);
    private final WordCounter wordCounter = new WordCounter(translator);

    @BeforeEach
    void setUp() {
        when(translator.translate(anyString())).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void shouldBeAbleToAddASingleWord() throws Exception {
        wordCounter.add("flower");

        assertThat(wordCounter.count("flower")).isEqualTo(1);
    }

    @Test
    void shouldReturnZeroIfWordNeverAdded() {
        assertThat(wordCounter.count("foo")).isEqualTo(0);
    }

    @Test
    void shouldBeAbleToAddMultipleWordsAtOnce() throws Exception {
        wordCounter.add("flower", "tree");

        assertThat(wordCounter.count("flower")).isEqualTo(1);
        assertThat(wordCounter.count("tree")).isEqualTo(1);
    }

    @Test
    void specifyingTheSameWordTwiceShouldIncrementTheCountTwice() throws Exception {
        wordCounter.add("flower", "flower");

        assertThat(wordCounter.count("flower")).isEqualTo(2);
    }

    @Test
    void shouldBeCaseInsensitive() throws Exception {
        wordCounter.add("flower", "FLOWER", "fLoWeR");

        assertThat(wordCounter.count("Flower")).isEqualTo(3);
    }

    @ParameterizedTest
    @CsvSource({"!", "1", "@", "2", "-", "3", "Boom!"})
    void shouldNotAllowWordsWithNonAlphabeticChars(String nonAlphabeticCharacter) {
        assertThatExceptionOfType(WordContainsNonAlphabeticCharactersException.class)
                .isThrownBy(() -> wordCounter.add(nonAlphabeticCharacter))
                .withMessage("Words must not contain non-alphabetic characters: " + nonAlphabeticCharacter);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"     "})
    void shouldNotAllowNullOrEmpty(String nullOrEmpty) {
        assertThatExceptionOfType(WordMustNotBeNullOrEmptyException.class)
                .isThrownBy(() -> wordCounter.add(nullOrEmpty))
                .withMessage("Words must not be null or empty");
    }

    @Test
    void ifAnyWordIsInvalidThenNonShouldBeCounted() {
        String goodWord = "flower";
        String badWord = "123";
        assertThatExceptionOfType(WordContainsNonAlphabeticCharactersException.class)
                .isThrownBy(() -> wordCounter.add(goodWord, badWord, goodWord))
                .withMessage("Words must not contain non-alphabetic characters: " + badWord);
        assertThat(wordCounter.count(goodWord)).isEqualTo(0);
    }

    @Test
    void shouldCountWordsTogetherIfTheEnglishWordIsTheSame() throws Exception {
        String english = "flower";
        String spanish = "flor";
        when(translator.translate(spanish)).thenReturn(english);

        wordCounter.add(english, spanish);

        assertThat(wordCounter.count(english)).isEqualTo(2);
    }

    @Test
    void shouldGetCountIfRequestedWordIsNotEnglish() throws Exception {
        String english = "flower";
        String spanish = "flor";
        when(translator.translate(spanish)).thenReturn(english);

        wordCounter.add(english, spanish);

        assertThat(wordCounter.count(spanish)).isEqualTo(2);
    }
}

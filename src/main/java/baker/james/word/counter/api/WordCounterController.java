package baker.james.word.counter.api;

import baker.james.word.counter.exception.InvalidWordException;
import baker.james.word.counter.model.CurrentWordCount;
import baker.james.word.counter.service.WordCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordCounterController {
    private final WordCounter wordCounter;

    public WordCounterController(@Autowired WordCounter wordCounter) {
        this.wordCounter = wordCounter;
    }

    @GetMapping(value = "/word-count")
    public @ResponseBody CurrentWordCount get(@RequestParam(value = "word") String word) {
        return new CurrentWordCount(word, wordCounter.count(word));
    }

    @PostMapping(value = "/word-count")
    public @ResponseBody String get(@RequestBody() List<String> words) {
        try {
            wordCounter.add(words.toArray(new String[0]));
            return "ok";
        } catch (InvalidWordException e) {
            return "error";
        }
    }

}

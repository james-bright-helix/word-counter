package baker.james.word.counter.service;

import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.util.CollectionUtils.newHashMap;

//Hacked in for microservice demo
@Service
public class StubTranslator implements Translator {
    private static final Map<String, String> translations = newHashMap(3);

    static {
        translations.put("flor", "flower");
        translations.put("blume", "flower");
    }

    @Override
    public String translate(String word) {
        return translations.getOrDefault(word, word);
    }
}

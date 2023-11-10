package baker.james.word.counter.service;

import baker.james.word.counter.model.CurrentWordCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordCounterAppAcceptanceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void pingEndpointResponds() {
        assertThat(get("/ping", String.class)).contains("pong");
    }

    @Test
    void canGetCountForAWordViaAPI() {
        assertThat(get("/word-count?word=flower", CurrentWordCount.class)).isEqualTo(new CurrentWordCount("flower", 0));
    }

    @Test
    void canSetWordCountViaAPI() {
        ResponseEntity<String> response = restTemplate.postForEntity("/word-count", newArrayList("flower", "tree", "flor", "blume"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(get("/word-count?word=flower", CurrentWordCount.class)).isEqualTo(new CurrentWordCount("flower", 3));
        assertThat(get("/word-count?word=flower", CurrentWordCount.class)).isEqualTo(new CurrentWordCount("tree", 1));
    }

    private <T> T get(String path, Class<T> clazz) {
        return restTemplate.getForObject(baseUri() + path, clazz);
    }

    private String baseUri() {
        return "http://localhost:" + port;
    }
}

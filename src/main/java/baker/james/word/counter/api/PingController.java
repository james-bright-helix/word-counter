package baker.james.word.counter.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PingController {

    @RequestMapping("/ping")
    public @ResponseBody String ping() {
        return "pong";
    }

}

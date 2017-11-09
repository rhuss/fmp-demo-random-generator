package meetup;

import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomNumberEndpoint {

    private static Random random = new Random();

    private static UUID id = UUID.randomUUID();

    @RequestMapping(value = "/", produces = "application/json")
    public Map getRandomNumber() {
        Map ret = new HashMap();
        ret.put("random", random.nextInt());
        ret.put("id", id.toString());
        return ret;
    }
}

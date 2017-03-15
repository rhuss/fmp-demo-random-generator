package meetup.randomclient;

import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author roland
 * @since 15/03/2017
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            try {
                Thread.sleep(1000);
                URL generator = new URL("http://random-generator:8080/random");
                JSONObject response =
                    (JSONObject) new JSONParser().parse(new InputStreamReader(generator.openStream()));
                System.out.println(response.get("id") + ": " + response.get("random"));
            } catch (Exception exp) {
                System.err.println("Error: " + exp);
            }
        }
    }
}

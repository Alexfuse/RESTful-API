package hello;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

import hello.models.Greeting;
import hello.models.Person;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }

    @PostMapping("/postbodytext")
    public String postBody(@RequestBody String data)
    {
        return "Your data: "+data;
    }

    @PostMapping(
            value = "/postbody",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Person> postBody(@RequestBody Person person) {
        return ResponseEntity
                .created(URI
                        .create(String.format("/persons/%s", person.getFirstName())))
                .body(person);
    }


}
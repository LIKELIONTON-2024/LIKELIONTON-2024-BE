package likelion.chur;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chur")
public class ChurController {

    private final ChurService churService;

    public ChurController(ChurService churService) {
        this.churService = churService;
    }

}

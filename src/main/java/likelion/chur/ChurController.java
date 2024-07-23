package likelion.chur;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chur")
public class ChurController {

    private final ChurService churService;

    public ChurController(ChurService churService) {
        this.churService = churService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendChur(@RequestParam Long senderId, @RequestParam Long receiverId) {
        boolean isSent = churService.sendChur(senderId, receiverId);
        if (isSent) {
            return ResponseEntity.ok("Chur sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send Chur. Already sent today.");
        }
    }

}

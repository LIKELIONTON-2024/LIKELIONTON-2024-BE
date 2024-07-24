package likelion.chur;

import likelion.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chur")
public class ChurController {

    private final ChurService churService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ChurController(ChurService churService,JwtTokenUtil jwtTokenUtil) {
        this.churService = churService;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendChur(@RequestHeader("Authorization") String token, @RequestParam Long receiverId) {
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long senderId = Long.parseLong(userIdStr);

        boolean isSent = churService.sendChur(senderId, receiverId);
        if (isSent) {
            return ResponseEntity.ok("Chur sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send Chur. Already sent today.");
        }
    }
}

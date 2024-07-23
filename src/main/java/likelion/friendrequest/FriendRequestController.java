package likelion.friendrequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendrequest")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService){
        this.friendRequestService=friendRequestService;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getFriendRequestStatus(@RequestParam Long userId, @RequestParam Long friendId) {
        return ResponseEntity.ok(friendRequestService.getFriendRequestStatus(userId, friendId));
    }

}

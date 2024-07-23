package likelion.friendrequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend-request")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService){
        this.friendRequestService=friendRequestService;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getFriendRequestStatus(@RequestParam Long userId, @RequestParam Long friendId) {
        return ResponseEntity.ok(friendRequestService.getFriendRequestStatus(userId, friendId));
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId){
        Boolean isSent=friendRequestService.sendFriendRequest(senderId,receiverId);
        if (isSent) {
            return ResponseEntity.ok("Friend request sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send friend request.");
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId){
        Boolean isAccepted=friendRequestService.acceptFriendRequest(senderId,receiverId);
        if (isAccepted) {
            return ResponseEntity.ok("Friend request accepted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to accept friend request.");
        }
    }

    @DeleteMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId){
        boolean isDeleted = friendRequestService.deleteFriendRequest(senderId, receiverId);
        if (isDeleted) {
            return ResponseEntity.ok("Friend request deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete friend request.");
        }
    }

}

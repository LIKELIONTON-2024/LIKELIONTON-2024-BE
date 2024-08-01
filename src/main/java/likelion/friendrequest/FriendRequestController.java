package likelion.friendrequest;

import likelion.auth.JwtTokenUtil;
import likelion.friendrequest.dto.FriendRequestListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend-request")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public FriendRequestController(FriendRequestService friendRequestService,JwtTokenUtil jwtTokenUtil){
        this.friendRequestService=friendRequestService;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getFriendRequestStatus(@RequestHeader("Authorization") String token, @RequestParam Long friendId) {
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long userId = Long.parseLong(userIdStr);

        return ResponseEntity.ok(friendRequestService.getFriendRequestStatus(userId, friendId));
    }

   @GetMapping("/list")
    public ResponseEntity<List<FriendRequestListResponse>> getFriendRequestList(@RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long receiverId = Long.parseLong(userIdStr);

       List<FriendRequestListResponse> friendRequestList = friendRequestService.getFriendRequestList(receiverId);
       return ResponseEntity.ok(friendRequestList);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestHeader("Authorization") String token, @RequestParam Long receiverId){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long senderId = Long.parseLong(userIdStr);

        Boolean isSent=friendRequestService.sendFriendRequest(senderId,receiverId);
        if (isSent) {
            return ResponseEntity.ok("Friend request sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send friend request.");
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Long senderId, @RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long receiverId = Long.parseLong(userIdStr);

        Boolean isAccepted=friendRequestService.acceptFriendRequest(senderId,receiverId);
        if (isAccepted) {
            return ResponseEntity.ok("Friend request accepted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to accept friend request.");
        }
    }

    @DeleteMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam Long senderId, @RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long receiverId = Long.parseLong(userIdStr);

        boolean isDeleted = friendRequestService.deleteFriendRequest(senderId, receiverId);
        if (isDeleted) {
            return ResponseEntity.ok("Friend request deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete friend request.");
        }
    }
}

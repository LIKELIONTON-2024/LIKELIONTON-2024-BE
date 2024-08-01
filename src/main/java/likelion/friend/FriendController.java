package likelion.friend;

import jakarta.persistence.EntityNotFoundException;
import likelion.auth.JwtTokenUtil;
import likelion.friend.dto.FriendListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public FriendController(FriendService friendService,JwtTokenUtil jwtTokenUtil){
        this.friendService=friendService;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @GetMapping("/list")
    public ResponseEntity<List<FriendListResponse>>getFriendsByUserId(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long userId = Long.parseLong(userIdStr);

        List<FriendListResponse> friendList=friendService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friendList);
    }

    @GetMapping("/list/search")
    public ResponseEntity<List<FriendListResponse>> searchUserNickname(@RequestParam String searchKeyword){
        List<FriendListResponse> searchFriendList=friendService.getUsersBySearchKeyword(searchKeyword);
        return ResponseEntity.ok(searchFriendList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFriendFromList(@RequestParam Long friendId, @RequestHeader("Authorization") String token){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long userId = Long.parseLong(userIdStr);

        try {
            friendService.deleteFriendFromList(friendId, userId);
            return ResponseEntity.ok("Friend removed successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the friend.");
        }
    }
}

package likelion.friend;

import jakarta.persistence.EntityNotFoundException;
import likelion.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService){
        this.friendService=friendService;
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<User>>getFriendsByUserId(@PathVariable Long userId) {
        List<User> friendList=friendService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friendList);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> searchUserNickname(@RequestParam String searchKeyword){
        List<User> searchFriendList=friendService.getUsersBySearchKeyword(searchKeyword);
        return ResponseEntity.ok(searchFriendList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFriendFromList(@RequestParam Long friendId, @RequestParam Long userId){
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

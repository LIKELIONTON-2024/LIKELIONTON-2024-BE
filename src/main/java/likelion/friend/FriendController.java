package likelion.friend;

import likelion.user.User;
import likelion.user.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
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
    public List<User> getFriendsByUserId(@PathVariable Long userId) {
        return friendService.getFriendsByUserId(userId);
    }

    @GetMapping("/list")
    public List<User> searchUserNickname(@RequestParam String searchKeyword){
        return friendService.getUsersBySearchKeyword(searchKeyword);
    }
}

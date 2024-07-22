package likelion.friend;

import likelion.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

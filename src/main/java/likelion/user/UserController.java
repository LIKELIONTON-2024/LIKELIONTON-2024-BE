package likelion.user;

import likelion.user.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{friendId}")
    public ResponseEntity<UserResponseDTO> getUserProfile(@PathVariable Long friendId){
        UserResponseDTO friendFrofile=userService.getUserProfileByUserId(friendId);
        return ResponseEntity.ok(friendFrofile);
    }

}

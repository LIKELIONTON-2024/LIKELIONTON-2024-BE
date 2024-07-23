package likelion.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/join")
	public UserJoinResponse join(@RequestBody UserJoinRequest request) {
		return userService.join(request);
	}
}

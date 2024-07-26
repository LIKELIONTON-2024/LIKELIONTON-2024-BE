package likelion.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import likelion.auth.JwtTokenUtil;
import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;
import likelion.user.dto.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@GetMapping("/profile/{friendId}")
	public ResponseEntity<UserResponse> getFriendProfile(@PathVariable Long friendId) {
		UserResponse friendFrofile = userService.getFreiendProfileByFriendId(friendId);
		return ResponseEntity.ok(friendFrofile);
	}

	@PostMapping("/join")
	public UserJoinResponse join(@RequestBody UserJoinRequest request) {
		System.out.println("회원가입 API 호출됨!");
		return userService.join(request);
	}
}

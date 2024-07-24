package likelion.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import likelion.user.User;
import likelion.user.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserService userService;

	@Autowired
	public AuthController(JwtTokenUtil jwtTokenUtil, UserService userService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
	}

	@GetMapping("/refresh")
	public ResponseEntity<Map<String, String>> refreshToken(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
		String token = refreshToken.replace("Bearer ", "");
		String userId = jwtTokenUtil.getUserIdFromToken(token);

		User user = userService.findById(Long.parseLong(userId));
		if (user == null) {
			return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
		}

		String newAccessToken = jwtTokenUtil.generateAccessToken(user.getUserId(), user.getNickname(), user.getEmail());
		return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
	}
}

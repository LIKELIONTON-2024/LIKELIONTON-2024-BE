package likelion.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

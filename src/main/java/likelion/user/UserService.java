package likelion.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import likelion.auth.JwtTokenUtil;
import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	public UserJoinResponse join(UserJoinRequest request) {
		User user = User.createUser(request.email(), request.nickname(), request.latitude(), request.longitude());
		userRepository.save(user);
		Map<String, String> tokens = jwtTokenUtil.generateTokens(user.getUserId(), user.getNickname(), user.getEmail());
		return UserJoinResponse.from(tokens.get("accessToken"), tokens.get("refreshToken"));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(long userId) {
		return userRepository.findById(userId).orElse(null);
	}
}

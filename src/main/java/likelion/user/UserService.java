package likelion.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import likelion.auth.JwtTokenUtil;
import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;
import likelion.user.dto.UserMyPageResponse;
import likelion.user.dto.UserResponse;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	public UserResponse getFreiendProfileByFriendId(Long friendId) {
		User user = userRepository.findById(friendId)
			.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + friendId));

		return new UserResponse(user.getUserId(), user.getNickname(), user.getUserImage(), user.getLastVerifiedDate());
	}

	public UserJoinResponse join(UserJoinRequest request) {
		if (userRepository.findByEmail(request.email()) != null) {
			throw new IllegalArgumentException("유저가 이미 존재합니다.");
		}
		User user = User.createUser(request.email(), request.nickname(), request.zipCode());
		userRepository.save(user);
		Map<String, String> tokens = jwtTokenUtil.generateTokens(user.getUserId(), user.getNickname(), user.getEmail());
		return UserJoinResponse.from(tokens.get("accessToken"), tokens.get("refreshToken"));
	}

	public UserMyPageResponse getMyPage(Long id) {
		User user = findById(id);
		return UserMyPageResponse.from(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(long userId) {
		return userRepository.findById(userId).orElse(null);
	}

}

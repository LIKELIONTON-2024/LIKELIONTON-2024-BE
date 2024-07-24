package likelion.user;

import likelion.user.dto.UserResponseDTO;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserResponseDTO getFreiendProfileByFriendId(Long friendId){
        User user=userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + friendId));

        return new UserResponseDTO(user.getUserId(), user.getNickname(), user.getUserImage(), user.getLastVerifiedDate());
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

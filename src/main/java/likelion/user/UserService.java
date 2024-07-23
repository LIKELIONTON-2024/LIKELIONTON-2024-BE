package likelion.user;

import org.springframework.stereotype.Service;

import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserJoinResponse join(UserJoinRequest request) {
		User user = User.createUser(request.email(), request.nickname(), request.latitude(), request.longitude());
		userRepository.save(user);
		return UserJoinResponse.from(user);
	}
}

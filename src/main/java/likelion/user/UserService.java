package likelion.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import likelion.address.Address;
import likelion.address.AddressService;
import likelion.auth.JwtTokenUtil;
import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;
import likelion.user.dto.UserMyPageResponse;
import likelion.user.dto.UserResponse;
import likelion.visit.VisitService;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;
	private final AddressService addressService;
	private final VisitService visitService;

	@Autowired
	public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, AddressService addressService,
		VisitService visitService) {
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
		this.addressService = addressService;
		this.visitService = visitService;
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
		User user = User.createUser(request.email(), request.nickname(), request.address());
		Address address = addressService.getLatLonFromAddress(request.address(), user);
		user.setAddressEntity(address);
		userRepository.save(user);
		Map<String, String> tokens = jwtTokenUtil.generateTokens(user.getUserId(), user.getNickname(), user.getEmail());
		return UserJoinResponse.from(tokens.get("accessToken"), tokens.get("refreshToken"));
	}

	public UserMyPageResponse getMyPage(Long userId) {
		User user = findById(userId);
		if (user == null) {
			throw new IllegalArgumentException("유저가 존재하지 않습니다.");
		}
		List<LocalDateTime> visitDays = visitService.getVisitDates(userId);
		int consecutiveDays = visitService.getConsecutiveVisitDays(userId);
		return UserMyPageResponse.from(user, visitDays, consecutiveDays);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(long userId) {
		return userRepository.findById(userId).orElse(null);
	}

}

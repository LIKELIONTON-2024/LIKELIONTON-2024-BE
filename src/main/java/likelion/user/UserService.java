package likelion.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import likelion.address.Address;
import likelion.address.AddressService;
import likelion.auth.JwtTokenUtil;
import likelion.item.Item;
import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;
import likelion.user.dto.UserMyImageResponse;
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
		setUserInventory(user);
		userRepository.save(user);
		Map<String, String> tokens = jwtTokenUtil.generateTokens(user.getUserId(), user.getNickname(), user.getEmail());
		return UserJoinResponse.from(tokens.get("accessToken"), tokens.get("refreshToken"));
	}

	private void setUserInventory(User user) {
		List<Item> defaultItems = List.of(
			new Item("default", "갈색냥이", "cat",
				"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/inventory-default.png",
				false, true, 0, user),
			new Item("black", "검정냥이", "cat",
				"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/inventory-black.png",
				true,
				false, 3, user),
			new Item("gray", "회색냥이", "cat",
				"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/inventory-gray.png",
				true,
				false, 5,
				user),
			new Item("white", "흰색냥이", "cat",
				"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/inventory-white.png",
				true,
				false, 5, user),
			new Item("default", "하얀", "background",
				"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/default.png", false, true, 0, user),
			new Item("green", "풀밭", "background",
				"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/green.png", true, false, 10, user)
		);
		user.setInventory(defaultItems);
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

	public UserMyImageResponse getMyImages(Long userId) {
		User user = findById(userId);
		if (user == null) {
			throw new IllegalArgumentException("유저가 존재하지 않습니다");
		}

		String catImage = user.getInventory().stream()
			.filter(item -> item.isSelected() && item.getType().equalsIgnoreCase("cat"))
			.map(Item::getImageUrl)
			.findFirst()
			.orElse(null);

		String backgroundImage = user.getInventory().stream()
			.filter(item -> item.isSelected() && item.getType().equalsIgnoreCase("background"))
			.map(Item::getImageUrl)
			.findFirst()
			.orElse(null);

		return new UserMyImageResponse(catImage, backgroundImage);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public int getTotalChuru(Long userId) {
		return findById(userId).getTotalChuru();
	}

	public User findById(long userId) {
		return userRepository.findById(userId).orElse(null);
	}

}

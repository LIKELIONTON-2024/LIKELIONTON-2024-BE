package likelion.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import likelion.auth.JwtTokenUtil;
import likelion.item.ItemService;
import likelion.item.dto.ItemGetResponse;
import likelion.user.dto.UserInventoryResponse;
import likelion.user.dto.UserJoinRequest;
import likelion.user.dto.UserJoinResponse;
import likelion.user.dto.UserMyImageResponse;
import likelion.user.dto.UserMyPageResponse;
import likelion.user.dto.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final ItemService itemService;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserController(UserService userService, ItemService itemService, JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.itemService = itemService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@GetMapping("/profile/{friendId}")
	public ResponseEntity<UserResponse> getFriendProfile(@PathVariable Long friendId) {
		UserResponse friendProfile = userService.getFreiendProfileByFriendId(friendId);
		return ResponseEntity.ok(friendProfile);
	}

	@PostMapping("/join")
	public UserJoinResponse join(@RequestBody UserJoinRequest request) {
		return userService.join(request);
	}

	@GetMapping("/my")
	public UserMyPageResponse getMyPage(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
		String token = accessToken.replace("Bearer ", "");
		return userService.getMyPage(Long.parseLong(jwtTokenUtil.getUserIdFromToken(token)));
	}

	@GetMapping("/image")
	public UserMyImageResponse getMyImages(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
		String token = accessToken.replace("Bearer ", "");
		return userService.getMyImages(Long.parseLong(jwtTokenUtil.getUserIdFromToken(token)));
	}

	@GetMapping("/inventory")
	public UserInventoryResponse getMyInventory(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
		String token = accessToken.replace("Bearer ", "");
		Long userId = Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
		List<ItemGetResponse> items = itemService.getItemsByUserId(userId);
		return new UserInventoryResponse(userService.getTotalChuru(userId), items);
	}
}

package likelion.item;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import likelion.auth.JwtTokenUtil;
import likelion.item.dto.ItemSelectRequest;

@RestController
@RequestMapping("/inventory")
public class ItemController {

	private final ItemService itemService;
	private final JwtTokenUtil jwtTokenUtil;

	public ItemController(ItemService itemService, JwtTokenUtil jwtTokenUtil) {
		this.itemService = itemService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PutMapping("/buy")
	public ResponseEntity<String> buyItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
		@RequestParam String type,
		@RequestParam String name) {
		String token = accessToken.replace("Bearer ", "");
		Long userId = Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
		itemService.buyItem(userId, type, name);
		return ResponseEntity.ok("아이템이 성공적으로 구매되었습니다.");
	}

	@PutMapping("/select")
	public ResponseEntity<String> selectItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
		@RequestBody List<ItemSelectRequest> request) {
		String token = accessToken.replace("Bearer ", "");
		Long userId = Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
		itemService.selectItem(userId, request);
		return ResponseEntity.ok("성공적으로 업데이트 됨!");
	}
}

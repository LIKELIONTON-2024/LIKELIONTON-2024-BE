package likelion.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import likelion.item.dto.ItemGetResponse;
import likelion.item.dto.ItemSelectRequest;
import likelion.user.User;
import likelion.user.UserRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
	}

	public List<ItemGetResponse> getItemsByUserId(Long userId) {
		return itemRepository.findAllByUser_UserId(userId).stream()
			.map(ItemGetResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public void buyItem(Long userId, String type, String name) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
		Item item = itemRepository.findByUser_UserIdAndTypeAndName(userId, type, name)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 아이템이 없습니다."));

		int totalChuru = user.getTotalChuru();
		int itemChuruCost = item.getChuru();

		if (!item.isLocked()) {
			throw new IllegalArgumentException("아이템 '" + name + "'은 이미 잠금 해제되었습니다.");
		}

		if (totalChuru < itemChuruCost) {
			throw new IllegalArgumentException("충분한 츄르가 없습니다. 필요한 츄르: " + itemChuruCost);
		}

		user.setTotalChuru(totalChuru - itemChuruCost);
		item.setLocked(false);

		itemRepository.save(item);
		userRepository.save(user);
	}

	@Transactional
	public void selectItem(Long userId, List<ItemSelectRequest> selections) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		for (ItemSelectRequest selection : selections) {
			String type = selection.type();
			String name = selection.name();
			List<Item> items = itemRepository.findAllByUser_UserIdAndType(userId, type);
			boolean itemFound = false;

			for (Item item : items) {
				if (item.getName().equalsIgnoreCase(name)) {
					if (item.isLocked()) {
						throw new IllegalArgumentException("아이템을 먼저 구매해주세요");
					}
					item.setSelected(true);
					if (type.equals("cat")) {
						setUserImage(user, item);
					}
					itemFound = true;
				} else {
					item.setSelected(false);
				}
			}
			if (!itemFound) {
				throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
			}
			itemRepository.saveAll(items);
		}
	}

	private void setUserImage(User user, Item item) {
		String userImage = switch (item.getName()) {
			case "black" -> "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/black.png";
			case "gray" -> "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/gray.png";
			case "white" -> "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/white.png";
			default -> "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/basic.png";
		};
		user.setUserImage(userImage);
	}
}

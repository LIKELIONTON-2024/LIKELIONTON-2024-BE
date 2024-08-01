package likelion.user.dto;

import java.util.List;

import likelion.item.dto.ItemGetResponse;

public record UserInventoryResponse(
	int totalChuru,
	List<ItemGetResponse> inventory
) {
}

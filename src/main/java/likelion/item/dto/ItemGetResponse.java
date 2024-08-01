package likelion.item.dto;

import likelion.item.Item;

public record ItemGetResponse(
	String type,
	String name,
	String koreanName,
	boolean isLocked,
	boolean isSelected,
	int churu
) {
	public static ItemGetResponse from(Item item) {
		return new ItemGetResponse(
			item.getType(),
			item.getName(),
			item.getKoreanName(),
			item.isLocked(),
			item.isSelected(),
			item.getChuru()
		);
	}
}

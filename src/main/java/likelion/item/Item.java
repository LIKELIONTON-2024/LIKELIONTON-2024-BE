package likelion.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import likelion.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String koreanName;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String imageUrl;

	@Column(nullable = false)
	private boolean isLocked;

	@Column(nullable = false)
	private boolean isSelected;

	@Column(nullable = false)
	private Integer churu;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Item(String name, String koreanName, String type, String imageUrl, boolean isLocked, boolean isSelected,
		Integer churu,
		User user) {
		this.name = name;
		this.koreanName = koreanName;
		this.type = type;
		this.imageUrl = imageUrl;
		this.isLocked = isLocked;
		this.isSelected = isSelected;
		this.churu = churu;
		this.user = user;
	}
}



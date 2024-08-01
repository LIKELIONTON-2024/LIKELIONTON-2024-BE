package likelion.user;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import likelion.address.Address;
import likelion.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

	@Id
	@JoinColumn(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String nickname;

	private String userImage;

	private String inventoryImage;

	private String inventoryBackgroundImage;

	private String userBadge;

	private String address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Address addressEntity;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Item> inventory;

	private Integer totalChuru;

	private Float totalDistance;

	private Integer totalVisits;

	private LocalDateTime createdDate;

	private LocalDateTime lastVerifiedDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
		this.lastVerifiedDate = LocalDateTime.now();
	}

	public User(String email, String nickname, String address) {
		this.email = email;
		this.nickname = nickname;
		this.address = address;
		this.userImage = "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/basic.png";
		this.inventoryImage = "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/inventory-default.png";
		this.inventoryBackgroundImage = "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/default.png";
		this.totalChuru = 0;
		this.totalDistance = 0.0f;
		this.totalVisits = 0;
	}

	public static User createUser(String email, String nickname, String address) {
		return new User(email, nickname, address);
	}
}

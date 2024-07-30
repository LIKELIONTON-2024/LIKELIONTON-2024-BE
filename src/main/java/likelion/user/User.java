package likelion.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String zipCode;

	private String latitude;

	private String longitude;

	@Column(nullable = false)
	private String userImage;

	private String userAddress;

	private Integer totalChuru;

	private Float totalDistance;

	private Integer totalSpots;

	private Integer totalVisits;

	private LocalDateTime createdDate;

	private LocalDateTime lastVerifiedDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
		this.lastVerifiedDate = LocalDateTime.now();
	}

	public User(String email, String nickname, String zipCode) {
		this.email = email;
		this.nickname = nickname;
		this.zipCode = zipCode;
		this.userImage = "https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/basic.png";
		this.totalChuru = 0;
		this.totalDistance = 0.0f;
		this.totalSpots = 0;
		this.totalVisits = 0;
	}

	public static User createUser(String email, String nickname, String zipCode) {
		return new User(email, nickname, zipCode);
	}
}

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
	private String userImage =
		"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/basic.png";

	private String userAddress;

	private Integer totalChuru = 0;

	private Float totalDistance = 0.0f;

	private Integer totalSpots = 0;

	private Integer totalVisits = 0;

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
	}

	public static User createUser(String email, String nickname, String zipCode) {
		return new User(email, nickname, zipCode);
	}
}

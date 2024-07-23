package likelion.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private Float latitude;//위도

	@Column(nullable = false)
	private Float longitude;//경도

	private String userImage;

	private String userAddress;//주소

	private Integer zipCode;//우편번호

	private Integer totalChuru;

	private Float totalDistance;

	private Integer totalSpots;

	private Integer totalVisits;

	private LocalDateTime createdDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
	}

	public User(String email, String nickname, Float latitude, Float longitude) {
		this.email = email;
		this.nickname = nickname;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static User createUser(String email, String nickname, Float latitude, Float longitude) {
		return new User(email, nickname, latitude, longitude);
	}
}

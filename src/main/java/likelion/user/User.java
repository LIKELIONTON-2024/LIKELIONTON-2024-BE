package likelion.user;

import java.time.LocalDateTime;
import jakarta.persistence.*;
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
	private Float latitude;

	@Column(nullable = false)
	private Float longitude;

	@Column(nullable = false)
	private String userImage =
		"https://likelion-hikikomori.s3.ap-northeast-2.amazonaws.com/basic.png";

	private String userAddress;

	private Integer zipCode;

	private Integer totalChuru;

	private Float totalDistance;

	private Integer totalSpots;

	private Integer totalVisits;

	private LocalDateTime createdDate;

    private LocalDateTime lastVerifiedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
		this.lastVerifiedDate=LocalDateTime.now();
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

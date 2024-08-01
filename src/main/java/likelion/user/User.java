package likelion.user;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import likelion.address.Address;
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

	private String userImage;

	private String address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Address addressEntity;

	private Integer totalChuru;//총 츄르 개수

	private Float totalDistance;//총 걸은 거리

	private Integer totalVisits;//총 인증 횟수

	private LocalDateTime createdDate;

	private LocalDateTime lastVerifiedDate;//인증 마지막 날짜

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
		this.totalChuru = 0;
		this.totalDistance = 0.0f;
		this.totalVisits = 0;
	}

	public static User createUser(String email, String nickname, String address) {
		return new User(email, nickname, address);
	}
}

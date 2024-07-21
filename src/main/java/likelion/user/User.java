package likelion.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private String userImage;

    @Column(nullable = false)
    private String userAddress;//주소

    @Column(nullable = false)
    private Float latitude;//위도

    @Column(nullable = false)
    private Float longitude;//경도

    @Column(nullable = false)
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

}

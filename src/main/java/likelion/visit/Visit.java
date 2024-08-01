package likelion.visit;

import jakarta.persistence.*;
import likelion.spot.Spot;
import likelion.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;

    private LocalDateTime visitDate;
    private Boolean isVerfied;
    private Integer distance;

    public Visit(User user,Spot spot, Integer distance){
        this.user=user;
        this.spot=spot;
        this.visitDate=LocalDateTime.now();
        this.isVerfied=true;
        this.distance=distance;
    }
}

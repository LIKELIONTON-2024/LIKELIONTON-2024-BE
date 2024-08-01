package likelion.spot;

import jakarta.persistence.*;
import likelion.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    private String name;
    private String address;
    private String latitude;
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Spot(User user,String name,String address,String latitude,String longitude){
        this.user=user;
        this.name=name;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}

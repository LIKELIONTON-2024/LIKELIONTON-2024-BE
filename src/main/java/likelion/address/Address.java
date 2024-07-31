package likelion.address;

import jakarta.persistence.*;
import likelion.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String latitude;
    private String longitude;
    private String address;

    public Address(User user,String address,String latitude,String longitude){
        this.user=user;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}

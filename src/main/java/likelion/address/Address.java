package likelion.address;

import jakarta.persistence.*;
import likelion.user.User;

import java.time.LocalDateTime;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Float latitude;
    private Float longitude;
    private String address;
    private String zipCode;
    private LocalDateTime createdDate;

    public Address(){}

    public Address(User user,String address,Float latitude,Float longitude,String zipCode,LocalDateTime createdDate){
        this.user=user;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
        this.zipCode=zipCode;
        this.createdDate=user.getCreatedDate();
    }
}

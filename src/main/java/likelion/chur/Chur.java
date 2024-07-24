package likelion.chur;

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
public class Chur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long churId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime sendDate;

    @PrePersist
    protected void onCreate() {
        this.sendDate = LocalDateTime.now();
    }
}

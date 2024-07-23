package likelion.chur;

import likelion.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ChurRepository extends JpaRepository<Chur,Long> {

    boolean existsBySenderAndReceiverAndSendDateBetween(User sender, User receiver, LocalDateTime start, LocalDateTime end);

}

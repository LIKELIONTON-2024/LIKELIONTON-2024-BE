package likelion.friend;


import likelion.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {

    List<Friend> findByUserUserId(Long userId);
    Boolean existsByUserUserIdAndFriendUserId(Long userId,Long friendId);
    Optional<Friend> findByUserAndFriend(User user, User friend);
}

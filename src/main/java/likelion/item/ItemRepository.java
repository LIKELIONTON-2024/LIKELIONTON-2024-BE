package likelion.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findAllByUser_UserId(Long userId);

	List<Item> findAllByUser_UserIdAndType(Long userId, String type);

	Optional<Item> findByUser_UserIdAndTypeAndName(Long userId, String type, String name);
}

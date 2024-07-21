package likelion.chur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurRepository extends JpaRepository<Chur,Long> {
}

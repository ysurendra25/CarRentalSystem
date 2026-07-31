package CarRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.UserTable;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Integer> {

    Optional<UserTable> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
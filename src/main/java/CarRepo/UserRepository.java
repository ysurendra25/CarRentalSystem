package CarRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarDto.user.UserResponseDto;
import CarEntity.UserTable;
import CarEntity.role;
import CarEntity.owner_status;


@Repository
public interface UserRepository extends JpaRepository<UserTable, Integer> {

    Optional<UserTable> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    
    List<UserTable> findByRoleAndOwnerStatus(role role, owner_status ownerStatus);

}
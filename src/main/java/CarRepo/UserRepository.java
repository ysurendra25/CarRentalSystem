package CarRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.UserTable;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Integer>{

}

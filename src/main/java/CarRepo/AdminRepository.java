package CarRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.admintable;

@Repository
public interface AdminRepository extends JpaRepository<admintable, Integer>{

}

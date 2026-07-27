package CarRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.CartTable;

@Repository
public interface CartRepository extends JpaRepository<CartTable, Integer>{

}

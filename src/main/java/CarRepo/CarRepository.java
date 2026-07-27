package CarRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.CarsTable;

@Repository
public interface CarRepository extends JpaRepository<CarsTable, Integer>{

}

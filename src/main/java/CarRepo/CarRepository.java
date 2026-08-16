package CarRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.CarsTable;
import CarEntity.availabilty_status;

@Repository
public interface CarRepository extends JpaRepository<CarsTable, Integer> {

    Optional<CarsTable> findByRegistrationNumber(String registrationNumber);

    boolean existsByRegistrationNumber(String registrationNumber);

    List<CarsTable> findByBrand(String brand);

    List<CarsTable> findByModel(String model);

    List<CarsTable> findByAvailabilityStatus(availabilty_status status);

}

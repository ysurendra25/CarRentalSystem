package CarRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.BookingsTable;
import CarEntity.CarsTable;
import CarEntity.UserTable;
import CarEntity.status;



@Repository
public interface BookingRepository extends JpaRepository<BookingsTable, Integer> {

	List<BookingsTable> findByUser(UserTable user);
	List<BookingsTable> findByCar(CarsTable car);
	List<BookingsTable> findByStatus(status status);
}

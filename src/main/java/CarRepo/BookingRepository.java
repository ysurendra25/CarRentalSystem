package CarRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.BookingsTable;

@Repository
public interface BookingRepository extends JpaRepository<BookingsTable, Integer> {

}

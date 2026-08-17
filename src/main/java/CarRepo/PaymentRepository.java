package CarRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import CarEntity.PaymentTable;
import CarEntity.payment_status;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTable, Integer> {

    PaymentTable findByBookingBookingId(int bookingId);

    @Query("SELECT p FROM PaymentTable p WHERE p.booking.user.userId = :userId")
    List<PaymentTable> findByBookingUserId(@Param("userId") int userId);

    List<PaymentTable> findByPaymentStatus(payment_status paymentStatus);
}

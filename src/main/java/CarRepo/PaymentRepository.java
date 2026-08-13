package CarRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CarEntity.PaymentTable;
import CarEntity.payment_status;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTable, Integer>{

	PaymentTable findByBooking_Booking_id(int bookingId);
	
	List<PaymentTable> findByBookingUserId(int userId);
	
	List<PaymentTable> findByPayment_status(payment_status paymentStatus);
}

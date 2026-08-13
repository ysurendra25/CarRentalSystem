package CarService;

import java.util.List;

import CarDto.payment.PaymentRequestDto;
import CarDto.payment.PaymentResponseDto;

public interface PaymentService {

	PaymentResponseDto payForBooking(int bookingId,PaymentRequestDto paymentRequestDto);
	
	PaymentResponseDto getPaymentById(int paymentId);
	
	List<PaymentResponseDto> getPaymentsByUser();
	
	List<PaymentResponseDto> getAllPayments();
	
	PaymentResponseDto refundPayment(int paymentId);
	
	
	
}


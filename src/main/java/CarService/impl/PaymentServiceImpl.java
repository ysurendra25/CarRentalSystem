package CarService.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import CarDto.payment.PaymentRequestDto;
import CarDto.payment.PaymentResponseDto;
import CarEntity.BookingsTable;
import CarEntity.PaymentTable;
import CarEntity.UserTable;
import CarEntity.payment_method;
import CarEntity.payment_status;
import CarEntity.status;
import CarRepo.BookingRepository;
import CarRepo.PaymentRepository;
import CarRepo.UserRepository;
import CarService.PaymentService;
import CarUtils.AuthUtils;

@Service
public class PaymentServiceImpl implements PaymentService{
    private UserRepository userRepo;
    private BookingRepository bookingRepo;
    private PaymentRepository payRepo;
    
	

	public PaymentServiceImpl(UserRepository userRepo, BookingRepository bookingRepo, PaymentRepository payRepo) {
		super();
		this.userRepo = userRepo;
		this.bookingRepo = bookingRepo;
		this.payRepo = payRepo;
	}

	
	
	@Override
	public PaymentResponseDto payForBooking(int bookingId, PaymentRequestDto paymentRequestDto) {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		BookingsTable book = bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found!"));
		PaymentTable pay = payRepo.findByBooking_Booking_id(bookingId);
		
		if(!book.getUser().equals(user)) {
			throw new RuntimeException("You are not authorized to pay for this booking");
		}
		if(!book.getStatus().equals(status.pending)) {
			if(book.getStatus().equals(status.confirmed)) {
				throw new RuntimeException("Already paid");
			}
			if(book.getStatus().equals(status.cancelled) || book.getStatus().equals(status.completed)) {
				throw new RuntimeException("you cannot pay for this");
			}
		}
		
		  if (pay != null) {
		        throw new RuntimeException("Payment already exists for this booking");
		    }
		pay = new PaymentTable();
		BigDecimal totalPrice = book.getTotal_price();
		
		pay.setBooking(book);
		pay.setAmount(totalPrice);
		pay.setPayment_method(paymentRequestDto.getPaymentMethod());
		pay.setPayment_status(payment_status.success);
		PaymentTable savedPayment = payRepo.save(pay);
		book.setStatus(status.confirmed);
		bookingRepo.save(book);
		
		PaymentResponseDto resp = new PaymentResponseDto();
		resp.setPaymentId(savedPayment.getPayment_id());
		resp.setBookingId(savedPayment.getBooking().getBooking_id());
		resp.setAmount(savedPayment.getAmount());
		resp.setPaymentMethod(savedPayment.getPayment_method());
		resp.setPaymentStatus(savedPayment.getPayment_status());
		resp.setPaymentDate(savedPayment.getPayment_date());
		resp.setRazorpayOrderId(savedPayment.getRazorpayOrderId());
		resp.setRazorpayPaymentId(savedPayment.getRazorpayPaymentId());
		
		return resp;
	}

	@Override
	public PaymentResponseDto getPaymentById(int paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentResponseDto> getPaymentsByUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentResponseDto> getAllPayments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentResponseDto refundPayment(int paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

}

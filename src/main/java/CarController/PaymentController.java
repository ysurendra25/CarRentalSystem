package CarController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CarDto.payment.PaymentRequestDto;
import CarDto.payment.PaymentResponseDto;
import CarService.PaymentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	private PaymentService pservice;
	public PaymentController(PaymentService pservice) {
		super();
		this.pservice = pservice;
	}

    @PostMapping("/payforbooking/{bookingId}")
	public PaymentResponseDto payForBooking(@PathVariable int bookingId,@Valid @RequestBody PaymentRequestDto req) {
		PaymentResponseDto resp = pservice.payForBooking(bookingId, req);
		
		return resp;
	}
    @GetMapping("/getPaymentDetail/{paymentId}")
    public PaymentResponseDto getPaymentById(@PathVariable int paymentId) {
    		PaymentResponseDto resp = pservice.getPaymentById(paymentId);
    		
    		return resp;
    }
    
    @GetMapping("/getPaymentsByUser")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByUser() {
    	    List<PaymentResponseDto> resp = pservice.getPaymentsByUser();
    	    
    	    return ResponseEntity.ok(resp);
    }
    
    @GetMapping("/getAllPayments")
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
    		List<PaymentResponseDto> resp = pservice.getAllPayments();
    		
    		return ResponseEntity.ok(resp);
    }
    
    @PostMapping("/refundPayment/{paymentId}")
    public PaymentResponseDto refundPayment(@PathVariable int paymentId) {
    	    PaymentResponseDto resp = pservice.refundPayment(paymentId);
    	    
    	    return resp;
    }
    
   
	
	
}

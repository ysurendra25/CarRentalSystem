package CarDto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import CarEntity.payment_method;
import CarEntity.payment_status;

public class PaymentResponseDto {

	private int paymentId;
	private int bookingId;
	private BigDecimal amount;
	private payment_method paymentMethod;
	private payment_status paymentStatus;
	private String transactionId;
	private LocalDateTime paymentDate;
	private String message;
	private String razorpayOrderId;
	private String razorpayPaymentId;
	
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public payment_method getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(payment_method paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public payment_status getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(payment_status paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}
	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}
	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}
	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}
	
	
	
	
	
}

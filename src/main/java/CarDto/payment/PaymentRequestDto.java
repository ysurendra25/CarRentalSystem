package CarDto.payment;

import CarEntity.payment_method;

public class PaymentRequestDto {

	private int bookinId;
	private payment_method paymentMethod;
	public int getBookinId() {
		return bookinId;
	}
	public void setBookinId(int bookinId) {
		this.bookinId = bookinId;
	}
	public payment_method getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(payment_method paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}

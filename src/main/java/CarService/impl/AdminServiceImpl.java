package CarService.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import CarDto.booking.BookingResponseDto;
import CarDto.car.CarResponseDto;
import CarDto.owner.OwnerResponseDto;
import CarDto.payment.PaymentResponseDto;
import CarDto.user.UserResponseDto;
import CarEntity.BookingsTable;
import CarEntity.CarsTable;
import CarEntity.PaymentTable;
import CarEntity.UserTable;
import CarEntity.owner_status;
import CarEntity.role;
import CarRepo.BookingRepository;
import CarRepo.CarRepository;
import CarRepo.PaymentRepository;
import CarRepo.UserRepository;
import CarService.AdminService;
import CarUtils.AuthUtils;


@Service
public class AdminServiceImpl implements AdminService{

	private UserRepository userRepo;
	private CarRepository carrepo;
	private BookingRepository bookingRepo;
	private PaymentRepository payRepo;

	public AdminServiceImpl(UserRepository userRepo, CarRepository carrepo, BookingRepository bookingRepo,
			PaymentRepository payRepo) {
		super();
		this.userRepo = userRepo;
		this.carrepo = carrepo;
		this.bookingRepo = bookingRepo;
		this.payRepo = payRepo;
	}


	@Override
	public List<OwnerResponseDto> getOwnerRequest() {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		if(user.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access for this!");
		}
		List<UserTable> owner = userRepo.findByRoleAndOwnerStatus(role.CUSTOMER, owner_status.PENDING);
		
		List<OwnerResponseDto> resp = new ArrayList<>();
		
		for(UserTable u1:owner) {
			OwnerResponseDto dtoResp = new OwnerResponseDto();
			dtoResp.setUserId(u1.getUserId());
			dtoResp.setPname(u1.getPname());
			dtoResp.setEmail(u1.getEmail());
			dtoResp.setOwnerStatus(u1.getOwnerStatus().name());
			
			resp.add(dtoResp);
		}
		
		return resp;
	}


	@Override
	public OwnerResponseDto approveOwner(int userId) {
		
		UserTable owner = AuthUtils.getLoggedUser(userRepo);
		if(owner.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access");
		}
		UserTable user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found!"));
		if(user.getRole()!=role.CUSTOMER || user.getOwnerStatus() != owner_status.PENDING) {
			throw new RuntimeException("update yourself as owner and request again!");
		}
		
		user.setOwnerStatus(owner_status.APPROVED);
		user.setRole(role.OWNER); 
		userRepo.save(user);
		
		OwnerResponseDto resp= new OwnerResponseDto();
		resp.setOwnerStatus(owner_status.APPROVED.name());
		resp.setMessage("you are owner now!");
		
		return resp;
	}


	@Override
	public OwnerResponseDto rejectOwner(int userId) {
		UserTable owner = AuthUtils.getLoggedUser(userRepo);
		if(owner.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access");
		}
		UserTable user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found!"));
		if(user.getRole()!=role.CUSTOMER || user.getOwnerStatus() != owner_status.PENDING) {
			throw new RuntimeException("update yourself as owner and request again!");
		}
		
		user.setOwnerStatus(owner_status.REJECTED);
		user.setOwnerRejectedAt(LocalDateTime.now());
		userRepo.save(user);
		
		OwnerResponseDto resp= new OwnerResponseDto();
		resp.setOwnerStatus(owner_status.REJECTED.name());
		resp.setMessage("owner request rejected!");
		
		return resp;
	}


	@Override
	public List<UserResponseDto> getAllUsers() {
		UserTable owner = AuthUtils.getLoggedUser(userRepo);
		if(owner.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access");
		}
		List<UserTable> users = userRepo.findAll();
		List<UserResponseDto> resp = new ArrayList<>();
		for(UserTable u1:users) {
			UserResponseDto dto = new UserResponseDto();
			dto.setUserId(u1.getUserId());
			dto.setPname(u1.getPname());
			dto.setEmail(u1.getEmail());
			dto.setRole(u1.getRole().name());
		    dto.setPhone(u1.getPhone());
		    
		    resp.add(dto);
		}
		
		return resp;
	}


	@Override
	public List<CarResponseDto> getAllCars() {
		UserTable owner = AuthUtils.getLoggedUser(userRepo);
		if(owner.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access");
		}
		
		List<CarsTable> car = carrepo.findAll();
		List<CarResponseDto> resp = new ArrayList<>();
		for(CarsTable c1:car) {
			CarResponseDto dto = new CarResponseDto();
			dto.setCarId(c1.getCarId());
			dto.setBrand(c1.getBrand());
			dto.setModel(c1.getModel());
			dto.setYear(c1.getYear());  
			dto.setPricePerDay(c1.getPricePerDay());
			dto.setRegistrationNumber(c1.getRegistrationNumber());
			dto.setAvailabilityStatus(c1.getAvailabilityStatus()); 
			
			resp.add(dto);
		}
		
		return resp;
	}


	@Override
	public List<BookingResponseDto> getAllBookings() {
		UserTable owner = AuthUtils.getLoggedUser(userRepo);
		if(owner.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access");
		}
	
		List<BookingsTable> bookings = bookingRepo.findAll();
		List<BookingResponseDto> resp = new ArrayList<>();
		for(BookingsTable b1:bookings) {
			BookingResponseDto dto = new BookingResponseDto();
			dto.setBookingId(b1.getBookingId());
			dto.setUserId(b1.getUser().getUserId());
			dto.setUserEmail(b1.getUser().getEmail());
			dto.setBrand(b1.getCar().getBrand());
			dto.setPickupDateTime(b1.getStartDate());
			dto.setReturnDateTime(b1.getEndDate());
			dto.setEndDateTime(b1.getReturnDate());
			dto.setTotalPrice(b1.getTotalPrice());
			dto.setBookingStatus(b1.getStatus());
			dto.setPenaltyAmount(b1.getPenaltyAmount());
			resp.add(dto);
		}
		
		return resp;
	}


	@Override
	public List<PaymentResponseDto> getAllPayments() {
		UserTable owner = AuthUtils.getLoggedUser(userRepo);
		if(owner.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access");
		}
		
		List<PaymentTable> payments = payRepo.findAll();
		List<PaymentResponseDto> resp = new ArrayList<>();
		for(PaymentTable p1:payments) {
			PaymentResponseDto dto = new PaymentResponseDto();
			dto.setPaymentId(p1.getPaymentId());
			dto.setBookingId(p1.getBooking().getBookingId());
			dto.setUserId(p1.getBooking().getUser().getUserId());
			dto.setEmail(p1.getBooking().getUser().getEmail());
			dto.setAmount(p1.getAmount());
			dto.setPaymentMethod(p1.getPaymentMethod());
			dto.setPaymentStatus(p1.getPaymentStatus());
			dto.setPaymentDate(p1.getPaymentDate());
			
			resp.add(dto);
		}
		
		return resp;
	}

	
}

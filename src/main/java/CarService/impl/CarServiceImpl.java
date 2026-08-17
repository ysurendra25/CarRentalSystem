package CarService.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import CarDto.car.CarRequestDto;
import CarDto.car.CarResponseDto;
import CarDto.car.CarUpdateDto;
import CarEntity.CarsTable;
import CarEntity.UserTable;
import CarEntity.availabilty_status;
import CarEntity.role;
import CarRepo.CarRepository;
import CarRepo.UserRepository;
import CarService.CarService;
import CarUtils.AuthUtils;

@Service
public class CarServiceImpl implements CarService {

	private  CarRepository carRepo;
	private UserRepository userRepo;
	
	public CarServiceImpl(CarRepository carRepo,UserRepository userRepo) {
		super();
		this.carRepo = carRepo;
		this.userRepo = userRepo;
	}

	@Override
	public CarResponseDto addCar(CarRequestDto request) {
		
		UserTable user = AuthUtils.getLoggedUser(userRepo);
	    if (user.getRole() != role.ADMIN && user.getRole() != role.OWNER) {
	        throw new RuntimeException("Only admin or owner can add cars");
	    }
		if (carRepo.existsByRegistrationNumber(request.getRegistrationNumber())) {
	        throw new RuntimeException("Registration number already exists");
	    }
		
		CarsTable car = new CarsTable();
		car.setBrand(request.getBrand());
		car.setModel(request.getModel());
		car.setYear(request.getYear());
		car.setRegistrationNumber(request.getRegistrationNumber());
		car.setPricePerDay(request.getPricePerDay());
		car.setAvailabilityStatus(request.getAvailabilityStatus());
		
	    CarsTable savedCar = carRepo.save(car);
		return convertToResponse(savedCar);
	}

	@Override
	public CarResponseDto updateCar(int carId, CarRequestDto request) {
		
		UserTable user = AuthUtils.getLoggedUser(userRepo);
	    if (user.getRole() != role.ADMIN && user.getRole() != role.OWNER) {
	        throw new RuntimeException("Only admin or owner can add cars");
	    }
		
		CarsTable car = carRepo.findById(carId).orElseThrow(()->new RuntimeException("Car not Found"));
		 if (!car.getRegistrationNumber().equals(request.getRegistrationNumber())
		            && carRepo.existsByRegistrationNumber(request.getRegistrationNumber())) {

		        throw new RuntimeException("Registration number already exists");
		    }
	
		car.setBrand(request.getBrand());
		car.setModel(request.getModel());
		car.setYear(request.getYear());
		car.setRegistrationNumber(request.getRegistrationNumber());
		car.setPricePerDay(request.getPricePerDay());
		car.setAvailabilityStatus(request.getAvailabilityStatus());
		
		CarsTable updatedCar = carRepo.save(car);
		
		return convertToResponse(updatedCar);
	}
	
	private CarResponseDto convertToResponse(CarsTable car) {
		
		CarResponseDto response = new CarResponseDto();
		
		response.setCarId(car.getCarId());
        response.setBrand(car.getBrand());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setRegistrationNumber(car.getRegistrationNumber());
        response.setPricePerDay(car.getPricePerDay());
        response.setAvailabilityStatus(car.getAvailabilityStatus());
		
		return response;
	}
	
	@Override
	public CarResponseDto partialUpdate(int carId, CarUpdateDto request) {
		
		UserTable user = AuthUtils.getLoggedUser(userRepo);
	    if (user.getRole() != role.ADMIN && user.getRole() != role.OWNER) {
	        throw new RuntimeException("Only admin or owner can add cars");
	    }
		
		CarsTable car = carRepo.findById(carId).orElseThrow(()->new RuntimeException("Car Not Found"));
		
		if(request.getBrand()!=null) {
			car.setBrand(request.getBrand());
		}
		if (request.getModel() != null) {
		    car.setModel(request.getModel());
		}

		if (request.getYear() != null) {
		    car.setYear(request.getYear());
		}

		if (request.getRegistrationNumber() != null) {
		    car.setRegistrationNumber(request.getRegistrationNumber());
		}

		if (request.getPricePerDay() != null) {
		    car.setPricePerDay(request.getPricePerDay());
		}

		if (request.getAvailabilityStatus()!=null) {
		    car.setAvailabilityStatus(request.getAvailabilityStatus());
		}
		
		CarsTable updatedCar = carRepo.save(car);
		
		return convertToResponse(updatedCar);
	}

	@Override
	public String deleteCar(int carId) {
		
		UserTable user = AuthUtils.getLoggedUser(userRepo);
	    if (user.getRole() != role.ADMIN && user.getRole() != role.OWNER) {
	        throw new RuntimeException("Only admin or owner can add cars");
	    }
		
		CarsTable car = carRepo.findById(carId).orElseThrow(()->new RuntimeException("Car Not found"));
		carRepo.delete(car);
		
		return "Car Deleted Successfully";
	}

	@Override
	public CarResponseDto getCarById(int carId) {
		
		CarsTable car = carRepo.findById(carId).orElseThrow(()->new RuntimeException("Car Not Found"));
		
		return convertToResponse(car);
	}

	@Override
	public List<CarResponseDto> getAllCars() {
		
		List<CarsTable> cars = carRepo.findAll();
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}

	@Override
	public List<CarResponseDto> getCarsByBrand(String brand) {
		
		List<CarsTable> cars = carRepo.findByBrand(brand);
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}

	@Override
	public List<CarResponseDto> getCarsByModel(String model) {
		
		List<CarsTable> cars = carRepo.findByModel(model);
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}

	@Override
	public List<CarResponseDto> getAvailableCars() {
		
		List<CarsTable> cars = carRepo.findByAvailabilityStatus(availabilty_status.available);
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}



}

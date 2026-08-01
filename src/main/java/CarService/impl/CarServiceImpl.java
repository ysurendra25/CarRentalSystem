package CarService.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CarDto.car.CarRequestDto;
import CarDto.car.CarResponseDto;
import CarDto.car.CarUpdateDto;
import CarEntity.CarsTable;
import CarEntity.CartTable;
import CarEntity.UserTable;
import CarEntity.availabilty_status;
import CarEntity.status;
import CarRepo.CarRepository;
import CarService.CarService;

@Service
public class CarServiceImpl implements CarService {

	private final CarRepository repo;
	
	public CarServiceImpl(CarRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public CarResponseDto addCar(CarRequestDto request) {
		
		CarsTable car = new CarsTable();
		car.setBrand(request.getBrand());
		car.setModel(request.getModel());
		car.setYear(request.getYear());
		car.setRegistration_number(request.getRegistrationNumber());
		car.setPrice_per_day(request.getPricePerDay());
		car.setAvailabilty_status(request.getAvailabilityStatus());
		
		if (repo.existsByRegistrationNumber(request.getRegistrationNumber())) {
	        throw new RuntimeException("Registration number already exists");
	    }
		
	    CarsTable savedCar = repo.save(car);
		return convertToResponse(savedCar);
	}

	@Override
	public CarResponseDto updateCar(int carId, CarRequestDto request) {
		
		CarsTable car = repo.findById(carId).orElseThrow(()->new RuntimeException("Car not Found"));
		 if (!car.getRegistration_number().equals(request.getRegistrationNumber())
		            && repo.existsByRegistrationNumber(request.getRegistrationNumber())) {

		        throw new RuntimeException("Registration number already exists");
		    }
	
		car.setBrand(request.getBrand());
		car.setModel(request.getModel());
		car.setYear(request.getYear());
		car.setRegistration_number(request.getRegistrationNumber());
		car.setPrice_per_day(request.getPricePerDay());
		car.setAvailabilty_status(request.getAvailabilityStatus());
		
		CarsTable updatedCar = repo.save(car);
		
		return convertToResponse(updatedCar);
	}
	
	private CarResponseDto convertToResponse(CarsTable car) {
		CarResponseDto response = new CarResponseDto();
		
		response.setCarId(car.getCar_id());
        response.setBrand(car.getBrand());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setRegistrationNumber(car.getRegistration_number());
        response.setPricePerDay(car.getPrice_per_day());
        response.setAvailabiltyStatus(car.getAvailabilty_status());
		
		return response;
	}
	
	@Override
	public CarResponseDto partialUpdate(int carId, CarUpdateDto request) {
		
		CarsTable car = repo.findById(carId).orElseThrow(()->new RuntimeException("Car Not Found"));
		
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
		    car.setRegistration_number(request.getRegistrationNumber());
		}

		if (request.getPricePerDay() != null) {
		    car.setPrice_per_day(request.getPricePerDay());
		}

		if (request.getAvailabiltyStatus()!=null) {
		    car.setAvailabilty_status(request.getAvailabiltyStatus());
		}
		
		CarsTable updatedCar = repo.save(car);
		
		return convertToResponse(updatedCar);
	}

	@Override
	public String deleteCar(int carId) {
		
		CarsTable car = repo.findById(carId).orElseThrow(()->new RuntimeException("Car Not found"));
		repo.delete(car);
		
		return "Car Deleted Successfully";
	}

	@Override
	public CarResponseDto getCarById(int carId) {
		CarsTable car = repo.findById(carId).orElseThrow(()->new RuntimeException("Car Not Found"));
		
		return convertToResponse(car);
	}

	@Override
	public List<CarResponseDto> getAllCars() {
		
		List<CarsTable> cars = repo.findAll();
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}

	@Override
	public List<CarResponseDto> getCarsByBrand(String brand) {
		
		List<CarsTable> cars = repo.findByBrand(brand);
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}

	@Override
	public List<CarResponseDto> getCarsByModel(String model) {
		
		List<CarsTable> cars = repo.findByModel(model);
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}

	@Override
	public List<CarResponseDto> getAvailableCars() {
		
		List<CarsTable> cars = repo.findByAvailabiltyStatus(availabilty_status.available);
		List<CarResponseDto> respList = new ArrayList<>();
		for(CarsTable car:cars) {
			respList.add(convertToResponse(car));
		}
		return respList;
	}



}

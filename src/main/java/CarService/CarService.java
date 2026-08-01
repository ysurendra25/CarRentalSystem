package CarService;

import java.util.List;

import CarDto.car.CarRequestDto;
import CarDto.car.CarResponseDto;
import CarDto.car.CarUpdateDto;

public interface CarService {

    CarResponseDto addCar(CarRequestDto request);

    CarResponseDto updateCar(int carId, CarRequestDto request);
    
    CarResponseDto partialUpdate(int carId,CarUpdateDto request);

    String deleteCar(int carId);

    CarResponseDto getCarById(int carId);

    List<CarResponseDto> getAllCars();

    List<CarResponseDto> getCarsByBrand(String brand);

    List<CarResponseDto> getCarsByModel(String model);

    List<CarResponseDto> getAvailableCars();

}
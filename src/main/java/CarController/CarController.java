package CarController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import CarDto.car.CarRequestDto;
import CarDto.car.CarResponseDto;
import CarDto.car.CarUpdateDto;
import CarService.CarService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Add Car
    @PostMapping("/addCar")
    public ResponseEntity<CarResponseDto> addCar(
            @Valid @RequestBody CarRequestDto request) {

        CarResponseDto response = carService.addCar(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Full Update
    @PutMapping("/{carId}")
    public ResponseEntity<CarResponseDto> updateCar(
            @PathVariable("carId") int carId,
            @Valid @RequestBody CarRequestDto request) {

        CarResponseDto response = carService.updateCar(carId, request);

        return ResponseEntity.ok(response);
    }

    // Partial Update
    @PatchMapping("/{carId}")
    public ResponseEntity<CarResponseDto> partialUpdate(
            @PathVariable("carId") int carId,
            @RequestBody CarUpdateDto request) {

        CarResponseDto response = carService.partialUpdate(carId, request);

        return ResponseEntity.ok(response);
    }

    // Delete Car
    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(
            @PathVariable("carId") int carId) {

        String response = carService.deleteCar(carId);

        return ResponseEntity.ok(response);
    }

    // Get Car By Id
    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseDto> getCarById(
            @PathVariable("carId") int carId) {

        CarResponseDto response = carService.getCarById(carId);

        return ResponseEntity.ok(response);
    }

    // Get All Cars
    @GetMapping("/all")
    public ResponseEntity<List<CarResponseDto>> getAllCars() {

        List<CarResponseDto> response = carService.getAllCars();

        return ResponseEntity.ok(response);
    }

    // Get Cars By Brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CarResponseDto>> getCarsByBrand(
            @PathVariable("brand") String brand) {

        List<CarResponseDto> response = carService.getCarsByBrand(brand);

        return ResponseEntity.ok(response);
    }

    // Get Cars By Model
    @GetMapping("/model/{model}")
    public ResponseEntity<List<CarResponseDto>> getCarsByModel(
            @PathVariable("model") String model) {

        List<CarResponseDto> response = carService.getCarsByModel(model);

        return ResponseEntity.ok(response);
    }

    // Get Available Cars
    @GetMapping("/available")
    public ResponseEntity<List<CarResponseDto>> getAvailableCars() {

        List<CarResponseDto> response = carService.getAvailableCars();

        return ResponseEntity.ok(response);
    }

}
package CarController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import CarDto.user.LoginRequestDto;
import CarDto.user.LoginResponseDto;
import CarDto.user.RegisterRequestDto;
import CarDto.user.UserResponseDto;
import CarService.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @Validated @RequestBody RegisterRequestDto request) {

        UserResponseDto response = userService.registerUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
    	return ResponseEntity.ok(userService.login(request));
    }

}
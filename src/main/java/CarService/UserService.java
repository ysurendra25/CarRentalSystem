package CarService;

import CarDto.user.LoginRequestDto;
import CarDto.user.LoginResponseDto;
import CarDto.user.RegisterRequestDto;
import CarDto.user.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(RegisterRequestDto request);
    
    LoginResponseDto login(LoginRequestDto request);

}
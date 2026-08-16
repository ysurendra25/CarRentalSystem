package CarService.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import CarDto.user.LoginRequestDto;
import CarDto.user.LoginResponseDto;
import CarDto.user.RegisterRequestDto;
import CarDto.user.UserResponseDto;
import CarEntity.UserTable;
import CarEntity.owner_status;
import CarEntity.role;
import CarException.EmailAlreadyExistsException;
import CarException.PhoneAlreadyExistsException;
import CarRepo.UserRepository;
import CarService.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        super();
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto registerUser(RegisterRequestDto request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if (userRepo.existsByPhone(request.getPhone())) {
            throw new PhoneAlreadyExistsException("Phone number already exists");
        }
        UserTable user = new UserTable();
        user.setPname(request.getPname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(role.CUSTOMER);
        UserTable savedUser = userRepo.save(user);
        return convertToResponse(savedUser);
    }

    private UserResponseDto convertToResponse(UserTable user) {
        UserResponseDto response = new UserResponseDto();
        response.setUserId(user.getUserId());
        response.setPname(user.getPname());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().name());
        return response;
    }
}
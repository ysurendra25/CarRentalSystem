package CarService.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import CarDto.user.LoginRequestDto;
import CarDto.user.LoginResponseDto;
import CarDto.user.RegisterRequestDto;
import CarDto.user.UserResponseDto;
import CarEntity.UserTable;
import CarEntity.role;
import CarException.EmailAlreadyExistsException;
import CarException.PhoneAlreadyExistsException;
import CarRepo.UserRepository;
import CarService.UserService;
import Security1.JwtService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
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

        // Encrypt Password
        user.setPassword_hash(passwordEncoder.encode(request.getPassword()));

        user.setAddress(request.getAddress());

        // Default Role
        user.setRole(role.CUSTOMER);

        UserTable savedUser = userRepo.save(user);

        UserResponseDto response = new UserResponseDto();

        response.setUserId(savedUser.getUser_id());
        response.setPname(savedUser.getPname());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setRole(savedUser.getRole().name());

        return response;
    }
    
    @Override
    public LoginResponseDto login(LoginRequestDto request) {
    	UserTable user = userRepo.findByEmail(request.getEmail())
    			.orElseThrow(()-> new UsernameNotFoundException("Invalid Email"));
    	
    	if (!passwordEncoder.matches(request.getPassword(), user.getPassword_hash())) {
    	    throw new RuntimeException("Invalid Password");
    	}
    	
    	String token = jwtService.generateToken(user);
    	
    	return new LoginResponseDto(
    			token,
    			user.getEmail(),
    			user.getRole().name());
    }

}
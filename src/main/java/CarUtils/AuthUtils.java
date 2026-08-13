package CarUtils;

import org.springframework.security.core.context.SecurityContextHolder;

import CarEntity.UserTable;
import CarRepo.UserRepository;

public class AuthUtils {

	public static UserTable getLoggedUser(UserRepository userRepo) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
	}
}

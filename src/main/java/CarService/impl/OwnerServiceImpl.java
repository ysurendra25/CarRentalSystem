package CarService.impl;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import CarDto.owner.OwnerRequestDto;
import CarDto.owner.OwnerResponseDto;
import CarDto.user.UserResponseDto;
import CarEntity.UserTable;
import CarEntity.owner_status;
import CarEntity.role;
import CarRepo.UserRepository;
import CarService.OwnerService;
import CarUtils.AuthUtils;

@Service
public class OwnerServiceImpl implements OwnerService{

	private UserRepository userRepo;

	@Override
	public OwnerResponseDto requestOwner(OwnerRequestDto request) {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		
		if (user.getOwnerStatus() == owner_status.REJECTED) {

            if (user.getOwnerRejectedAt() == null) {
                throw new RuntimeException(
                        "Owner rejection date not found");
            }

            long coolingDays = ChronoUnit.DAYS.between(
                    user.getOwnerRejectedAt(),
                    LocalDateTime.now());

            if (coolingDays < 30) {
                throw new RuntimeException(
                        "You are under the 30-day cooling period");
            }
        }
		if(user.getOwnerStatus()==owner_status.APPROVED) {
			throw new RuntimeException("You are already Owner!");
		}
		if(user.getOwnerStatus()==owner_status.PENDING) {
			throw new RuntimeException("your status is pending!");
		}
		if (user.getRole()!=role.CUSTOMER) {
		    throw new RuntimeException(
		        "Only customers can request owner access"
		    );
		}
			user.setRole(role.OWNER);
			user.setOwnerStatus(owner_status.PENDING);
			
		UserTable savedUser =  userRepo.save(user);
		
		OwnerResponseDto resp = new OwnerResponseDto();
		resp.setUserId(savedUser.getUser_id());
		resp.setPname(savedUser.getPname());
		resp.setEmail(savedUser.getEmail());
		resp.setOwnerStatus(savedUser.getOwnerStatus().name());
		resp.setMessage("Owner request submitted successfully");
		
		
		return resp;
	}
	
	
}

package CarService.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import CarDto.owner.OwnerRequestDto;
import CarDto.owner.OwnerResponseDto;
import CarEntity.UserTable;
import CarEntity.owner_status;
import CarEntity.role;
import CarRepo.UserRepository;
import CarService.OwnerService;
import CarUtils.AuthUtils;

@Service
public class OwnerServiceImpl implements OwnerService {

    private UserRepository userRepo;

    public OwnerServiceImpl(UserRepository userRepo) {
        super();
        this.userRepo = userRepo;
    }

    @Override
    public OwnerResponseDto requestOwner(OwnerRequestDto request) {
        UserTable user = AuthUtils.getLoggedUser(userRepo);
        if (user.getOwnerStatus() == owner_status.APPROVED) {

            throw new RuntimeException("You are already an owner!");
        }

        if (user.getOwnerStatus() == owner_status.PENDING) {

            throw new RuntimeException(
                    "Your owner request is already pending!");
        }

        if (user.getOwnerStatus() == owner_status.REJECTED) {

            if (user.getOwnerRejectedAt() == null) {
                throw new RuntimeException(
                        "Owner rejection date not found");
            }

            long coolingDays = ChronoUnit.DAYS.between(
                    user.getOwnerRejectedAt(),
                    LocalDateTime.now()
            );

            // 30-day cooling period
            if (coolingDays < 30) {
                throw new RuntimeException(
                        "You are under the 30-day cooling period");
            }

            // Cooling period completed
            user.setOwnerStatus(owner_status.PENDING);
            user.setOwnerRejectedAt(null);
        }

        // Normal customer requesting owner access
        else if (user.getRole() == role.CUSTOMER) {
            user.setOwnerStatus(owner_status.PENDING);
        }
        else {
            throw new RuntimeException(
                    "Invalid user state for owner request");
        }
        UserTable savedUser = userRepo.save(user);

        OwnerResponseDto resp = new OwnerResponseDto();

        resp.setUserId(savedUser.getUserId());
        resp.setPname(savedUser.getPname());
        resp.setEmail(savedUser.getEmail());
        resp.setOwnerStatus(
                savedUser.getOwnerStatus().name());
        resp.setMessage(
                "Owner request submitted successfully");

        return resp;
    }
}
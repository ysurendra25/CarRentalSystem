package CarService;

import CarDto.owner.OwnerRequestDto;
import CarDto.owner.OwnerResponseDto;

public interface OwnerService {

	OwnerResponseDto requestOwner(OwnerRequestDto request);
}

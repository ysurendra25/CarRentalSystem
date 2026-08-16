package CarController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CarDto.owner.OwnerRequestDto;
import CarDto.owner.OwnerResponseDto;
import CarService.OwnerService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/owner")
public class OwnerController {

	private OwnerService ownerService;
	public OwnerController(OwnerService ownerService) {
		super();
		this.ownerService = ownerService;
	}


	@PostMapping("/registerRequest")
	public OwnerResponseDto requestOwner(@RequestBody @Valid OwnerRequestDto request) {
	    return ownerService.requestOwner(request);
	}
	
}

package in.ling.restapi.controller;

import in.ling.restapi.dto.ProfileDTO;
import in.ling.restapi.io.ProfileRequest;
import in.ling.restapi.io.ProfileResponse;
import in.ling.restapi.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;


    /**
     * This method is used to create a new profile.
     * @param profileRequest the profile request
     * @return the profile response
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        log.info("API/ register called {}", profileRequest);
        ProfileDTO profileDTO = mapToProfileDTO(profileRequest);
        profileDTO = profileService.createProfile(profileDTO);
        log.info("Print the profile dto details {}", profileDTO);
        return mapToProfileResponse(profileDTO);
    }


    /**
     * This method is used to map ProfileRequest to ProfileDTO.
     * @param profileRequest the profile request
     * @return the profile dto
     */
    private ProfileDTO mapToProfileDTO(@Valid ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }

    /**
     * This method is used to map ProfileDTO to ProfileResponse.
     * @param profileDTO the profile dto
     * @return the profile response
     */
    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return  modelMapper.map(profileDTO, ProfileResponse.class);
    }
}

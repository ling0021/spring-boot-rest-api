package in.ling.restapi.service.impl;

import in.ling.restapi.dto.ProfileDTO;
import in.ling.restapi.entity.ProfileEntity;
import in.ling.restapi.repository.ProfileRepository;
import in.ling.restapi.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    /**
     * This method is used to create a new profile to database.
     * @param profileDTO the profile dto
     * @return the profile dto
     */
    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = mapToProfileEntity(profileDTO);
        profileEntity.setProfileId(UUID.randomUUID().toString());
        profileEntity = profileRepository.save(profileEntity);
        log.info("Print the profile entity details: {}", profileEntity);
        return mapToProfileDTO(profileEntity);
    }

    /**
     * This method is used to map ProfileEntity to ProfileDTO.
     * @param profileEntity the profile entity
     * @return the profile dto
     */
    private ProfileDTO mapToProfileDTO(ProfileEntity profileEntity) {
        return modelMapper.map(profileEntity, ProfileDTO.class);
    }

    /**
     * This method is used to map ProfileDTO to ProfileEntity.
     * @param profileDTO the profile dto
     * @return the profile entity
     */
    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileEntity.class);
    }
}

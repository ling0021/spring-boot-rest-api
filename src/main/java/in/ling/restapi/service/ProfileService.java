package in.ling.restapi.service;

import in.ling.restapi.dto.ProfileDTO;

public interface ProfileService {

    /**
     * This method is used to create a new profile to database.
     * @param profileDTO the profile dto
     * @return the profile dto
     */

    ProfileDTO createProfile(ProfileDTO profileDTO);

}

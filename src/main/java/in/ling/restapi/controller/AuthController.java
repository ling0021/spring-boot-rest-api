package in.ling.restapi.controller;

import in.ling.restapi.dto.ProfileDTO;
import in.ling.restapi.io.AuthRequest;
import in.ling.restapi.io.AuthResponse;
import in.ling.restapi.io.ProfileRequest;
import in.ling.restapi.io.ProfileResponse;
import in.ling.restapi.service.CustomUserDetailsService;
import in.ling.restapi.service.ProfileService;
import in.ling.restapi.service.TokenBlacklistService;
import in.ling.restapi.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;

    private final ProfileService profileService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsService userDetailsService;

    private final TokenBlacklistService tokenBlacklistService;


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

    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("API/ authenticate called {}", authRequest);
        authenticate(authRequest);
        final UserDetails userDetails =  userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, authRequest.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/signout")
    public void signout(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromRequest(request);
        if (jwtToken != null) {
            tokenBlacklistService.addTokenToBlacklist(jwtToken);
        }
    }

    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void authenticate(AuthRequest authRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        }catch (DisabledException ex){
            throw new Exception("Profile disabled");
        }catch (BadCredentialsException ex){
            throw new Exception("Bad Credentials");
        }
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

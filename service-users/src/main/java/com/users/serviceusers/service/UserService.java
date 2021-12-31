package com.users.serviceusers.service;

import com.users.serviceusers.api.dto.ClaimDto;
import com.users.serviceusers.api.dto.GameDto;
import com.users.serviceusers.repo.UserRepo;
import com.users.serviceusers.repo.model.User;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    private String claimsUrlAddress ="http://service-claims:8081/claims/user/";
    private String gamesUrlAddress = "http://service-game:8082/games/user/";

    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    public User fetchUserById(long id) throws  IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);

        if (maybeUser.isPresent()) return maybeUser.get();
        else
            throw new IllegalArgumentException("Invalid User id");
    }

    public long createUser(User newUser) {
        final User savedUser = userRepo.save(newUser);
        return savedUser.getId();
    }

    public void updateUser(long id, User updatedUser) throws  IllegalArgumentException{
        final Optional<User> maybeUser = userRepo.findById(id);

        if (maybeUser.isEmpty())
            throw new IllegalArgumentException("Invalid User Id");

        final User user = maybeUser.get();

        String country = updatedUser.getCountry();
        if (country != null && !country.isBlank())
            user.setCountry(country);

        String username = updatedUser.getUsername();
        if (username != null && !username.isBlank())
            user.setUsername(username);

        String password = updatedUser.getPassword();
        if (password != null && !password.isBlank())
            user.setPassword(password);

        String first_name = updatedUser.getFirst_name();
        if (first_name != null && !first_name.isBlank())
            user.setFirst_name(first_name);

        String last_name = updatedUser.getLast_name();
        if (last_name != null && !last_name.isBlank())
            user.setLast_name(last_name);

        String email = updatedUser.getEmail();
        if (email != null && !email.isBlank())
            user.setEmail(email);

        Long rating = updatedUser.getRating();
        if (rating != null) user.setRating(rating);

        userRepo.save(user);

    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public List<ClaimDto> getClaimsByUser (long id) throws IllegalArgumentException{

        final Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) throw new IllegalArgumentException("Invalid User Id");
        else {
            final RestTemplate restTemplate = new RestTemplate();
            final HttpEntity<Long> userRequest = new HttpEntity<>(id);
            final ResponseEntity<List<ClaimDto>> userResponse = restTemplate.exchange(claimsUrlAddress + id,
                           HttpMethod.GET, userRequest, new ParameterizedTypeReference<List<ClaimDto>>(){});
            List<ClaimDto> claimsList = userResponse.getBody();
            if (claimsList.isEmpty()) throw new IllegalArgumentException("Claims not found");
                else {
                    return claimsList;
            }
        }
    }

    public List<GameDto> getGamesByUser(long id) throws IllegalArgumentException {
        final Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) throw new IllegalArgumentException("Invalid User Id");
        else {
            final RestTemplate restTemplate = new RestTemplate();
            final HttpEntity<Long> userRequest = new HttpEntity<>(id);
            final ResponseEntity<List<GameDto>> userResponse = restTemplate.exchange(gamesUrlAddress + id,
                    HttpMethod.GET, userRequest, new ParameterizedTypeReference<List<GameDto>>() {
                    });
            List<GameDto> gamesList = userResponse.getBody();
            if (gamesList.isEmpty()) throw new IllegalArgumentException("Games not found");
            else
                return gamesList;
        }
    }

}

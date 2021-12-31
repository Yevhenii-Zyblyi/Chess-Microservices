package com.users.serviceusers.api;

import com.users.serviceusers.api.dto.ClaimDto;
import com.users.serviceusers.api.dto.GameDto;
import com.users.serviceusers.repo.model.User;
import com.users.serviceusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userService.fetchAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> showById(@PathVariable long id){
        try {
            final User user = userService.fetchUserById(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/claims")
    public ResponseEntity<List<ClaimDto>> showClaimsById(@PathVariable long id) {
        try {
            final List<ClaimDto> claims = userService.getClaimsByUser(id);
            return ResponseEntity.ok(claims);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameDto>> showGamesById(@PathVariable long id) {
        try {
            final List<GameDto> games = userService.getGamesByUser(id);
            return ResponseEntity.ok(games);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody User user) {
        final long userId = userService.createUser(user);
        final String userUri = String.format("/cars/%d", userId);

        return ResponseEntity.created(URI.create(userUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}

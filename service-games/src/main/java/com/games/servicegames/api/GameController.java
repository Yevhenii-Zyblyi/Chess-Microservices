package com.games.servicegames.api;

import com.games.servicegames.api.dto.GameDto;
import com.games.servicegames.repo.model.Game;
import com.games.servicegames.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> index() {
        final List<Game> games = gameService.fetchAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> showById(@PathVariable long id) {
        try {
            final Game game = gameService.fetchGameById(id);

            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Game>> showByUserId(@PathVariable long id){
        try {
            final List<Game> games = gameService.fetchGameByUserId(id);

            return  ResponseEntity.ok(games);
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody GameDto game) {
        final long whiteId = game.whiteId();
        final long blackId = game.blackId();
        final String status = game.status();
        final String result = game.result();
        final long duration = game.duration();
        final String variant = game.variant();
        final String timeControl = game.timeControl();
        final long gameId = gameService.createGame(whiteId, blackId, status, result, duration, variant, timeControl);

        final String gameUri = String.format("/games/%d", gameId);

        return ResponseEntity.created(URI.create(gameUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody GameDto game) {
        final long whiteId = game.whiteId();
        final long blackId = game.blackId();
        final String status = game.status();
        final String result = game.result();
        final long duration = game.duration();
        final String variant = game.variant();
        final String timeControl = game.timeControl();

        try {
            gameService.updateGame(id, whiteId, blackId, status, result, duration, variant, timeControl);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {

        gameService.deleteGame(id);

        return ResponseEntity.noContent().build();
    }

}

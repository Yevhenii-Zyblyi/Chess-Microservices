package com.games.servicegames.service;

import com.games.servicegames.repo.GameRepo;
import com.games.servicegames.repo.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepo gameRepo;

    public List<Game> fetchAll() {
        return gameRepo.findAll();
    }

    public Game fetchGameById(long id) throws IllegalArgumentException {
        final Optional<Game> maybeGame = gameRepo.findById(id);

        if (maybeGame.isPresent()) return maybeGame.get();
        else
            throw new IllegalArgumentException("Game not found");
    }

    public List<Game> fetchGameByUserId(long id) throws IllegalArgumentException{
        final List<Game> maybeGames = gameRepo.findByWhiteIdOrBlackId(id, id);
        if (maybeGames.isEmpty()) throw new IllegalArgumentException("Invalid User Id");
        else
            return maybeGames;
    }

    public long createGame(long whiteId, long blackId, String status, String result,
                           long duration, String variant, String timeControl) {
        final Game game = new Game(whiteId, blackId, status, result, duration, variant, timeControl);
        final Game savedGame = gameRepo.save(game);

        return savedGame.getId();
    }

    public void updateGame(long id, long whiteId, long blackId, String status,
                           String result, long duration, String variant,
                           String timeControl) throws IllegalArgumentException {
        final Optional<Game> maybeGame = gameRepo.findById(id);

        if (maybeGame.isEmpty())
            throw new IllegalArgumentException("Invalid Game Id");

        final Game game = maybeGame.get();
        if(whiteId > 0) game.setWhiteId(whiteId);
        if(blackId > 0) game.setBlackId(blackId);
        if(status != null && !status.isBlank()) game.setStatus(status);
        if(result != null && !result.isBlank()) game.setResult(result);
        if(duration > 0 ) game.setDuration(duration);
        if(variant != null && !variant.isBlank()) game.setVariant(variant);
        if(timeControl != null && !timeControl.isBlank()) game.setTimeControl(timeControl);

        gameRepo.save(game);
    }

    public void deleteGame(long id ) {
        gameRepo.deleteById(id);
    }
}

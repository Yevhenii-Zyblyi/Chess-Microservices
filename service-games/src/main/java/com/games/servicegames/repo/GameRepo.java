package com.games.servicegames.repo;

import com.games.servicegames.repo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {
    List<Game> findByWhiteIdOrBlackId(long whiteId ,long blackId);
}

package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GamesRepository repo;

    public Games create(Games game) {
        log.info("Creating game: {}", game.getName());
        game.setId(null); // let MongoDB assign ObjectId
        validate(game);

        return repo.save(game);
    }

    public List<Games> findAll() {
        log.info("Finding all games");
        return repo.findAll();
    }

    public Games findById(String id) {
        log.info("Finding game by id {}", id);
        Optional<Games> optionalGame = repo.findById(id);
        if (optionalGame.isEmpty()) {
            log.error("Attempted to find non-existing game id: {}", id);
            throw new ResourceNotFoundException("Game not found: " + id);
        }
        return optionalGame.get();
    }

    public Games update(String id, Games game) {
        Optional<Games> optionalGame = repo.findById(id);
        if (optionalGame.isEmpty()) {
            log.error("Attempted to update non-existing game id: {}", id);
            throw new ResourceNotFoundException("Game not found: " + id);
        }

        log.info("Updating game by id {}", id);
        Games oldGame = optionalGame.get();

        // update fields
        oldGame.setName(game.getName());
        oldGame.setPrice(game.getPrice());
        oldGame.setDescription(game.getDescription());

        validate(oldGame);
        return repo.save(oldGame);
    }

    public boolean delete(String id) {
        Optional<Games> optionalGame = repo.findById(id);
        if (optionalGame.isEmpty()) {
            log.error("Attempted to delete non-existing game id: {}", id);
            throw new ResourceNotFoundException("Game not found: " + id);
        }
        log.info("Deleting game by id {}", id);
        repo.deleteById(id);
        return true;
    }

    private void validate(Games game) {
        if (game.getName() == null || game.getName().trim().isEmpty()) {
            log.error("Name cannot be null or empty");
            throw new BusinessException("Name cannot be null or empty");
        }
        if (game.getPrice() == null || game.getPrice() <= 0) {
            log.error("Price must be greater than zero");
            throw new BusinessException("Price must be greater than zero");
        }
    }
}

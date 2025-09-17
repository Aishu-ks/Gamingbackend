package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/games")
public class GamesController {

    @Autowired
    private GamesRepository repo;

    @PostMapping
    public Games create(@RequestBody Games game) {
        game.setId(null);
        Games savedGame = repo.save(game);
        return savedGame;
    }

    @GetMapping
    public List<Games> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Games findById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping(path="/{id}")
    public Games update(@PathVariable String id, @RequestBody Games game) {
        Games oldGame = repo.findById(id).orElse(null);
        if (oldGame != null) {
            oldGame.setName(game.getName());
            oldGame.setDescription(game.getDescription());

            oldGame.setPrice(game.getPrice());
            return repo.save(oldGame);
        }
        return null;
    }

    @DeleteMapping(path="/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<Games> optionalGame = repo.findById(id);
        if(optionalGame.isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}


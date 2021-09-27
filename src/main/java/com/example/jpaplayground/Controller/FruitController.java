package com.example.jpaplayground.Controller;


import com.example.jpaplayground.Model.Fruit;
import com.example.jpaplayground.Repository.FruitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FruitController {

    private final FruitRepository fruitRepository;

    public FruitController(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @GetMapping("/fruits")
    public Iterable<Fruit> getAllFruitsFromDb() {
        return this.fruitRepository.findAll();
    }

    @GetMapping("/fruits/ripe")
    public List<Fruit> getAllRipeFruit() {
        return this.fruitRepository.findAllByRipeIsTrue();
    }

    @GetMapping("/fruits/color/{color}")
    public List<Fruit> getAllFruitByColor(@PathVariable("color") String color) {
        return this.fruitRepository.findAllByColor(color);
    }

    @PostMapping("/fruits")
    public Fruit addFruitToDatabase(@RequestBody Fruit fruit) {
        return this.fruitRepository.save(fruit);
    }

    @DeleteMapping("/fruits/{id}")
    public Optional<Fruit> deleteFruitById(@PathVariable Long id) {
        Optional<Fruit> itemDeleted = this.fruitRepository.findById(id);
        this.fruitRepository.deleteById(id);
        return itemDeleted;
    }

    @GetMapping("/fruits/{id}")
    public Optional<Fruit> getFruitById(@PathVariable("id") Long id) {
        return this.fruitRepository.findById(id);
    }

    @GetMapping("/fruits/name/{name}")
    public Iterable<Fruit> getFruitByName(@PathVariable String name) {
        return this.fruitRepository.findAllByName(name);
    }

}

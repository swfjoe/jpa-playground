package com.example.jpaplayground.Controller;


import com.example.jpaplayground.Model.Fruit;
import com.example.jpaplayground.Repository.FruitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/fruits")
    public void addFruitToDatabase(Fruit fruit) {
        this.fruitRepository.save(fruit);
    }


}

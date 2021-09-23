package com.example.jpaplayground.Repository;

import com.example.jpaplayground.Model.Fruit;
import org.springframework.data.repository.CrudRepository;

public interface FruitRepository extends CrudRepository<Fruit, Long> {
}

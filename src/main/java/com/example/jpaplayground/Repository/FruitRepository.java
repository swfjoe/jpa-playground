package com.example.jpaplayground.Repository;

import com.example.jpaplayground.Model.Fruit;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FruitRepository extends CrudRepository<Fruit, Long> {
    List<Fruit> findAllByColor(String color);

    List<Fruit> findAllByRipeIsTrue();

    Iterable<Fruit> findAllByName(String name);

    Iterable<Fruit> findAllByExpiresOnBetween(Date startDate, Date endDate);
}

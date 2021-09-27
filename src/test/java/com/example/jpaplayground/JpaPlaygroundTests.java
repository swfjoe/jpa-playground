package com.example.jpaplayground;

import com.example.jpaplayground.Model.Fruit;
import com.example.jpaplayground.Repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class JpaPlaygroundTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	FruitRepository newFruitRepository;

	@Transactional
	@Rollback
	@Test
	void testGetSingleFruitById() throws Exception {
		//Setup
		Fruit testFruit = new Fruit();
		testFruit.setRipe(true);
		testFruit.setColor("orange");
		testFruit.setName("Orange");

		this.newFruitRepository.save(testFruit);

		//Execution
		this.mvc.perform(get("/fruits/1"))

				//Assertions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(testFruit.getId().intValue())))
				.andExpect(jsonPath("$.name", is("Orange")));
	}

	@Transactional
	@Rollback
	@Test
	public void testAddFruitToDatabase() throws Exception {
		//setup
		String json = "{\"name\":\"Pineapple\",\"color\":\"yellow\",\"ripe\":true}";

		//Execution
		this.mvc.perform(post("/fruits")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))

				//Assertion
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name", is ("Pineapple")));
	}


	@Transactional
	@Rollback
	@Test
	void testGetAllFruits() throws Exception {
		//Setup
		Fruit testFruit = new Fruit();
		testFruit.setRipe(true);
		testFruit.setColor("orange");
		testFruit.setName("orange");

		this.newFruitRepository.save(testFruit);

		//Execution
		this.mvc.perform(get("/fruits"))

		//Assertions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id", is(testFruit.getId().intValue())))
		.andExpect(jsonPath("$[0].name", is("orange")));
	}

	@Transactional
	@Rollback
	@Test
	public void testGetFruitByName() throws Exception {
		//Setup
		Fruit testFruit = new Fruit();
		testFruit.setRipe(true);
		testFruit.setColor("purple");
		testFruit.setName("grape");

		this.newFruitRepository.save(testFruit);

		//Execution
		this.mvc.perform(get("/fruits/name/grape"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("grape")));

	}

}

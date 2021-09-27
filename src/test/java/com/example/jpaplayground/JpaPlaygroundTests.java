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
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
	public void testGetSingleFruitById() throws Exception {
		//Setup
		Fruit testFruit = new Fruit();
		testFruit.setRipe(true);
		testFruit.setColor("orange");
		testFruit.setName("Orange");

		this.newFruitRepository.save(testFruit);

		//Execution
		this.mvc.perform(get(String.format("/fruits/%d", testFruit.getId())))

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
	public void testPatchMethodModifiesFruitInDatabase() throws Exception {
		//Setup
		Fruit testFruit = new Fruit();
		testFruit.setRipe(true);
		testFruit.setColor("yellow");
		testFruit.setName("Pineapple");

		//setup
		String json = "{\"name\":\"Pineapple\",\"color\":\"yellow\",\"ripe\":false}";

		this.newFruitRepository.save(testFruit);

		//Execution
		this.mvc.perform(patch("/fruits/update/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))

		//Assertions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ripe", is(false)));

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
		this.mvc.perform(get("/fruits/find/grape"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("grape")));

	}

	@Transactional
	@Rollback
	@Test
	public void testGetFruitByExpirationDateRange() throws Exception {
		//Setup
		Fruit testFruit1 = new Fruit();

		testFruit1.setRipe(true);
		testFruit1.setColor("purple");
		testFruit1.setName("Grape");
		testFruit1.setExpiresOn(new Date(2021-1900, Calendar.OCTOBER,01));

		Fruit testFruit2 = new Fruit();
		testFruit2.setRipe(true);
		testFruit2.setColor("green");
		testFruit2.setName("Grape");
		testFruit2.setExpiresOn(new Date(2021-1900, Calendar.OCTOBER,02));

		Fruit testFruit3 = new Fruit();
		testFruit3.setRipe(true);
		testFruit3.setColor("purple");
		testFruit3.setName("Pineapple");
		testFruit3.setExpiresOn(new Date(2021-1900, Calendar.OCTOBER,03));

		Fruit testFruit4 = new Fruit();
		testFruit4.setRipe(true);
		testFruit4.setColor("orange");
		testFruit4.setName("Orange");
		testFruit4.setExpiresOn(new Date(2021-1900, Calendar.OCTOBER,04));

		this.newFruitRepository.save(testFruit1);
		this.newFruitRepository.save(testFruit2);
		this.newFruitRepository.save(testFruit3);
		this.newFruitRepository.save(testFruit4);

		//Execution
//		this.mvc.perform(get("/fruits"))
//		.andExpect(status().is4xxClientError());
		this.mvc.perform(get("/fruits/between?startDate=2021-10-01&endDate=2021-10-03"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("Grape")))
				.andExpect(jsonPath("$[1].name", is("Grape")))
				.andExpect(jsonPath("$[2].name", is("Pineapple")));

	}

}

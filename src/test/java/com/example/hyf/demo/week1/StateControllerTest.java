package com.example.hyf.demo.week1;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StateControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void stateIsSetToInitialState() throws Exception {
		expectStateWhen(10, mvc.perform(get("/state")));
	}

	@Test
	public void addIncreaseState() throws Exception {
		expectStateWhen(11, mvc.perform(post("/add")));
	}

	@Test
	public void subtractsDecreaseState() throws Exception {
		expectStateWhen(9, mvc.perform(post("/subtract")));
	}

	@Test
	public void resetSetStateToInitialState() throws Exception {
		expectStateWhen(10, mvc.perform(post("/reset")));
	}

	@Test
	public void addAndResetSetStateToInitialState() throws Exception {
		expectStateWhen(11, mvc.perform(post("/add")));
		expectStateWhen(10, mvc.perform(post("/reset")));
	}

	@Test
	public void subtractAndResetSetStateToInitialState() throws Exception {
		expectStateWhen(9, mvc.perform(post("/subtract")));
		expectStateWhen(10, mvc.perform(post("/reset")));
	}

	private void expectStateWhen(int state, ResultActions action) throws Exception {
		action.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("state").value(state));
	}

}

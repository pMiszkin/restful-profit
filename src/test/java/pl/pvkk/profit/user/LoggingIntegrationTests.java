package pl.pvkk.profit.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoggingIntegrationTests {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private ObjectWriter ow;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ow = mapper.writer().withDefaultPrettyPrinter();
	}
	
	@Test
	public void testAddAndPrintUser() throws Exception {
		User user = new User();
		user.setLogin("ernest");
		user.setPassword("pass");
		
	    String requestJson=ow.writeValueAsString(user);

		//add example user
		this.mockMvc
			.perform(post("/user/add")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(requestJson))
			.andExpect(status().isOk());

		//and try to get him
		this.mockMvc
			.perform(get("/user/print/1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testAddUserWithWrongData() throws Exception {
		User user = new User();
		user.setLogin("ern");
		user.setPassword("pass");
		
	    String requestJson=ow.writeValueAsString(user);

	    //login is too short
		this.mockMvc
			.perform(post("/user/add")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(requestJson))
			.andExpect(status().isBadRequest());
		
		//hope he hasn't been saved
		this.mockMvc
			.perform(post("/user/print/1")
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void testAddTakenLogin() throws Exception {
		User user = new User();
		user.setLogin("ernest");
		user.setPassword("pass");
		
	    String requestJson=ow.writeValueAsString(user);
		
	    this.mockMvc
			.perform(post("/user/add")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(requestJson))
			.andExpect(status().isOk());
		
	    //tryin to save the same user two times
	    this.mockMvc
			.perform(post("/user/add")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(requestJson))
			.andExpect(status().isBadRequest());
	}

}

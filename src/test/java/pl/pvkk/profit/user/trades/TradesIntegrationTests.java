package pl.pvkk.profit.user.trades;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@PreAuthorize("hasRole('USER')")
public class TradesIntegrationTests {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	private MockMvc mockMvc;
	

	@Before
	public void init() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
	}
	
	private RequestBuilder request(String url) {
		return post(url)
			    	.with(user("login").password("password").roles("USER"))
			    	.with(csrf())
			    	.accept(MediaType.parseMediaType("text/plain;charset=UTF-8"));
	}
	
	@Test
	@WithMockUser("login")
	public void testBuyShares() throws Exception {
		//positive version
		this.mockMvc
			.perform(request("/user/pocket/transfer/purchases?name=PLLOTOS00025&number=5"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/plain;charset=UTF-8"));
		
		//wrong number
		this.mockMvc
			.perform(request("/user/pocket/transfer/purchases?name=PLLOTOS00025&number=a"))
			.andExpect(status().isBadRequest());
		
		//wrong name
		this.mockMvc
			.perform(request("/user/pocket/transfer/purchases?name=aasd&number=5"))
			.andExpect(status().isBadRequest());
	}
	

}

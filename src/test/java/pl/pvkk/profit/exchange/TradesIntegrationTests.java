package pl.pvkk.profit.exchange;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TradesIntegrationTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	public static class MockSecurityContext implements SecurityContext {

		private static final long serialVersionUID = -1386535243513362694L;

		private Authentication authentication;

		public MockSecurityContext(Authentication authentication) {
			this.authentication = authentication;
		}

		@Override
		public Authentication getAuthentication() {
			return this.authentication;
		}

		@Override
		public void setAuthentication(Authentication authentication) {
			this.authentication = authentication;
		}
	}
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testBuyShares() throws Exception {
		//fine version
		this.mockMvc
			.perform(post("/user/pocket/transfer/purchases?name=PLLOTOS00025&number=5")
					.accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/plain;charset=UTF-8"));
		
		//wrong number
		this.mockMvc
			.perform(post("/user/pocket/transfer/purchases?name=PLLOTOS00025&number=a")
					.accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
			.andExpect(status().isBadRequest());
		
		//wrong name
		this.mockMvc
			.perform(post("/user/pocket/transfer/purchases?name=aasd&number=5")
					.accept(MediaType.parseMediaType("text/plain;charset=UTF-8")))
			.andExpect(status().isBadRequest());
	}

}

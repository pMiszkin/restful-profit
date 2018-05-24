package pl.pvkk.profit.trades;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradesRestControllerIntegration {

    @Autowired
    private MockMvc mockMvc;
    private final String BUY_URL = "/user/pocket/transfer/purchases";
    private final String SELL_URL = "/user/pocket/transfer/sales";
    private String shareShortcut;
    private int shareNumber;

    @Before
    public void setUp() {
        shareNumber = 0;
        shareShortcut = "PLLOTOS00025";
    }

    @Test
    @WithMockUser(roles = "USER")
    public void buyWithShareNumberLessThanOne() throws Exception {
        performPost(BUY_URL)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Share number must be greater than or equal to 1"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void sellWithShareNumberLessThanOne() throws Exception {
        performPost(SELL_URL)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Share number must be greater than or equal to 1"));
    }

    private ResultActions performPost(String url) throws Exception {
        return mockMvc.perform(post(url)
                .param("name", shareShortcut)
                .param("number", Integer.toString(shareNumber))
                .with(csrf().asHeader()));
    }
}

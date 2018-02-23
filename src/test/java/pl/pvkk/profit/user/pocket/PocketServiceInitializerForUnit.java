package pl.pvkk.profit.user.pocket;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.pvkk.profit.shares.SharesService;
import pl.pvkk.profit.user.UserService;

import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public abstract class PocketServiceInitializerForUnit {

    @Mock
    protected PocketDao pocketDao;
    @Mock
    protected SharesService sharesService;
    @Mock
    protected UserService userService;
    @InjectMocks
    protected PocketService pocketService;
    protected String username;
    protected String shareIsin;
    protected int shareNumber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Random random = new Random();
        username = "randomUsername";
        shareIsin = "randomShareIsin";
        shareNumber = 5;
    }
}

package dk.rbc.petstore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Not real tests per se, just a way to verify things
 * 
 * @author daaa
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SomeTests {
 
    /**
     * A fake test
     */
    @Test
    public void aTest() {
        Assert.assertTrue(true);
    }
}

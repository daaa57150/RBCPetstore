package dk.rbc.petstore.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import dk.rbc.petstore.domain.Status;
import dk.rbc.petstore.persistence.repositories.StatusRepo;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private StatusRepo statusRepo;

    @Test
    public void testStatusRepo() {
        
        // insert one
        String testName = "testname";
        Status testStatus = new Status(testName);
        statusRepo.save(testStatus);
        
        // did it get an id?
        assertThat(testStatus.getId()).isNotNull();
        
        // find it and see if the name matches
        assertThat(statusRepo.findOneByName(testName).getName()).isEqualTo(testName);
    }
 

}

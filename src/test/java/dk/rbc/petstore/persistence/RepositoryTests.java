package dk.rbc.petstore.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.persistence.repositories.CategoryRepo;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    public void testCategoryRepo() {
        
        // insert one
        String testName = "testname";
        Category testStatus = new Category(testName);
        categoryRepo.save(testStatus);
        
        // did it get an id?
        assertThat(testStatus.getId()).isNotNull();
        
        // find it and see if the name matches
        assertThat(categoryRepo.findOneByName(testName).getName()).isEqualTo(testName);
    }
 
    
}

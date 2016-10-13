package dk.rbc.petstore.persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.persistence.dao.PetDao;
import dk.rbc.petstore.persistence.repositories.CategoryRepo;
import dk.rbc.petstore.persistence.repositories.PetRepo;

/**
 * Tests the Pet Dao
 * 
 * @author daaa
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Application.class})
//@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = "dk.rbc.petstore.persistence") // instanciate only the persistence layer
public class PetDaoTests {
    
    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetDaoTests.class);
            
            
    /** The pet dao we are testing */
    @Autowired
    private PetDao petDao;
    
    /** The pet repo, which we assume works and will be used to validate the dao behaviour */
    @Autowired
    private PetRepo petRepo;
    
    /** The category repo, which we assume works */
    @Autowired
    private CategoryRepo categoryRepo;
    
    /** Category used in the test, this instance is saved in the DB beforehand  */
    private Category testCategorySaved = new Category("sheep");
    
    // We should write tests also with a detached category
//    /** Category used in the test, this instance is detached from the DB */
//    private Category testCategoryNotSaved = new Category("wolf");
    
    /** Pet stored in the DB for the tests */
    private Pet testPetSaved = new Pet("Shelby", testCategorySaved);
    
    /** And id we're sure is not in the DB */
    private static final Long ID_NOT_IN_DB = -1L;
    
    
    
    /** Prepares the test data before a test is run */
    @Before // cannot easily use @BeforeClass with JUnit
    public void setUp() {
        categoryRepo.save(testCategorySaved);
        LOGGER.info("Saved category: " + testCategorySaved);
        
        petRepo.save(testPetSaved);
        LOGGER.info("Saved pet: ");
        LOGGER.info(testPetSaved.toString());
    }
    
    
    /** Cleans the test data after the class has run */
    @After // cannot easily use @AfterClass with JUnit
    public void tearDown() {
        if(categoryRepo.exists(testCategorySaved.getId())) {
            categoryRepo.delete(testCategorySaved);
            LOGGER.info("Deleted category: " + testCategorySaved);
        }
        
        if(petRepo.exists(testPetSaved.getId())) {
            petRepo.delete(testPetSaved);
            LOGGER.info("Deleted pet: " + testPetSaved);
        }
    }
    
    
    /**
     * Tests {@link PetDao.createPet(Pet)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testCreatePetWithInstance() {
        String name = "Gunther";
        Pet gunther = new Pet(name, testCategorySaved);
        
        Pet guntherSaved = petDao.createPet(gunther);
        Assert.assertNotNull(guntherSaved);
        Assert.assertNotNull(guntherSaved.getId());
        Assert.assertEquals(gunther.getName(), guntherSaved.getName());
        
        LOGGER.info("test ok");
    }

    /**
     * Tests {@link PetDao.createPet(String, Category)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testCreatePetWithName() {
        String name = "Gunther";
        String category = testCategorySaved.getName();
        
        Pet guntherSaved = petDao.createPet(name, category);
        Assert.assertNotNull(guntherSaved);
        Assert.assertNotNull(guntherSaved.getId());
        Assert.assertEquals(name, guntherSaved.getName());
        
        LOGGER.info("test ok");
    }
    
    /**
     * Tests {@link PetDao.findPetById(Long)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testFindPetById() {
        // existing pet
        Pet found = petDao.findPetById(testPetSaved.getId());
        
        Assert.assertNotNull(found);
        Assert.assertEquals(testPetSaved.getId(), found.getId());
        
        // non existing pet
        Pet nope = petDao.findPetById(ID_NOT_IN_DB);
        Assert.assertNull(nope);
        
        LOGGER.info("test ok");
    }
    
    /**
     * Tests {@link PetDao.testDeletePetById(Long)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testDeletePetById() {
        // non existing pet
        boolean deleted = petDao.deletePetById(ID_NOT_IN_DB);
        Assert.assertFalse(deleted);
        
        // existing pet
        deleted = petDao.deletePetById(testPetSaved.getId());
        Assert.assertTrue(deleted);
        
        // confirm it was deleted using the petRepo
        Pet nope = petRepo.findOne(testPetSaved.getId());
        Assert.assertNull(nope);
        
        LOGGER.info("test ok");
    }
}









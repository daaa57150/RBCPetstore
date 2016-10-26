package dk.rbc.petstore.service;

import java.util.Arrays;

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

import com.google.common.collect.Iterables;

import dk.rbc.petstore.domain.entities.Category;
import dk.rbc.petstore.domain.entities.Pet;
import dk.rbc.petstore.domain.enums.Status;
import dk.rbc.petstore.persistence.repositories.CategoryRepo;
import dk.rbc.petstore.persistence.repositories.PetRepo;

/**
 * Tests the Pet Service
 * 
 * @author daaa
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Application.class})
//@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = "dk.rbc.petstore.service") // instanciate only the service layer
public class PetServiceTests {
    
    /** The logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetServiceTests.class);
            
            
    /** The pet service we are testing */
    @Autowired
    private PetService petService;
    
    /** The pet repo, which we assume works and will be used to validate the service behaviour */
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
    
    /** Pets stored in the DB for the tests */
    private Pet[] testPetsSaved = new Pet[] {
        new Pet("Shelby",   testCategorySaved, Status.AVAILABLE),
        new Pet("Leonard",  testCategorySaved, Status.PENDING),
        new Pet("Francis",  testCategorySaved, Status.SOLD)
    };
    
    /** And id we're sure is not in the DB */
    private static final Long ID_NOT_IN_DB = -1L;
    
    
    
    /** Prepares the test data before a test is run */
    @Before // cannot easily use @BeforeClass with JUnit
    public void setUp() {
        categoryRepo.save(testCategorySaved);
        LOGGER.info("Saved category: " + testCategorySaved);
        
        for(Pet pet: testPetsSaved) {
            petRepo.save(pet);
            LOGGER.info("Saved pet: ");
            LOGGER.info(pet.toString());
        }
        
        LOGGER.info("## SET UP OK ##");
    }
    
    
    /** Cleans the test data after the class has run */
    @After // cannot easily use @AfterClass with JUnit
    public void tearDown() {
        LOGGER.info("## TEAR DOWN... ##");
        
        for(Pet pet: testPetsSaved) {
            if(petRepo.exists(pet.getId())) {
                petRepo.delete(pet);
                LOGGER.info("Deleted pet: " + pet);
            }
        }
        
        if(categoryRepo.exists(testCategorySaved.getId())) {
            categoryRepo.delete(testCategorySaved);
            LOGGER.info("Deleted category: " + testCategorySaved);
        }
    }
    
    
    /**
     * Tests {@link PetService.createPet(Pet)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testCreatePetWithInstance() {
        String name = "Gunther";
        Pet gunther = new Pet(name, testCategorySaved);
        
        Pet guntherSaved = petService.createPet(gunther);
        Assert.assertNotNull(guntherSaved);
        Assert.assertNotNull(guntherSaved.getId());
        Assert.assertEquals(gunther.getName(), guntherSaved.getName());
        
        LOGGER.info("test ok");
    }
    
    /**
     * Tests if multiple pets can be created with the same category
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testCreateManyPets() {
        Pet pet1 = petService.createPet(new Pet("pet1", testCategorySaved));
        Pet pet2 = petService.createPet(new Pet("pet2", testCategorySaved));
        
        Assert.assertNotNull(pet1);
        Assert.assertNotNull(pet2);
    }

    /**
     * Tests {@link PetService.createPet(String, Category)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testCreatePetWithName() {
        String name = "Gunther";
        String category = testCategorySaved.getName();
        
        Pet guntherSaved = petService.createPet(name, category);
        Assert.assertNotNull(guntherSaved);
        Assert.assertNotNull(guntherSaved.getId());
        Assert.assertEquals(name, guntherSaved.getName());
        
        LOGGER.info("test ok");
    }
    
    /**
     * Tests {@link PetService.findPetById(Long)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testFindPetById() {
        // existing pet
        Pet found = petService.findPetById(testPetsSaved[0].getId());
        
        Assert.assertNotNull(found);
        Assert.assertEquals(testPetsSaved[0].getId(), found.getId());
        
        // non existing pet
        Pet nope = petService.findPetById(ID_NOT_IN_DB);
        Assert.assertNull(nope);
        
        LOGGER.info("test ok");
    }
    
    /**
     * Tests {@link PetService.deletePetById(Long)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testDeletePetById() {
        // non existing pet
        boolean deleted = petService.deletePetById(ID_NOT_IN_DB);
        Assert.assertFalse(deleted);
        
        // existing pet
        deleted = petService.deletePetById(testPetsSaved[0].getId());
        Assert.assertTrue(deleted);
        
        // confirm it was deleted using the petRepo
        Pet nope = petRepo.findOne(testPetsSaved[0].getId());
        Assert.assertNull(nope);
        
        LOGGER.info("test ok");
    }
    
    /**
     * Tests {@link PetService.findPetByStatus(Collection<Status>)}
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW) // rolls back the transaction in each test
    public void testFindPetByStatus() {
        // find the availables
        Iterable<Pet> av = petService.findPetByStatus(Arrays.asList(new Status[]{Status.AVAILABLE}));
        Assert.assertNotNull(av);
        Assert.assertTrue(Iterables.size(av) == 1);
        
        // find the pending
        Iterable<Pet> pe = petService.findPetByStatus(Arrays.asList(new Status[]{Status.PENDING}));
        Assert.assertNotNull(pe);
        Assert.assertTrue(Iterables.size(pe) == 1);
        
        // find the sold
        Iterable<Pet> so = petService.findPetByStatus(Arrays.asList(new Status[]{Status.SOLD}));
        Assert.assertNotNull(so);
        Assert.assertTrue(Iterables.size(so) == 1);
        
        // find the available and pending
        Iterable<Pet> avpe = petService.findPetByStatus(Arrays.asList(new Status[]{Status.AVAILABLE, Status.PENDING}));
        Assert.assertNotNull(avpe);
        Assert.assertTrue(Iterables.size(avpe) == 2);
       
        // find the available and sold
        Iterable<Pet> avso = petService.findPetByStatus(Arrays.asList(new Status[]{Status.AVAILABLE, Status.SOLD}));
        Assert.assertNotNull(avso);
        Assert.assertTrue(Iterables.size(avso) == 2);
        
        // find all statuses
        Iterable<Pet> all = petService.findPetByStatus(Arrays.asList(Status.values()));
        Assert.assertNotNull(all);
        Assert.assertTrue(Iterables.size(all) == 3);
        
    }
    
    //TODO: list pets etc...
}

































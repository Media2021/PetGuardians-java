package pets.example.guardians.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pets.example.guardians.model.PetType;
class PetEntityTest {

    @Test
    void testSettersAndGetters() {
        PetEntity petEntity = new PetEntity();


        petEntity.setId(1L);
        petEntity.setName("Test Pet");
        petEntity.setAge(3);
        petEntity.setDescription("Test pet description");
        petEntity.setType(PetType.DOG);
        petEntity.setStatus("Available");
        petEntity.setGender("Male");
        UserEntity adopter = new UserEntity();
        petEntity.setAdopter(adopter);


        Assertions.assertEquals(1L, petEntity.getId());
        Assertions.assertEquals("Test Pet", petEntity.getName());
        Assertions.assertEquals(3, petEntity.getAge());
        Assertions.assertEquals("Test pet description", petEntity.getDescription());
        Assertions.assertEquals(PetType.DOG, petEntity.getType());
        Assertions.assertEquals("Available", petEntity.getStatus());
        Assertions.assertEquals("Male", petEntity.getGender());
        Assertions.assertEquals(adopter, petEntity.getAdopter());
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        PetEntity petEntity1 = new PetEntity();
        PetEntity petEntity2 = new PetEntity();


        Assertions.assertEquals(petEntity1, petEntity2);
        Assertions.assertEquals(petEntity1.hashCode(), petEntity2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithDifferentIds() {
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setId(1L);
        PetEntity petEntity2 = new PetEntity();
        petEntity2.setId(2L);


        Assertions.assertNotEquals(petEntity1, petEntity2);
        Assertions.assertNotEquals(petEntity1.hashCode(), petEntity2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithDifferentTypes() {
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setType(PetType.DOG);
        PetEntity petEntity2 = new PetEntity();
        petEntity2.setType(PetType.CAT);


        Assertions.assertNotEquals(petEntity1, petEntity2);
        Assertions.assertNotEquals(petEntity1.hashCode(), petEntity2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithDifferentObjects() {
        PetEntity petEntity = new PetEntity();
        Object otherObject = new Object();


        Assertions.assertNotEquals(petEntity, otherObject);
        Assertions.assertNotEquals(petEntity.hashCode(), otherObject.hashCode());
    }
    @Test
    void testNullDescriptionException() {
        PetEntity petEntity = new PetEntity();

        Assertions.assertThrows(NullPointerException.class, () -> {
            petEntity.setDescription(null);
        });
    }

    @Test
  void testGetterSetter() {
        long id = 1L;
        String name = "Test Pet";
        int age = 3;
        String description = "Test pet description";
        PetType type = PetType.DOG;
        String status = "Available";
        String gender = "Male";
        UserEntity adopter = new UserEntity();

        PetEntity petEntity = new PetEntity();
        petEntity.setId(id);
        petEntity.setName(name);
        petEntity.setAge(age);
        petEntity.setDescription(description);
        petEntity.setType(type);
        petEntity.setStatus(status);
        petEntity.setGender(gender);
        petEntity.setAdopter(adopter);

        Assertions.assertEquals(id, petEntity.getId());
        Assertions.assertEquals(name, petEntity.getName());
        Assertions.assertEquals(age, petEntity.getAge());
        Assertions.assertEquals(description, petEntity.getDescription());
        Assertions.assertEquals(type, petEntity.getType());
        Assertions.assertEquals(status, petEntity.getStatus());
        Assertions.assertEquals(gender, petEntity.getGender());
        Assertions.assertEquals(adopter, petEntity.getAdopter());
    }
    @Test
    void testDataAnnotation() {
        long id = 1L;
        String name = "Fluffy";
        int age = 2;
        String description = "A cute and playful pet";
        PetType type = PetType.CAT;
        String status = "AVAILABLE";
        String gender = "Female";
        UserEntity adopter = new UserEntity();

        PetEntity pet = new PetEntity();
        pet.setId(id);
        pet.setName(name);
        pet.setAge(age);
        pet.setDescription(description);
        pet.setType(type);
        pet.setStatus(status);
        pet.setGender(gender);
        pet.setAdopter(adopter);

        Assertions.assertEquals(id, pet.getId());
        Assertions.assertEquals(name, pet.getName());
        Assertions.assertEquals(age, pet.getAge());
        Assertions.assertEquals(description, pet.getDescription());
        Assertions.assertEquals(type, pet.getType());
        Assertions.assertEquals(status, pet.getStatus());
        Assertions.assertEquals(gender, pet.getGender());
        Assertions.assertEquals(adopter, pet.getAdopter());
    }
    @Test
   void testEqualsAndHashCode() {
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setId(1L);
        petEntity1.setName("Test Pet");
        petEntity1.setAge(3);
        petEntity1.setDescription("Test pet description");
        petEntity1.setType(PetType.DOG);
        petEntity1.setStatus("Available");
        petEntity1.setGender("Male");
        petEntity1.setAdopter(new UserEntity());

        PetEntity petEntity2 = new PetEntity();
        petEntity2.setId(1L);
        petEntity2.setName("Test Pet");
        petEntity2.setAge(3);
        petEntity2.setDescription("Test pet description");
        petEntity2.setType(PetType.DOG);
        petEntity2.setStatus("Available");
        petEntity2.setGender("Male");
        petEntity2.setAdopter(new UserEntity());

        Assertions.assertEquals(petEntity1, petEntity2);
        Assertions.assertEquals(petEntity1.hashCode(), petEntity2.hashCode());
    }

    @Test
     void testToString() {
        long id = 1L;
        String name = "Test Pet";
        int age = 3;
        String description = "Test pet description";
        PetType type = PetType.DOG;
        String status = "Available";
        String gender = "Male";
        UserEntity adopter = new UserEntity();

        PetEntity petEntity = new PetEntity();
        petEntity.setId(id);
        petEntity.setName(name);
        petEntity.setAge(age);
        petEntity.setDescription(description);
        petEntity.setType(type);
        petEntity.setStatus(status);
        petEntity.setGender(gender);
        petEntity.setAdopter(adopter);

        String expectedToString = "PetEntity(id=1, name=Test Pet, age=3, description=Test pet description, " +
                "type=DOG, status=Available, gender=Male, adopter=" + adopter + ")";

        Assertions.assertEquals(expectedToString, petEntity.toString());
    }
    @Test
    void testEmptyPetEntity() {
        PetEntity petEntity = new PetEntity();

        Assertions.assertEquals(0L, petEntity.getId());
        Assertions.assertNull(petEntity.getName());
        Assertions.assertEquals(0, petEntity.getAge());
        Assertions.assertNull(petEntity.getDescription());
        Assertions.assertNull(petEntity.getType());
        Assertions.assertNull(petEntity.getStatus());
        Assertions.assertNull(petEntity.getGender());
        Assertions.assertNull(petEntity.getAdopter());
    }

    @Test
    void testPetEntityWithNullFields() {
        PetEntity petEntity = new PetEntity();
        petEntity.setId(1L);
        petEntity.setName(null);
        petEntity.setAge(0);

        petEntity.setType(null);
        petEntity.setStatus(null);
        petEntity.setGender(null);
        petEntity.setAdopter(null);

        Assertions.assertEquals(1L, petEntity.getId());
        Assertions.assertNull(petEntity.getName());
        Assertions.assertEquals(0, petEntity.getAge());

        Assertions.assertNull(petEntity.getType());
        Assertions.assertNull(petEntity.getStatus());
        Assertions.assertNull(petEntity.getGender());
        Assertions.assertNull(petEntity.getAdopter());
    }

    @Test
    void testPetEntityEqualityWithDifferentInstances() {
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setId(1L);
        petEntity1.setName("Test Pet");
        petEntity1.setAge(3);
        petEntity1.setDescription("Test pet description");
        petEntity1.setType(PetType.DOG);
        petEntity1.setStatus("Available");
        petEntity1.setGender("Male");
        petEntity1.setAdopter(new UserEntity());

        PetEntity petEntity2 = new PetEntity();
        petEntity2.setId(1L);
        petEntity2.setName("Test Pet");
        petEntity2.setAge(3);
        petEntity2.setDescription("Test pet description");
        petEntity2.setType(PetType.DOG);
        petEntity2.setStatus("Available");
        petEntity2.setGender("Male");
        petEntity2.setAdopter(new UserEntity());

        Assertions.assertEquals(petEntity1, petEntity2);
        Assertions.assertEquals(petEntity1.hashCode(), petEntity2.hashCode());
        Assertions.assertNotSame(petEntity1, petEntity2);
    }
    @Test
    void testDifferentPetEntityInstances() {
        PetEntity petEntity1 = new PetEntity();
        petEntity1.setId(1L);
        petEntity1.setName("Test Pet");
        petEntity1.setAge(3);
        petEntity1.setDescription("Test pet description");
        petEntity1.setType(PetType.DOG);
        petEntity1.setStatus("Available");
        petEntity1.setGender("Male");
        petEntity1.setAdopter(new UserEntity());

        PetEntity petEntity2 = new PetEntity();
        petEntity2.setId(2L);
        petEntity2.setName("Different Pet");
        petEntity2.setAge(5);
        petEntity2.setDescription("Different pet description");
        petEntity2.setType(PetType.CAT);
        petEntity2.setStatus("Adopted");
        petEntity2.setGender("Female");
        petEntity2.setAdopter(null);

        Assertions.assertNotEquals(petEntity1, petEntity2);
        Assertions.assertNotEquals(petEntity1.hashCode(), petEntity2.hashCode());
    }
}
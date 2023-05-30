package pets.example.guardians.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pets.example.guardians.model.PetType;



class PetEntityTest {

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
}
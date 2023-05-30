package pets.example.guardians.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PetTest {


    @Test
    void testGetterSetter() {
        long id = 1L;
        String name = "Test Pet";
        int age = 3;
        String description = "Test pet description";
        PetType type = PetType.DOG;
        String status = "Available";
        String gender = "Male";
        User adopter = new User();

        Pet pet = new Pet();
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
    void testBuilder() {
        long id = 1L;
        String name = "Test Pet";
        int age = 3;
        String description = "Test pet description";
        PetType type = PetType.DOG;
        String status = "Available";
        String gender = "Male";
        User adopter = new User();

        Pet pet = Pet.builder()
                .id(id)
                .name(name)
                .age(age)
                .description(description)
                .type(type)
                .status(status)
                .gender(gender)
                .adopter(adopter)
                .build();

        Assertions.assertEquals(id, pet.getId());
        Assertions.assertEquals(name, pet.getName());
        Assertions.assertEquals(age, pet.getAge());
        Assertions.assertEquals(description, pet.getDescription());
        Assertions.assertEquals(type, pet.getType());
        Assertions.assertEquals(status, pet.getStatus());
        Assertions.assertEquals(gender, pet.getGender());
        Assertions.assertEquals(adopter, pet.getAdopter());
    }
}
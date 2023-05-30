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

    @Test
    void testDataAnnotation() {
        long id = 1L;
        String name = "Buddy";
        int age = 2;
        String description = "Friendly and playful";
        PetType type = PetType.DOG;
        String status = "AVAILABLE";
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
    void testConstructor() {
        long id = 1L;
        String name = "Test Pet";
        int age = 3;
        String description = "Test pet description";
        PetType type = PetType.DOG;
        String status = "Available";
        String gender = "Male";
        User adopter = new User();

        Pet pet = new Pet(id, name, age, description, type, status, gender, adopter);

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
        Pet pet1 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet2 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet3 = new Pet(2L, "Max", 3, "Loyal and energetic", PetType.DOG, "AVAILABLE", "Male", null);

        Assertions.assertEquals(pet1, pet2);
        Assertions.assertNotEquals(pet1, pet3);
        Assertions.assertEquals(pet1.hashCode(), pet2.hashCode());
        Assertions.assertNotEquals(pet1.hashCode(), pet3.hashCode());
    }
    @Test
    void testToString() {
        Pet pet = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);

        String expectedToString = "Pet(id=1, name=Buddy, age=2, description=Friendly and playful, " +
                "type=DOG, status=AVAILABLE, gender=Male, adopter=null)";

        Assertions.assertEquals(expectedToString, pet.toString());
    }
}
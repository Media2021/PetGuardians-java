package pets.example.guardians.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PetTest {

    @Test
    void testAdopterNullWhenStatusAvailable() {

        Pet pet = new Pet();
        pet.setStatus("Available");
        pet.setAdopter(null);
        assertNull(pet.getAdopter());
    }

    @Test
    void testBuilderWithDefaultValues() {
        Pet pet = Pet.builder()
                .name("Buddy")
                .build();

        assertEquals(0L, pet.getId());
        assertEquals("Buddy", pet.getName());
        assertEquals(0, pet.getAge());
        assertNull(pet.getDescription());
        assertNull(pet.getType());
        assertNull(pet.getStatus());
        assertNull(pet.getGender());
        assertNull(pet.getAdopter());
    }

    @Test
    void testBuilderWithAllValues() {
        Pet pet = Pet.builder()
                .id(1L)
                .name("Buddy")
                .age(2)
                .description("Friendly and playful")
                .type(PetType.DOG)
                .status("AVAILABLE")
                .gender("Male")
                .adopter(new User())
                .build();

        assertEquals(1L, pet.getId());
        assertEquals("Buddy", pet.getName());
        assertEquals(2, pet.getAge());
        assertEquals("Friendly and playful", pet.getDescription());
        assertEquals(PetType.DOG, pet.getType());
        assertEquals("AVAILABLE", pet.getStatus());
        assertEquals("Male", pet.getGender());
        assertNotNull(pet.getAdopter());
    }
    @Test
    void testEmptyPet() {
        Pet pet = new Pet();

        assertEquals(0L, pet.getId());
        assertNull(pet.getName());
        assertEquals(0, pet.getAge());
        assertNull(pet.getDescription());
        assertNull(pet.getType());
        assertNull(pet.getStatus());
        assertNull(pet.getGender());
        assertNull(pet.getAdopter());
    }

    @Test
    void testPetWithNullFields() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName(null);
        pet.setAge(0);
        pet.setType(null);
        pet.setStatus(null);
        pet.setGender(null);
        pet.setAdopter(null);

        assertEquals(1L, pet.getId());
        assertNull(pet.getName());
        assertEquals(0, pet.getAge());
        assertNull(pet.getDescription());
        assertNull(pet.getType());
        assertNull(pet.getStatus());
        assertNull(pet.getGender());
        assertNull(pet.getAdopter());
    }

    @Test
    void testPetEqualityWithDifferentInstances() {
        Pet pet1 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet2 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet3 = new Pet(2L, "Max", 3, "Loyal and energetic", PetType.DOG, "AVAILABLE", "Male", null);

        assertEquals(pet1, pet2);
        assertEquals(pet1.hashCode(), pet2.hashCode());
        assertNotSame(pet1, pet2);
        assertNotEquals(pet1, pet3);
        assertNotEquals(pet1.hashCode(), pet3.hashCode());
    }

    @Test
    void testDifferentPets() {
        Pet pet1 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet2 = new Pet(2L, "Max", 3, "Loyal and energetic", PetType.DOG, "AVAILABLE", "Male", null);

        assertNotEquals(pet1, pet2);
        assertNotEquals(pet1.hashCode(), pet2.hashCode());
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

        assertEquals(id, pet.getId());
        assertEquals(name, pet.getName());
        assertEquals(age, pet.getAge());
        assertEquals(description, pet.getDescription());
        assertEquals(type, pet.getType());
        assertEquals(status, pet.getStatus());
        assertEquals(gender, pet.getGender());
        assertEquals(adopter, pet.getAdopter());
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

        assertEquals(id, pet.getId());
        assertEquals(name, pet.getName());
        assertEquals(age, pet.getAge());
        assertEquals(description, pet.getDescription());
        assertEquals(type, pet.getType());
        assertEquals(status, pet.getStatus());
        assertEquals(gender, pet.getGender());
        assertEquals(adopter, pet.getAdopter());
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

        assertEquals(id, pet.getId());
        assertEquals(name, pet.getName());
        assertEquals(age, pet.getAge());
        assertEquals(description, pet.getDescription());
        assertEquals(type, pet.getType());
        assertEquals(status, pet.getStatus());
        assertEquals(gender, pet.getGender());
        assertEquals(adopter, pet.getAdopter());
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

        assertEquals(id, pet.getId());
        assertEquals(name, pet.getName());
        assertEquals(age, pet.getAge());
        assertEquals(description, pet.getDescription());
        assertEquals(type, pet.getType());
        assertEquals(status, pet.getStatus());
        assertEquals(gender, pet.getGender());
        assertEquals(adopter, pet.getAdopter());
    }

    @Test
    void testEqualsAndHashCode() {
        Pet pet1 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet2 = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);
        Pet pet3 = new Pet(2L, "Max", 3, "Loyal and energetic", PetType.DOG, "AVAILABLE", "Male", null);

        assertEquals(pet1, pet2);
        Assertions.assertNotEquals(pet1, pet3);
        assertEquals(pet1.hashCode(), pet2.hashCode());
        Assertions.assertNotEquals(pet1.hashCode(), pet3.hashCode());
    }
    @Test
    void testToString() {
        Pet pet = new Pet(1L, "Buddy", 2, "Friendly and playful", PetType.DOG, "AVAILABLE", "Male", null);

        String expectedToString = "Pet(id=1, name=Buddy, age=2, description=Friendly and playful, " +
                "type=DOG, status=AVAILABLE, gender=Male, adopter=null)";

        assertEquals(expectedToString, pet.toString());
    }
}
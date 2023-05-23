package pets.example.guardians.services.Mapper;

import org.junit.jupiter.api.Test;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testToModel() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");



        PetEntity petEntity = new PetEntity();
        petEntity.setId(1L);
        petEntity.setName("Fido");



        Set<PetEntity> adoptedPets = new HashSet<>();
        adoptedPets.add(petEntity);
        userEntity.setAdoptedPets(adoptedPets);


        User user = UserMapper.toModel(userEntity);


        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());

        assertNotNull(user.getAdoptedPets());
        assertEquals(1, user.getAdoptedPets().size());
        Pet adoptedPet = user.getAdoptedPets().iterator().next();
        assertEquals(1L, adoptedPet.getId());
        assertEquals("Fido", adoptedPet.getName());

    }

    @Test
    void testToEntity() {

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");



        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");

        Set<Pet> adoptedPets = new HashSet<>();
        adoptedPets.add(pet);
        user.setAdoptedPets(adoptedPets);

        UserEntity userEntity = UserMapper.toEntity(user);


        assertNotNull(userEntity);
        assertEquals(1L, userEntity.getId());
        assertEquals("John", userEntity.getFirstName());
        assertEquals("Doe", userEntity.getLastName());

        assertNotNull(userEntity.getAdoptedPets());
        assertEquals(1, userEntity.getAdoptedPets().size());
        PetEntity adoptedPetEntity = userEntity.getAdoptedPets().iterator().next();
        assertEquals(1L, adoptedPetEntity.getId());
        assertEquals("Fido", adoptedPetEntity.getName());

    }
}








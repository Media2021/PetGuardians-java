package pets.example.guardians.services.Mapper;

import org.junit.jupiter.api.Test;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.repository.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PetMapperTest {

    @Test
    public void testToModel() {

        PetEntity petEntity = mock(PetEntity.class);
        when(petEntity.getId()).thenReturn(1L);
        when(petEntity.getName()).thenReturn("Fluffy");

        UserEntity adopterEntity = mock(UserEntity.class);
        when(adopterEntity.getId()).thenReturn(1L);
        when(petEntity.getAdopter()).thenReturn(adopterEntity);


        Pet pet = PetMapper.toModel(petEntity);


        assertEquals(1L, pet.getId());
        assertEquals("Fluffy", pet.getName());

        assertEquals(1L, pet.getAdopter().getId());
    }

    @Test
    public void testToEntity() {

        Pet pet = mock(Pet.class);
        when(pet.getId()).thenReturn(1L);
        when(pet.getName()).thenReturn("Fluffy");

        User adopter = mock(User.class);
        when(adopter.getId()).thenReturn(1L);
        when(pet.getAdopter()).thenReturn(adopter);


        PetEntity petEntity = PetMapper.toEntity(pet);


        assertEquals(1L, petEntity.getId());
        assertEquals("Fluffy", petEntity.getName());

        assertEquals(1L, petEntity.getAdopter().getId());
    }
}
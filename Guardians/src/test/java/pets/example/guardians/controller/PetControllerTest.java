package pets.example.guardians.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import pets.example.guardians.model.Pet;
import pets.example.guardians.model.PetType;
import pets.example.guardians.repository.AvailableListPetType;
import pets.example.guardians.repository.PetTypeCount;
import pets.example.guardians.repository.entity.PetEntity;
import pets.example.guardians.services.PetService;

import java.util.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PetService petServiceMock;






    @Test
     void testGetAvailablePets() throws Exception {

        List<PetEntity> availablePets = new ArrayList<>();
        availablePets.add(new PetEntity());
        Mockito.when(petServiceMock.getListAvailablePets()).thenReturn(availablePets);


        mockMvc.perform(get("/pets/available"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    void testCountAdoptedByType() throws Exception {

        List<Map<String, Object>> expectedResult = new ArrayList<>();

        List<PetTypeCount> petTypeCounts = new ArrayList<>();
        Mockito.when(petServiceMock.countByStatusAdoptedAndType()).thenReturn(petTypeCounts);


        mockMvc.perform(get("/pets/count-adopted-by-type"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
     void testCountAvailableByType() throws Exception {

        List<Map<String, Object>> expectedResult = new ArrayList<>();


        List<AvailableListPetType> petTypeCounts = new ArrayList<>();


        Mockito.when(petServiceMock.countByStatusAvailableAndType()).thenReturn(petTypeCounts);


        mockMvc.perform(get("/pets/count-available-by-type"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
     void testCountAllPets() throws Exception {

        long count = 5;
        Mockito.when(petServiceMock.countAllPets()).thenReturn(count);


        mockMvc.perform(get("/pets/all/count"))
                .andExpect(status().isOk())
                .andExpect(content().json("5"));
    }

    @Test
     void testCountAvailablePetsByStatus() throws Exception {

        long count = 3;
        Mockito.when(petServiceMock.countAvailablePetsByStatus()).thenReturn(count);


        mockMvc.perform(get("/pets/available/count"))
                .andExpect(status().isOk())
                .andExpect(content().json("3"));
    }


    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void testCreatePet() throws Exception {
        Pet pet = new Pet();
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        given(petServiceMock.createPet(any(Pet.class))).willReturn(pet);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fluffy"))
                .andExpect(jsonPath("$.age").value(2))
                .andExpect(jsonPath("$.description").value("A cute and cuddly Pet"))
                .andExpect(jsonPath("$.type").value("DOG"))
                .andExpect(jsonPath("$.status").value("Available"))
                .andExpect(jsonPath("$.gender").value("Female"));

        verify(petServiceMock, times(1)).createPet(any(Pet.class));
    }

    @Test
    void testGetAllPets() throws Exception {

        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Fluffy");
        pet1.setAge(2);
        pet1.setDescription("A cute and cuddly Pet");
        pet1.setType(PetType.DOG);
        pet1.setStatus("Available");
        pet1.setGender("Female");

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Buddy");
        pet2.setAge(3);
        pet2.setDescription("A friendly and playful Pet");
        pet2.setType(PetType.CAT);
        pet2.setStatus("Available");
        pet2.setGender("Male");

        List<Pet> pets = new ArrayList<>();
        pets.add(pet1);
        pets.add(pet2);

        given(petServiceMock.getAllPets()).willReturn(pets);


        mockMvc.perform(get("/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Fluffy"))
                .andExpect(jsonPath("$[0].age").value(2))
                .andExpect(jsonPath("$[0].description").value("A cute and cuddly Pet"))
                .andExpect(jsonPath("$[0].type").value("DOG"))
                .andExpect(jsonPath("$[0].status").value("Available"))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Buddy"))
                .andExpect(jsonPath("$[1].age").value(3))
                .andExpect(jsonPath("$[1].description").value("A friendly and playful Pet"))
                .andExpect(jsonPath("$[1].type").value("CAT"))
                .andExpect(jsonPath("$[1].status").value("Available"))
                .andExpect(jsonPath("$[1].gender").value("Male"));

        verify(petServiceMock, times(1)).getAllPets();
    }

    @Test
    void testGetAllPetsWithNoPetsFound() throws Exception {
        given(petServiceMock.getAllPets()).willThrow(new NoSuchElementException("No pets found"));

        mockMvc.perform(get("/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

                .andReturn();

        verify(petServiceMock, times(1)).getAllPets();
    }



    @Test
    void testDeletePet() throws Exception {
        Long petId = 123L;

        mockMvc.perform(delete("/pets/" + petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(petServiceMock, times(1)).deletePet(petId);
    }



    @Test
    void testGetPetById() throws Exception {
        Long petId = 123L;
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Fluffy");
        pet.setAge(2);
        pet.setDescription("A cute and cuddly Pet");
        pet.setType(PetType.DOG);
        pet.setStatus("Available");
        pet.setGender("Female");

        given(petServiceMock.getPetById(petId)).willReturn(Optional.of(pet));

        mockMvc.perform(get("/pets/" + petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petId))
                .andExpect(jsonPath("$.name").value("Fluffy"))
                .andExpect(jsonPath("$.age").value(2))
                .andExpect(jsonPath("$.description").value("A cute and cuddly Pet"))
                .andExpect(jsonPath("$.type").value("DOG"))
                .andExpect(jsonPath("$.status").value("Available"))
                .andExpect(jsonPath("$.gender").value("Female"));

        verify(petServiceMock, times(1)).getPetById(petId);
    }

    @Test
    void testGetPetById_NotFound() throws Exception {
        Long petId = 123L;

        given(petServiceMock.getPetById(petId)).willReturn(Optional.empty());

        mockMvc.perform(get("/pets/" + petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(petServiceMock, times(1)).getPetById(petId);
    }



    @Test
    @WithMockUser(username = "admin1", roles = {"ADMIN"})
    void testUpdatePetById_shouldReturn200() throws Exception {
        Long petId = 123L;
        Pet petToUpdate = new Pet();
        petToUpdate.setName("Fluffy");
        petToUpdate.setAge(3);
        petToUpdate.setDescription("A cute and cuddly Pet");
        petToUpdate.setType(PetType.DOG);
        petToUpdate.setStatus("Available");
        petToUpdate.setGender("Female");

        Pet updatedPet = new Pet();
        updatedPet.setId(petId);
        updatedPet.setName("Fluffy");
        updatedPet.setAge(4);
        updatedPet.setDescription("A cute and cuddly Pet");
        updatedPet.setType(PetType.DOG);
        updatedPet.setStatus("Pending");
        updatedPet.setGender("Female");

        given(petServiceMock.updatePetById(eq(petId), any(Pet.class))).willReturn(updatedPet);


        mockMvc.perform(put("/pets/" + petId)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(petToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petId))
                .andExpect(jsonPath("$.name").value("Fluffy"))
                .andExpect(jsonPath("$.age").value(4))
                .andExpect(jsonPath("$.description").value("A cute and cuddly Pet"))
                .andExpect(jsonPath("$.type").value("DOG"))
                .andExpect(jsonPath("$.status").value("Pending"))
                .andExpect(jsonPath("$.gender").value("Female"));

        verify(petServiceMock, times(1)).updatePetById(eq(petId), any(Pet.class));
    }

    @Test
    void testGetPetById_PetNotFound() throws Exception {
        Long petId = 123L;

        given(petServiceMock.getPetById(petId)).willReturn(Optional.empty());

        mockMvc.perform(get("/pets/" + petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(petServiceMock, times(1)).getPetById(petId);
    }


}
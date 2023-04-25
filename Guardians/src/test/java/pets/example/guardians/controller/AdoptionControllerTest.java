//package pets.example.guardians.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import pets.example.guardians.model.Pet;
//import pets.example.guardians.model.User;
//import pets.example.guardians.services.AdoptionService;
//import pets.example.guardians.services.PetService;
//import pets.example.guardians.services.UserService;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AdoptionController.class)
//class AdoptionControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//    @MockBean
//    private PetService petService;
//    @MockBean
//    private AdoptionService adoptionService;
//    @Test
//    void adoptPet()  throws Exception {
//        Long userId = 1L;
//        Long petId = 2L;
//
//        User user = new User();
//        user.setId(userId);
//
//        Pet pet = new Pet();
//        pet.setId(petId);
//
//        given(userService.getUserById(userId)).willReturn(Optional.of(user));
//        given(petService.getPetById(petId)).willReturn(Optional.of(pet));
//
//        mockMvc.perform(post("/users/{userId}/pets/{petId}/adopt", userId, petId))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        verify(adoptionService, times(1)).adoptPet(user, pet);
//    }
//    @Test
//    void deletePet() {
//    }
//}
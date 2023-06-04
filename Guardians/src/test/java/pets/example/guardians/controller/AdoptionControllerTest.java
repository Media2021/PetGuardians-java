package pets.example.guardians.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pets.example.guardians.model.AdoptionRequest;
import pets.example.guardians.model.Pet;
import pets.example.guardians.model.User;
import pets.example.guardians.repository.entity.AdoptionRequestEntity;
import pets.example.guardians.services.AdoptionService;
import pets.example.guardians.services.PetService;
import pets.example.guardians.services.UserService;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdoptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdoptionService adoptionService;
    @MockBean
    private UserService userService;
    @MockBean
    private PetService petService;

    @Test
    @WithMockUser(roles = "USER")
    void createAdoptionRequest() throws Exception{
        User mockUser = new User();
        when(userService.getUserById(any())).thenReturn(Optional.of(mockUser));

        Pet mockedPet = new Pet();
        when(petService.getPetById(any())).thenReturn(Optional.of(mockedPet));

        AdoptionRequest request = new AdoptionRequest();
        request.setUser(mockUser);
        request.setPet(mockedPet);
        request.setNotes("Sample notes");
        request.setRequestDate(new Date());
        request.setStatus("PENDING");

        when(adoptionService.createAdoptionRequest(any(AdoptionRequestEntity.class))).thenReturn(request);

        mockMvc.perform(post("/adoption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notes").value(request.getNotes()))
                .andExpect(jsonPath("$.status").value(request.getStatus()));

        verify(adoptionService).createAdoptionRequest(any(AdoptionRequestEntity.class));
    }
    @Test
    @WithMockUser(roles = "USER")
    void createAdoptionRequest_UnhappyFlow() throws Exception {

        when(userService.getUserById(any())).thenReturn(Optional.empty());


        when(petService.getPetById(any())).thenReturn(Optional.empty());

        AdoptionRequest request = new AdoptionRequest();
        request.setUser(new User());
        request.setPet(new Pet());
        request.setNotes("Sample notes");
        request.setRequestDate(new Date());
        request.setStatus("PENDING");

        mockMvc.perform(post("/adoption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(adoptionService, never()).createAdoptionRequest(any(AdoptionRequestEntity.class));
    }



    @Test
    void getAllAdoptionRequests()throws Exception {
        AdoptionRequest adoptionRequest1 = new AdoptionRequest();
        adoptionRequest1.setUser(new User());
        adoptionRequest1.setPet(new Pet());
        adoptionRequest1.setNotes("Sample notes 1");
        adoptionRequest1.setRequestDate(new Date());
        adoptionRequest1.setStatus("PENDING");

        AdoptionRequest adoptionRequest2 = new AdoptionRequest();
        adoptionRequest2.setUser(new User());
        adoptionRequest2.setPet(new Pet());
        adoptionRequest2.setNotes("Sample notes 2");
        adoptionRequest2.setRequestDate(new Date());
        adoptionRequest2.setStatus("APPROVED");

        List<AdoptionRequest> adoptionRequests = Arrays.asList(adoptionRequest1, adoptionRequest2);


        when(adoptionService.getAllAdoptionRequests()).thenReturn(adoptionRequests);


        when(userService.getUserById(any())).thenReturn(Optional.of(new User()));


        when(petService.getPetById(any())).thenReturn(Optional.of(new Pet()));

        mockMvc.perform(get("/adoption"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].notes").value(adoptionRequest1.getNotes()))
                .andExpect(jsonPath("$[0].status").value(adoptionRequest1.getStatus()))
                .andExpect(jsonPath("$[1].notes").value(adoptionRequest2.getNotes()))
                .andExpect(jsonPath("$[1].status").value(adoptionRequest2.getStatus()));

        verify(adoptionService).getAllAdoptionRequests();
        verify(userService, times(2)).getUserById(any());
        verify(petService, times(2)).getPetById(any());
    }
    @Test
    void getAllAdoptionRequests_EmptyList() throws Exception {
        List<AdoptionRequest> adoptionRequests = new ArrayList<>();


        when(adoptionService.getAllAdoptionRequests()).thenReturn(adoptionRequests);

        mockMvc.perform(get("/adoption"))
                .andExpect(status().isNoContent());

        verify(adoptionService).getAllAdoptionRequests();
        verifyNoInteractions(userService);
        verifyNoInteractions(petService);
    }

    @Test
    void getAdoptionRequestById() throws Exception {
        Long requestId = 1L;
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setId(requestId); // Set the id field

        User mockUser = new User();
        adoptionRequest.setUser(mockUser);

        Pet mockedPet = new Pet();
        adoptionRequest.setPet(mockedPet);

        when(adoptionService.getAdoptionRequestById(requestId)).thenReturn(Optional.of(adoptionRequest));
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        when(petService.getPetById(anyLong())).thenReturn(Optional.of(mockedPet));

        mockMvc.perform(get("/adoption/{id}", requestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestId));

        verify(adoptionService).getAdoptionRequestById(requestId);
        verify(userService).getUserById(anyLong());
        verify(petService).getPetById(anyLong());
    }


    @Test
    void getAdoptionRequestById_NonExistingId_ReturnsNotFound() throws Exception {
        Long requestId = 1L;

        when(adoptionService.getAdoptionRequestById(requestId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/adoption/{id}", requestId))
                .andExpect(status().isNotFound());

        verify(adoptionService).getAdoptionRequestById(requestId);
        verifyNoInteractions(userService);
        verifyNoInteractions(petService);
    }
    @Test
    void updateAdoptionRequestById() throws Exception {
        Long requestId = 1L;
        AdoptionRequest updatedRequest = new AdoptionRequest();
        updatedRequest.setId(requestId);

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setId(requestId);

        when(adoptionService.updateAdoptionRequestById(requestId, updatedRequest)).thenReturn(Optional.of(adoptionRequest));

        mockMvc.perform(put("/adoption/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestId));

        verify(adoptionService).updateAdoptionRequestById(requestId, updatedRequest);
    }


    @Test
    void updateAdoptionRequestById_NonExistingId_ReturnsNotFound() throws Exception {
        Long requestId = 1L;
        AdoptionRequest updatedRequest = new AdoptionRequest();

        when(adoptionService.updateAdoptionRequestById(requestId, updatedRequest)).thenReturn(Optional.empty());

        mockMvc.perform(put("/adoption/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedRequest)))
                .andExpect(status().isNotFound());

        verify(adoptionService).updateAdoptionRequestById(requestId, updatedRequest);
    }
    @Test
    void acceptAdoptionRequest() throws Exception {
        Long requestId = 1L;
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setId(requestId);

        when(adoptionService.acceptAdoptionRequest(requestId)).thenReturn(adoptionRequest);

        mockMvc.perform(put("/adoption/{id}/accept", requestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestId));

        verify(adoptionService).acceptAdoptionRequest(requestId);
    }
    @Test
    void acceptAdoptionRequest_Exception() throws Exception {
        Long requestId = 1L;

        when(adoptionService.acceptAdoptionRequest(requestId)).thenThrow(new NoSuchElementException());

        mockMvc.perform(put("/adoption/{id}/accept", requestId))
                .andExpect(status().isNotFound());

        verify(adoptionService).acceptAdoptionRequest(requestId);
    }

    @Test
    void declineAdoptionRequest() throws Exception {
        Long requestId = 1L;
        AdoptionRequest declinedRequest = new AdoptionRequest();
        declinedRequest.setId(requestId);

        when(adoptionService.declineAdoptionRequest(requestId)).thenReturn(declinedRequest);

        mockMvc.perform(put("/adoption/{id}/decline", requestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestId));

        verify(adoptionService).declineAdoptionRequest(requestId);
    }
    @Test
    void declineAdoptionRequest_Exception() throws Exception {
        Long requestId = 1L;

        when(adoptionService.declineAdoptionRequest(requestId)).thenThrow(new RuntimeException("Failed to decline adoption request"));

        mockMvc.perform(put("/adoption/{id}/decline", requestId))
                .andExpect(status().isInternalServerError());

        verify(adoptionService).declineAdoptionRequest(requestId);
    }


}
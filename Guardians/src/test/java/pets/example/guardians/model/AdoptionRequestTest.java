package pets.example.guardians.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;


class AdoptionRequestTest {

    @Test
    void testGetterAndSetter() {
        long id = 1L;
        User user = new User();
        Pet pet = new Pet();
        String status = "pending";
        String notes = "Please consider my request.";
        Date requestDate = new Date();

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setId(id);
        adoptionRequest.setUser(user);
        adoptionRequest.setPet(pet);
        adoptionRequest.setStatus(status);
        adoptionRequest.setNotes(notes);
        adoptionRequest.setRequestDate(requestDate);

        Assertions.assertEquals(id, adoptionRequest.getId());
        Assertions.assertEquals(user, adoptionRequest.getUser());
        Assertions.assertEquals(pet, adoptionRequest.getPet());
        Assertions.assertEquals(status, adoptionRequest.getStatus());
        Assertions.assertEquals(notes, adoptionRequest.getNotes());
        Assertions.assertEquals(requestDate, adoptionRequest.getRequestDate());
    }

    @Test
    void testToString() {
        long id = 1L;
        User user = new User();
        Pet pet = new Pet();
        String status = "pending";
        String notes = "Please consider my request.";
        Date requestDate = new Date();

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setId(id);
        adoptionRequest.setUser(user);
        adoptionRequest.setPet(pet);
        adoptionRequest.setStatus(status);
        adoptionRequest.setNotes(notes);
        adoptionRequest.setRequestDate(requestDate);

        String expectedToString = "AdoptionRequest(id=1, user=" + user + ", pet=" + pet + ", status=pending, notes=Please consider my request., requestDate=" + requestDate + ")";

        Assertions.assertEquals(expectedToString, adoptionRequest.toString());
    }
}
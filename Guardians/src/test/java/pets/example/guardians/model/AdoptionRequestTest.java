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

        adoptionRequest.setId(2L);
        adoptionRequest.setUser(null);
        adoptionRequest.setPet(null);
        adoptionRequest.setStatus(null);
        adoptionRequest.setNotes(null);
        adoptionRequest.setRequestDate(null);

        Assertions.assertEquals(2L, adoptionRequest.getId());
        Assertions.assertNull(adoptionRequest.getUser());
        Assertions.assertNull(adoptionRequest.getPet());
        Assertions.assertNull(adoptionRequest.getStatus());
        Assertions.assertNull(adoptionRequest.getNotes());
        Assertions.assertNull(adoptionRequest.getRequestDate());

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
        AdoptionRequest adoptionRequest2 = new AdoptionRequest();
        adoptionRequest2.setId(2L);
        adoptionRequest2.setUser(null);
        adoptionRequest2.setPet(null);
        adoptionRequest2.setStatus(null);
        adoptionRequest2.setNotes(null);
        adoptionRequest2.setRequestDate(null);

        String expectedToString2 = "AdoptionRequest(id=2, user=null, pet=null, status=null, notes=null, requestDate=null)";

        Assertions.assertEquals(expectedToString2, adoptionRequest2.toString());
    }
    @Test
    void testDataAnnotation() {
        long id = 1L;
        User user = new User();
        Pet pet = new Pet();
        String status = "PENDING";
        String notes = "Sample notes";
        Date requestDate = new Date();

        AdoptionRequest adoptionRequest = AdoptionRequest.builder()
                .id(id)
                .user(user)
                .pet(pet)
                .status(status)
                .notes(notes)
                .requestDate(requestDate)
                .build();

        Assertions.assertEquals(id, adoptionRequest.getId());
        Assertions.assertEquals(user, adoptionRequest.getUser());
        Assertions.assertEquals(pet, adoptionRequest.getPet());
        Assertions.assertEquals(status, adoptionRequest.getStatus());
        Assertions.assertEquals(notes, adoptionRequest.getNotes());
        Assertions.assertEquals(requestDate, adoptionRequest.getRequestDate());

        adoptionRequest = AdoptionRequest.builder().build();

        Assertions.assertEquals(0L, adoptionRequest.getId());
        Assertions.assertNull(adoptionRequest.getUser());
        Assertions.assertNull(adoptionRequest.getPet());
        Assertions.assertNull(adoptionRequest.getStatus());
        Assertions.assertNull(adoptionRequest.getNotes());
        Assertions.assertNull(adoptionRequest.getRequestDate());
    }

    @Test
    void testAllArgsConstructor() {
        long id = 1L;
        User user = new User();
        Pet pet = new Pet();
        String status = "PENDING";
        String notes = "Sample notes";
        Date requestDate = new Date();

        AdoptionRequest adoptionRequest = new AdoptionRequest(id, user, pet, status, notes, requestDate);

        Assertions.assertEquals(id, adoptionRequest.getId());
        Assertions.assertEquals(user, adoptionRequest.getUser());
        Assertions.assertEquals(pet, adoptionRequest.getPet());
        Assertions.assertEquals(status, adoptionRequest.getStatus());
        Assertions.assertEquals(notes, adoptionRequest.getNotes());
        Assertions.assertEquals(requestDate, adoptionRequest.getRequestDate());

        adoptionRequest = new AdoptionRequest(2L, null, null, null, null, null);

        Assertions.assertEquals(2L, adoptionRequest.getId());
        Assertions.assertNull(adoptionRequest.getUser());
        Assertions.assertNull(adoptionRequest.getPet());
        Assertions.assertNull(adoptionRequest.getStatus());
        Assertions.assertNull(adoptionRequest.getNotes());
        Assertions.assertNull(adoptionRequest.getRequestDate());
    }

    @Test
    void testBuilderAnnotation() {
        long id = 1L;
        User user = new User();
        Pet pet = new Pet();
        String status = "PENDING";
        String notes = "Sample notes";
        Date requestDate = new Date();

        AdoptionRequest adoptionRequest = AdoptionRequest.builder()
                .id(id)
                .user(user)
                .pet(pet)
                .status(status)
                .notes(notes)
                .requestDate(requestDate)
                .build();

        Assertions.assertEquals(id, adoptionRequest.getId());
        Assertions.assertEquals(user, adoptionRequest.getUser());
        Assertions.assertEquals(pet, adoptionRequest.getPet());
        Assertions.assertEquals(status, adoptionRequest.getStatus());
        Assertions.assertEquals(notes, adoptionRequest.getNotes());
        Assertions.assertEquals(requestDate, adoptionRequest.getRequestDate());

        adoptionRequest = AdoptionRequest.builder().build();

        Assertions.assertEquals(0L, adoptionRequest.getId());
        Assertions.assertNull(adoptionRequest.getUser());
        Assertions.assertNull(adoptionRequest.getPet());
        Assertions.assertNull(adoptionRequest.getStatus());
        Assertions.assertNull(adoptionRequest.getNotes());
        Assertions.assertNull(adoptionRequest.getRequestDate());

    }
}
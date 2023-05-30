package pets.example.guardians.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;



class AdoptionRequestEntityTest {



    @Test
    void testDataAnnotation() {
        long id = 1L;
        UserEntity user = new UserEntity();
        PetEntity pet = new PetEntity();
        String status = "PENDING";
        String notes = "Sample notes";
        Date requestDate = new Date();

        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(id);
        adoptionRequestEntity.setUser(user);
        adoptionRequestEntity.setPet(pet);
        adoptionRequestEntity.setStatus(status);
        adoptionRequestEntity.setNotes(notes);
        adoptionRequestEntity.setRequestDate(requestDate);

        Assertions.assertEquals(id, adoptionRequestEntity.getId());
        Assertions.assertEquals(user, adoptionRequestEntity.getUser());
        Assertions.assertEquals(pet, adoptionRequestEntity.getPet());
        Assertions.assertEquals(status, adoptionRequestEntity.getStatus());
        Assertions.assertEquals(notes, adoptionRequestEntity.getNotes());
        Assertions.assertEquals(requestDate, adoptionRequestEntity.getRequestDate());
    }
    @Test
    void testGetterAndSetter() {
        long id = 1L;
        UserEntity user = new UserEntity();
        PetEntity pet = new PetEntity();
        String status = "PENDING";
        String notes = "Sample notes";
        Date requestDate = new Date();

        AdoptionRequestEntity adoptionRequestEntity = new AdoptionRequestEntity();
        adoptionRequestEntity.setId(id);
        adoptionRequestEntity.setUser(user);
        adoptionRequestEntity.setPet(pet);
        adoptionRequestEntity.setStatus(status);
        adoptionRequestEntity.setNotes(notes);
        adoptionRequestEntity.setRequestDate(requestDate);

        Assertions.assertEquals(id, adoptionRequestEntity.getId());
        Assertions.assertEquals(user, adoptionRequestEntity.getUser());
        Assertions.assertEquals(pet, adoptionRequestEntity.getPet());
        Assertions.assertEquals(status, adoptionRequestEntity.getStatus());
        Assertions.assertEquals(notes, adoptionRequestEntity.getNotes());
        Assertions.assertEquals(requestDate, adoptionRequestEntity.getRequestDate());
    }

    @Test
    void testEqualsAndHashCode() {
        long id = 1L;
        UserEntity user1 = new UserEntity();
        PetEntity pet1 = new PetEntity();
        String status1 = "PENDING";
        String notes1 = "Sample notes";
        Date requestDate1 = new Date();

        AdoptionRequestEntity request1 = new AdoptionRequestEntity();
        request1.setId(id);
        request1.setUser(user1);
        request1.setPet(pet1);
        request1.setStatus(status1);
        request1.setNotes(notes1);
        request1.setRequestDate(requestDate1);

        UserEntity user2 = new UserEntity();
        PetEntity pet2 = new PetEntity();
        String status2 = "PENDING";
        String notes2 = "Sample notes";
        Date requestDate2 = new Date();

        AdoptionRequestEntity request2 = new AdoptionRequestEntity();
        request2.setId(id);
        request2.setUser(user2);
        request2.setPet(pet2);
        request2.setStatus(status2);
        request2.setNotes(notes2);
        request2.setRequestDate(requestDate2);


        Assertions.assertEquals(request1, request1);


        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());


        Assertions.assertNotEquals(request1, null);


        Assertions.assertNotEquals(request1, new Object());


        AdoptionRequestEntity request3 = new AdoptionRequestEntity();
        request3.setId(2L);
        Assertions.assertNotEquals(request1, request3);
        Assertions.assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}
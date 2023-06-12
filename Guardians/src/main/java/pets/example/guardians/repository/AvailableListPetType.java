package pets.example.guardians.repository;

import pets.example.guardians.model.PetType;

public interface AvailableListPetType {
    PetType getType();

    Long getCount();
}

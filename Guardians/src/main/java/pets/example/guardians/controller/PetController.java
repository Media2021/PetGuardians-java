package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.model.Pet;
import pets.example.guardians.services.PetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {
    private final PetService petService;





    @PostMapping
    public Pet createPet(@RequestBody Pet pet)
    {
        return petService.createPet(pet);

    }
    @GetMapping()
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @DeleteMapping("{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId)
    {

        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<Pet>> getPetById(@PathVariable Long id)
    {
        Optional<Pet> pet;

        pet= petService. getPetById(id);
        return ResponseEntity.ok(pet);
    }
    @PutMapping("{id}")
    public ResponseEntity<Pet> updatePetById(@PathVariable Long id , @RequestBody Pet pet)
    {
        pet = petService.updatePetById(id, pet);
        return ResponseEntity.ok(pet);
    }

}

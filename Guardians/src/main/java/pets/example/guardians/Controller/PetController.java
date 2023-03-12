package pets.example.guardians.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pets.example.guardians.Model.Pet;
import pets.example.guardians.Services.PetService;

import java.util.List;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {
    private final PetService petService;





    @PostMapping()
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
    public ResponseEntity<Pet> getPetById(@PathVariable Long id)
    {
        Pet pet = null;

        pet= petService. getPetById(id);
        return ResponseEntity.ok(pet);
    }
    @PutMapping("{id}")
    public ResponseEntity< Pet > updatePetById(@PathVariable Long id , @RequestBody Pet pet)
    {
        pet = petService.updatePetById(id, pet);
        return ResponseEntity.ok(pet);
    }

}

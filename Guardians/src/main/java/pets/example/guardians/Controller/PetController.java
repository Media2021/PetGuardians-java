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





    @PostMapping("/pet")
    public Pet createPet(@RequestBody Pet pet)
    {
        return petService.createPet(pet);

    }
    @GetMapping("/getPets")
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @DeleteMapping("/getPets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id)
    {

        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getPets/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id)
    {
        Pet pet = null;

        pet= petService. getPetById(id);
        return ResponseEntity.ok(pet);
    }
    @PutMapping("/getPets/{id}")
    public ResponseEntity< Pet > updatePetById(@PathVariable Long id , @RequestBody Pet pet)
    {
        pet = petService.updatePetById(id, pet);
        return ResponseEntity.ok(pet);
    }

}

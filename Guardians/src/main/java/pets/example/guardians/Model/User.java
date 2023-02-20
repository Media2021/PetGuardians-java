package pets.example.guardians.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String name;

    private String last_name ;

    private String user_name;

    private String email;

    private String address;


    private int password;



    private int phone;


    private Date birthdate;

    private UserRole userRole;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.payload;

import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter @Getter
@ToString
public class UserStatusResponse {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private Role role;

    public static UserStatusResponse create(User user ){

        UserStatusResponse usr = new UserStatusResponse();
        usr.setId( user.getId() );
        usr.setUsername( user.getUsername() );
        usr.setFirstName( user.getFirstName() );
        usr.setLastName( user.getLastName() );
        usr.setEmail( user.getEmail() );
        usr.setActive( user.isActive() );
        usr.setRole( user.getRole() );

        return usr;
    }
}

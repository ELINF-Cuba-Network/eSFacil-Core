/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.payload;

import cu.vlired.submod.model.User;
import cu.vlired.submod.model.Role;

/**
 *
 * @author luizo
 */
public class UserStatusResponse {

    private Long id;

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
        usr.setIsActive( user.isActive() );
        usr.setRole( user.getRole() );

        return usr;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
}

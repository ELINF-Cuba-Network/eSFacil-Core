/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.payload;

/**
 *
 * @author luizo
 */
public class UserExistRequest {
    private String  username;
    private String  email;

    private long    id = -1;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserExistRequest{" + "username=" + username + ", email=" + email + ", id=" + id + '}';
    }

    
}

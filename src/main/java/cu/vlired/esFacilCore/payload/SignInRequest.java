/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter @Getter
@ToString
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}

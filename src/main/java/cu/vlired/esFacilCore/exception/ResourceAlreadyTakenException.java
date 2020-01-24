/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.esFacilCore.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author luizo
 */

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyTakenException extends RuntimeException{

    public ResourceAlreadyTakenException(String message) {
        super(message);
    }

}

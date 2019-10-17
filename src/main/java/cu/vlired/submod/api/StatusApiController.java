/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author luizo
 */
@RestController
public class StatusApiController {
    
    @RequestMapping( value = "/status" )
    public ResponseEntity<String> status() {
        return new ResponseEntity("Alive!!", HttpStatus.OK);
    }
    
}

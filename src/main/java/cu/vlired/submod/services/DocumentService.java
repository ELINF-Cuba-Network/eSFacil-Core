/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.services;

import cu.vlired.submod.model.Bitstream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author luizo
 */

@Service
public class DocumentService {
    
    @Value("${dir.darkaiv}")
    private String dir_darkaiv;
    
    public boolean CreateDocumentFromFile(MultipartFile file) throws IOException
    {
        boolean is_created =  false;
        
        
        return is_created;
    }
}

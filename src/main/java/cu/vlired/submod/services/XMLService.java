/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.services;

import java.util.HashMap;
import java.util.List;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author luizo
 */

@Service
public class XMLService {
    
    public HashMap<String, Object> XMLtoHashMap(String XML) throws DocumentException
    {
        HashMap<String, Object> XML2HM = new HashMap<>();
        Document document =  new SAXReader().read(XML);
        List<Node> nodes = document.selectNodes("titleStmt" );        
        
        for (Node node : nodes) 
        {
            XML2HM.put("title", node.selectSingleNode("title").getText());
        }
        return XML2HM;
    }
}

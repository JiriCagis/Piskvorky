package utils.xmlParser;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlParserImpl<T> implements XmlParser<T>{
       
    @Override
    public List<T> parse(File xmlFile){
        try {
            FileInputStream fis = new FileInputStream(xmlFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            XMLDecoder xmlDecoder = new XMLDecoder(bis);
            return (List<T>) xmlDecoder.readObject();
        } catch (FileNotFoundException ex) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public void save(List<T> list, File outFile){
        try {
            FileOutputStream fos = new FileOutputStream(outFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            XMLEncoder xmlEncoder = new XMLEncoder(bos);
            xmlEncoder.writeObject(list);
            xmlEncoder.close();        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XmlParserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}

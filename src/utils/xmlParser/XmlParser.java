package utils.xmlParser;

import java.io.File;
import java.util.List;

/**
 * Component use to persistent data from list object to XML file
 * @author adminuser
 * @param <T> type object
 */
public interface XmlParser<T> {
    public List<T> parse(File xmlFile);
    public void save(List<T> list, File outFile);
}

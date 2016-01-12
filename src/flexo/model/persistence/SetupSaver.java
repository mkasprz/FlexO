package flexo.model.persistence;

import flexo.model.Setup;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

// [TODO] Try finding up a name for class which could be used to both 'save' or 'load' Setup
public class SetupSaver { // [TODO] Consider changing the name to 'SetupWriter' or 'SetupExporter'

    public static void saveToXMLFile(Setup setup, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Setup.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(setup, file);
    }

}

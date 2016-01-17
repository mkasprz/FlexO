package flexo.model.persistence;

import flexo.model.Setup;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static flexo.utilities.ThreadInterruptionUtility.checkIfInterrupted;

// [TODO] Try finding up a name for class which could be used to both 'save' or 'load' Setup
public class SetupLoader { // [TODO] Consider changing the name to 'SetupReader' or 'SetupImporter'

    public static Setup loadFromXMLFile(File file) throws JAXBException, InterruptedException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(Setup.class).createUnmarshaller();
        checkIfInterrupted();
        return (Setup) unmarshaller.unmarshal(file);
    }

}

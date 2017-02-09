package uo.sdi.persistence_jpa.util;

import java.io.IOException;
import javax.persistence.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Jpa {

    private static EntityManagerFactory emf = null;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<EntityManager>();

    public static EntityManager createEntityManager () {
	EntityManager entityManager = getEmf().createEntityManager();
	emThread.set(entityManager);
	return entityManager;
    }

    public static EntityManager getManager () {
	return emThread.get();
    }

    private static EntityManagerFactory getEmf () {
	if (emf == null) {
	    String persistenceUnitName = loadPersistentUnitName();
	    emf = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	return emf;
    }

    private static String loadPersistentUnitName () {
	try {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(Jpa.class.getResourceAsStream("/META-INF/persistence.xml"));

	    doc.getDocumentElement().normalize();
	    NodeList nl = doc.getElementsByTagName("persistence-unit");

	    return ((Element) nl.item(0)).getAttribute("name");

	} catch (ParserConfigurationException e1) {
	    throw new RuntimeException(e1);
	} catch (SAXException e1) {
	    throw new RuntimeException(e1);
	} catch (IOException e1) {
	    throw new RuntimeException(e1);
	}
    }
}
package uo.sdi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uo.sdi.persistence.util.Jpa;

/**
 * Fuerza a que se cargen los parámetros de configuración, se analizen todos los
 * mapeos y, si procede, se crea la BDD
 */
public class LoadEntityManagerFactory {

	public static void main(String[] args) {
		EntityManager ent = Jpa.createEntityManager();
		EntityTransaction trx = ent.getTransaction();

		trx.begin();

		/**
		 * Se usa para comprobar que est� realmente bien mapeado. Si salta
		 * alguna excepcion est� mal.
		 * 
		 * Esta prueba debe realizarse con tu base de datos, no con la primera
		 * que te dan ellos.
		 * 
		 */

		trx.commit();
		ent.close();
		System.out.println("--> Si no hay excepciones todo va bien");
		System.out.println("\n\t (O no hay ninguna clase mapeada)");
	}

}

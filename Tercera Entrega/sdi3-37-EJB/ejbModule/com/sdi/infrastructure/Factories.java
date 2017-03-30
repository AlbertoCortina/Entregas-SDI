package com.sdi.infrastructure;

import com.sdi.business.LocalEjbServicesLocator;
import com.sdi.business.ServicesFactory;
import com.sdi.persistence.Persistence;
import com.sdi.persistence.PersistenceFactory;

public class Factories {
	
//	public static ServicesFactory services = new Services();
	public static ServicesFactory services = new LocalEjbServicesLocator();
	
	public static PersistenceFactory persistence = new Persistence();
	
}
package uo.sdi.business.impl.command;

import javax.persistence.*;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.persistence.util.Jpa;

public class CommandExecutor<T> {

	public T execute(Command<T> cmd) throws BusinessException {
		EntityManager entityManager = Jpa.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		try {
			T object = cmd.execute();

			entityTransaction.commit();
			return object;

		} catch (PersistenceException | BusinessException e) {
			if (entityTransaction.isActive())
				entityTransaction.rollback();
			throw e;
		} finally {
			if (entityManager.isOpen())
				entityManager.close();
		}
	}
}
package io.musala.thedrone.producer;

import io.musala.thedrone.util.Constants;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
    @PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }
}

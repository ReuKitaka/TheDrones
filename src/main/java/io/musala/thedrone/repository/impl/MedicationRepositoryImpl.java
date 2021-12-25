package io.musala.thedrone.repository.impl;

import io.musala.thedrone.model.Medication;
import io.musala.thedrone.repository.MedicationRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class MedicationRepositoryImpl extends JpaRepositoryImplementation<Medication, Long> implements MedicationRepository {

    @Inject
    private EntityManager entityManager;

    public MedicationRepositoryImpl() {
        super(Medication.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}


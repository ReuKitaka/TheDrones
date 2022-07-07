    package io.musala.thedrone.repository.impl;

    import io.musala.thedrone.model.AuditLog;
    import io.musala.thedrone.repository.AuditLogRepository;

    import javax.inject.Inject;
    import javax.persistence.EntityManager;

    public class AuditLogRepositoryImpl extends JpaRepositoryImplementation<AuditLog, Long> implements AuditLogRepository {

        @Inject
        private EntityManager entityManager;

        public AuditLogRepositoryImpl() {
            super(AuditLog.class);
        }

        @Override
        protected EntityManager getEntityManager() {
            return entityManager;
        }


    }

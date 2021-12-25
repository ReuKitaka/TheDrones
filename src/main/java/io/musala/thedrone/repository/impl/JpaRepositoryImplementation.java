package io.musala.thedrone.repository.impl;

import io.musala.thedrone.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

import static javax.transaction.Transactional.TxType.REQUIRED;

public abstract class JpaRepositoryImplementation<E, P> implements JpaRepository<E, P> {
    private final Class<E> entityClass;

    public JpaRepositoryImplementation(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Transactional(REQUIRED)
    @Override
    public E create(E entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Transactional(REQUIRED)
    @Override
    public E edit(E entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional(REQUIRED)
    @Override
    public void remove(E entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Transactional(REQUIRED)
    @Override
    public void deleteById(P id) {
        Optional<E> optionalT = findById(id);
        optionalT.ifPresent(this::remove);
    }

    @Override
    public E find(P id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public Optional<E> findById(P id) {
        E e = getEntityManager().find(entityClass, id);
        if (e != null) {
            return Optional.of(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(P id) {
        Optional<E> optionalT = findById(id);
        return optionalT.isPresent();
    }

    @Override
    public List<E> findAll() {
        CriteriaQuery<E> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<E> findRange(int startPosition, int size) {
        return findRange(startPosition, size, null);
    }

    @Override
    public List<E> findRange(int startPosition, int size, String entityGraph) {
        CriteriaQuery<E> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        TypedQuery<E> q = getEntityManager().createQuery(cq);
        q.setMaxResults(size);
        q.setFirstResult(startPosition);
        if (entityGraph != null) {
            q.setHint("javax.persistence.loadgraph", getEntityManager().getEntityGraph(entityGraph));
        }
        return q.getResultList();
    }

    @Override
    public int count() {
        CriteriaQuery<Long> criteriaQuery =
                getEntityManager().getCriteriaBuilder().createQuery(Long.class);
        Root<E> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(getEntityManager().getCriteriaBuilder().count(root));
        Query query = getEntityManager().createQuery(criteriaQuery);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public Optional<E> findSingleByNamedQuery(String namedQueryName) {
        return findOrEmpty(
                () -> getEntityManager().createNamedQuery(namedQueryName, entityClass).getSingleResult());
    }

    @Override
    public Optional<E> findSingleByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        return findSingleByNamedQuery(namedQueryName, null, parameters);
    }

    @Override
    public Optional<E> findSingleByNamedQuery(
            String namedQueryName, String entityGraph, Map<String, Object> parameters) {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        TypedQuery<E> query = getEntityManager().createNamedQuery(namedQueryName, entityClass);
        rawParameters.forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        if (entityGraph != null) {
            query.setHint("javax.persistence.loadgraph", getEntityManager().getEntityGraph(entityGraph));
        }
        return findOrEmpty(query::getSingleResult);
    }

    @Override
    public List<E> findByNamedQuery(String namedQueryName) {
        return findByNamedQuery(namedQueryName, -1);
    }

    @Override
    public List<E> findByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        return findByNamedQuery(namedQueryName, parameters, -1);
    }

    @Override
    public List<E> findByNamedQuery(String namedQueryName, int resultLimit) {
        return findByNamedQuery(namedQueryName, Collections.EMPTY_MAP, resultLimit);
    }

    @Override
    public List<E> findByNamedQuery(
            String namedQueryName, Map<String, Object> parameters, int resultLimit) {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = getEntityManager().createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        rawParameters.forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
        return query.getResultList();
    }

    public static <E> Optional<E> findOrEmpty(final DaoRetriever<E> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            // log
        }
        return Optional.empty();
    }

    @FunctionalInterface
    public interface DaoRetriever<E> {

        E retrieve() throws NoResultException;
    }
}

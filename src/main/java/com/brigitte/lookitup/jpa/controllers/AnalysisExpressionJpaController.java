package com.brigitte.lookitup.jpa.controllers;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import com.brigitte.lookitup.jpa.controllers.exceptions.NonexistentEntityException;
import com.brigitte.lookitup.jpa.controllers.exceptions.PreexistingEntityException;
import com.brigitte.lookitup.jpa.controllers.exceptions.RollbackFailureException;
import com.brigitte.lookitup.jpa.entities.AnalysisExpression;
import com.brigitte.lookitup.jpa.entities.AnalysisRecord;

public class AnalysisExpressionJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "JsfJpaCrudPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AnalysisExpression analysisExpression) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AnalysisRecord analysisRecordId = analysisExpression.getAnalysisRecordId();
            if (analysisRecordId != null) {
                analysisRecordId = em.getReference(analysisRecordId.getClass(), analysisRecordId.getAnalysisRecordId());
                analysisExpression.setAnalysisRecordId(analysisRecordId);
            }
            em.persist(analysisExpression);
            if (analysisRecordId != null) {
                analysisRecordId.getAnalysisExpressionCollection().add(analysisExpression);
                analysisRecordId = em.merge(analysisRecordId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAnalysisExpression(analysisExpression.getId()) != null) {
                throw new PreexistingEntityException("AnalysisExpression " + analysisExpression + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AnalysisExpression analysisExpression) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AnalysisExpression persistentAnalysisExpression = em.find(AnalysisExpression.class, analysisExpression.getId());
            AnalysisRecord analysisRecordIdOld = persistentAnalysisExpression.getAnalysisRecordId();
            AnalysisRecord analysisRecordIdNew = analysisExpression.getAnalysisRecordId();
            if (analysisRecordIdNew != null) {
                analysisRecordIdNew = em.getReference(analysisRecordIdNew.getClass(), analysisRecordIdNew.getAnalysisRecordId());
                analysisExpression.setAnalysisRecordId(analysisRecordIdNew);
            }
            analysisExpression = em.merge(analysisExpression);
            if (analysisRecordIdOld != null && !analysisRecordIdOld.equals(analysisRecordIdNew)) {
                analysisRecordIdOld.getAnalysisExpressionCollection().remove(analysisExpression);
                analysisRecordIdOld = em.merge(analysisRecordIdOld);
            }
            if (analysisRecordIdNew != null && !analysisRecordIdNew.equals(analysisRecordIdOld)) {
                analysisRecordIdNew.getAnalysisExpressionCollection().add(analysisExpression);
                analysisRecordIdNew = em.merge(analysisRecordIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = analysisExpression.getId();
                if (findAnalysisExpression(id) == null) {
                    throw new NonexistentEntityException("The analysisExpression with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AnalysisExpression analysisExpression;
            try {
                analysisExpression = em.getReference(AnalysisExpression.class, id);
                analysisExpression.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The analysisExpression with id " + id + " no longer exists.", enfe);
            }
            AnalysisRecord analysisRecordId = analysisExpression.getAnalysisRecordId();
            if (analysisRecordId != null) {
                analysisRecordId.getAnalysisExpressionCollection().remove(analysisExpression);
                analysisRecordId = em.merge(analysisRecordId);
            }
            em.remove(analysisExpression);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AnalysisExpression> findAnalysisExpressionEntities() {
        return findAnalysisExpressionEntities(true, -1, -1);
    }

    public List<AnalysisExpression> findAnalysisExpressionEntities(int maxResults, int firstResult) {
        return findAnalysisExpressionEntities(false, maxResults, firstResult);
    }

    private List<AnalysisExpression> findAnalysisExpressionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AnalysisExpression as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AnalysisExpression findAnalysisExpression(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnalysisExpression.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnalysisExpressionCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from AnalysisExpression as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

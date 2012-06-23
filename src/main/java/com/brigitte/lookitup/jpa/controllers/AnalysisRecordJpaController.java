package com.brigitte.lookitup.jpa.controllers;

import com.brigitte.lookitup.jpa.controllers.exceptions.IllegalOrphanException;
import com.brigitte.lookitup.jpa.controllers.exceptions.NonexistentEntityException;
import com.brigitte.lookitup.jpa.controllers.exceptions.PreexistingEntityException;
import com.brigitte.lookitup.jpa.controllers.exceptions.RollbackFailureException;
import com.brigitte.lookitup.jpa.entities.AnalysisExpression;
import com.brigitte.lookitup.jpa.entities.AnalysisRecord;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.UserTransaction;

/**
 *
 * @author mbohm
 */
public class AnalysisRecordJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "JsfJpaCrudPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AnalysisRecord analysisRecord) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (analysisRecord.getAnalysisExpressionCollection() == null) {
            analysisRecord.setAnalysisExpressionCollection(new ArrayList<AnalysisExpression>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<AnalysisExpression> attachedAnalysisExpressionCollection = new ArrayList<AnalysisExpression>();
            for (AnalysisExpression analysisExpressionCollectionAnalysisExpressionToAttach : analysisRecord.getAnalysisExpressionCollection()) {
                analysisExpressionCollectionAnalysisExpressionToAttach = em.getReference(analysisExpressionCollectionAnalysisExpressionToAttach.getClass(), analysisExpressionCollectionAnalysisExpressionToAttach.getId());
                attachedAnalysisExpressionCollection.add(analysisExpressionCollectionAnalysisExpressionToAttach);
            }
            analysisRecord.setAnalysisExpressionCollection(attachedAnalysisExpressionCollection);
            em.persist(analysisRecord);
            for (AnalysisExpression analysisExpressionCollectionAnalysisExpression : analysisRecord.getAnalysisExpressionCollection()) {
                AnalysisRecord oldAnalysisRecordIdOfAnalysisExpressionCollectionAnalysisExpression = analysisExpressionCollectionAnalysisExpression.getAnalysisRecordId();
                analysisExpressionCollectionAnalysisExpression.setAnalysisRecordId(analysisRecord);
                analysisExpressionCollectionAnalysisExpression = em.merge(analysisExpressionCollectionAnalysisExpression);
                if (oldAnalysisRecordIdOfAnalysisExpressionCollectionAnalysisExpression != null) {
                    oldAnalysisRecordIdOfAnalysisExpressionCollectionAnalysisExpression.getAnalysisExpressionCollection().remove(analysisExpressionCollectionAnalysisExpression);
                    oldAnalysisRecordIdOfAnalysisExpressionCollectionAnalysisExpression = em.merge(oldAnalysisRecordIdOfAnalysisExpressionCollectionAnalysisExpression);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAnalysisRecord(analysisRecord.getAnalysisRecordId()) != null) {
                throw new PreexistingEntityException("AnalysisRecord " + analysisRecord + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AnalysisRecord analysisRecord) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AnalysisRecord persistentAnalysisRecord = em.find(AnalysisRecord.class, analysisRecord.getAnalysisRecordId());
            Collection<AnalysisExpression> analysisExpressionCollectionOld = persistentAnalysisRecord.getAnalysisExpressionCollection();
            Collection<AnalysisExpression> analysisExpressionCollectionNew = analysisRecord.getAnalysisExpressionCollection();
            List<String> illegalOrphanMessages = null;
            for (AnalysisExpression analysisExpressionCollectionOldAnalysisExpression : analysisExpressionCollectionOld) {
                if (!analysisExpressionCollectionNew.contains(analysisExpressionCollectionOldAnalysisExpression)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AnalysisExpression " + analysisExpressionCollectionOldAnalysisExpression + " since its analysisRecordId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AnalysisExpression> attachedAnalysisExpressionCollectionNew = new ArrayList<AnalysisExpression>();
            for (AnalysisExpression analysisExpressionCollectionNewAnalysisExpressionToAttach : analysisExpressionCollectionNew) {
                analysisExpressionCollectionNewAnalysisExpressionToAttach = em.getReference(analysisExpressionCollectionNewAnalysisExpressionToAttach.getClass(), analysisExpressionCollectionNewAnalysisExpressionToAttach.getId());
                attachedAnalysisExpressionCollectionNew.add(analysisExpressionCollectionNewAnalysisExpressionToAttach);
            }
            analysisExpressionCollectionNew = attachedAnalysisExpressionCollectionNew;
            analysisRecord.setAnalysisExpressionCollection(analysisExpressionCollectionNew);
            analysisRecord = em.merge(analysisRecord);
            for (AnalysisExpression analysisExpressionCollectionNewAnalysisExpression : analysisExpressionCollectionNew) {
                if (!analysisExpressionCollectionOld.contains(analysisExpressionCollectionNewAnalysisExpression)) {
                    AnalysisRecord oldAnalysisRecordIdOfAnalysisExpressionCollectionNewAnalysisExpression = analysisExpressionCollectionNewAnalysisExpression.getAnalysisRecordId();
                    analysisExpressionCollectionNewAnalysisExpression.setAnalysisRecordId(analysisRecord);
                    analysisExpressionCollectionNewAnalysisExpression = em.merge(analysisExpressionCollectionNewAnalysisExpression);
                    if (oldAnalysisRecordIdOfAnalysisExpressionCollectionNewAnalysisExpression != null && !oldAnalysisRecordIdOfAnalysisExpressionCollectionNewAnalysisExpression.equals(analysisRecord)) {
                        oldAnalysisRecordIdOfAnalysisExpressionCollectionNewAnalysisExpression.getAnalysisExpressionCollection().remove(analysisExpressionCollectionNewAnalysisExpression);
                        oldAnalysisRecordIdOfAnalysisExpressionCollectionNewAnalysisExpression = em.merge(oldAnalysisRecordIdOfAnalysisExpressionCollectionNewAnalysisExpression);
                    }
                }
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
                Integer id = analysisRecord.getAnalysisRecordId();
                if (findAnalysisRecord(id) == null) {
                    throw new NonexistentEntityException("The analysisRecord with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AnalysisRecord analysisRecord;
            try {
                analysisRecord = em.getReference(AnalysisRecord.class, id);
                analysisRecord.getAnalysisRecordId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The analysisRecord with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<AnalysisExpression> analysisExpressionCollectionOrphanCheck = analysisRecord.getAnalysisExpressionCollection();
            for (AnalysisExpression analysisExpressionCollectionOrphanCheckAnalysisExpression : analysisExpressionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AnalysisRecord (" + analysisRecord + ") cannot be destroyed since the AnalysisExpression " + analysisExpressionCollectionOrphanCheckAnalysisExpression + " in its analysisExpressionCollection field has a non-nullable analysisRecordId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(analysisRecord);
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

    public List<AnalysisRecord> findAnalysisRecordEntities() {
        return findAnalysisRecordEntities(true, -1, -1);
    }

    public List<AnalysisRecord> findAnalysisRecordEntities(int maxResults, int firstResult) {
        return findAnalysisRecordEntities(false, maxResults, firstResult);
    }

    private List<AnalysisRecord> findAnalysisRecordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AnalysisRecord as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AnalysisRecord findAnalysisRecord(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnalysisRecord.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnalysisRecordCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from AnalysisRecord as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

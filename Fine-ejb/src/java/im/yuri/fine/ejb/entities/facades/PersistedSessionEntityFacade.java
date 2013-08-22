/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb.entities.facades;

import im.yuri.fine.ejb.entities.PersistedSessionEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yuri
 */
@Stateless
public class PersistedSessionEntityFacade extends AbstractFacade<PersistedSessionEntity> {
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersistedSessionEntityFacade() {
        super(PersistedSessionEntity.class);
    }
    
    public PersistedSessionEntity findByUserAndHash(String email, String hash) {
         try {
          return (PersistedSessionEntity)em.createQuery(
            "SELECT c FROM PersistedSessionEntity c WHERE c.userId.email = :userEmail "
                                                    + "AND c.sessionHash = :hash")
                .setParameter("userEmail", email)
                .setParameter("hash", hash)
                .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
}

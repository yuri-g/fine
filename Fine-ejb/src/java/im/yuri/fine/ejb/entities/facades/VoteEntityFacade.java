/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb.entities.facades;

import im.yuri.fine.ejb.entities.VoteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author admin
 */
@Stateless
public class VoteEntityFacade extends AbstractFacade<VoteEntity> {
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VoteEntityFacade() {
        super(VoteEntity.class);
    }
    
    
    public VoteEntity getByEntryAndUser(Long entryId, Long userId) {
       try {
           return (VoteEntity)em.createQuery(
                   "SELECT c FROM VoteEntity c WHERE c.user.id = :userId AND c.entry.id = :entryId")
                   .setParameter("userId", userId)
                   .setParameter("entryId", entryId)
                   .getSingleResult();
       }
       catch(NoResultException e) {
           return null;
       }
    }


    
}

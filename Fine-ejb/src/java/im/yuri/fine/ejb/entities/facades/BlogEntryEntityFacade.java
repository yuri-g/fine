/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb.entities.facades;

import im.yuri.fine.ejb.entities.BlogEntryEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yuri
 */
@Stateless
public class BlogEntryEntityFacade extends AbstractFacade<BlogEntryEntity> {
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BlogEntryEntityFacade() {
        super(BlogEntryEntity.class);
    }
    
    public List<BlogEntryEntity> findAllByEmailDateDesc(String email) {
        try {
            return (List<BlogEntryEntity>)em.createQuery(
                    "SELECT c FROM BlogEntryEntity c WHERE c.user.email = :userEmail ORDER BY c.createdAt DESC ")
                    .setParameter("userEmail", email).getResultList();
        }
        catch(NoResultException e) {
            return null;
        }
    } 
    
    public BlogEntryEntity findById(int id) {
        try {
            return (BlogEntryEntity)em.createQuery(
                    "SELECT c FROM BlogEntryEntity c WHERE c.id = id")
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
    }
    
    public List<BlogEntryEntity> findPopular() {
        try {
            return (List<BlogEntryEntity>)em.createQuery(
                    "SELECT c FROM BlogEntryEntity c ORDER BY c.rating DESC")
                    .setMaxResults(10)
                    .getResultList();  
        }
        catch(NoResultException e) {
            return null;
        }
    }
    
    
}

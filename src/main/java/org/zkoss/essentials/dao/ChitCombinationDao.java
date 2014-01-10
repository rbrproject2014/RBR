package org.zkoss.essentials.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.zkoss.essentials.entity.Chit;
import org.zkoss.essentials.entity.ChitCombination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("chitCombinationDao")
@Scope(value="singleton",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ChitCombinationDao {
    @PersistenceContext
    private EntityManager em;

    public List<ChitCombination> queryAll(){
        Query query = em.createQuery("select o from ChitCombination o");
        return query.getResultList();
    }

    public List<ChitCombination> queryAllByChitID(long chitID){
        Query query = em.createQuery("SELECT e FROM ChitCombination e JOIN e.chit r WHERE r.id=:d");
        query.setParameter("d",chitID);
        return query.getResultList();
    }

    public ChitCombination reload(ChitCombination chitCombination){
        return em.find(ChitCombination.class,chitCombination.getId());
    }

    public ChitCombination get(Long id){
        return em.find(ChitCombination.class,id);
    }

    public ChitCombination save(ChitCombination chitCombination){
        em.persist(chitCombination);
        return chitCombination;
    }

    public ChitCombination update(ChitCombination chitCombination){
        chitCombination = em.merge(chitCombination);
        return chitCombination;
    }

    public void delete(ChitCombination chitCombination){
        ChitCombination c = em.find(ChitCombination.class,chitCombination.getId());
        if(c!=null){
            em.remove(c);
        }
    }
}

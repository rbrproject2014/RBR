package org.zkoss.essentials.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.zkoss.essentials.entity.ChitCombinationDetail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("chitCombinationDetailDao")
@Scope(value="singleton",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ChitCombinationDetailDao {
    @PersistenceContext
    private EntityManager em;


    public List<ChitCombinationDetail> queryAll(){
        Query query = em.createQuery("SELECT o FROM ChitCombinationDetail o");
        return query.getResultList();
    }

    public List<ChitCombinationDetail> queryAllByChitCombinationID(long chitCombinationID){
        Query query = em.createQuery("SELECT e FROM ChitCombinationDetail e JOIN e.chitCombination r WHERE r.id=:d");
        query.setParameter("d",chitCombinationID);
        return query.getResultList();
    }

    public ChitCombinationDetail reload(ChitCombinationDetail chitCombinationDetail){
        return em.find(ChitCombinationDetail.class,chitCombinationDetail.getId());
    }

    public ChitCombinationDetail get(Long id){
        return em.find(ChitCombinationDetail.class,id);
    }

    public ChitCombinationDetail save(ChitCombinationDetail chitCombinationDetail){
        em.persist(chitCombinationDetail);
        return chitCombinationDetail;
    }

    public ChitCombinationDetail update(ChitCombinationDetail chitCombinationDetail){
        chitCombinationDetail = em.merge(chitCombinationDetail);
        return chitCombinationDetail;
    }

    public void delete(ChitCombinationDetail chitCombinationDetail){
        ChitCombinationDetail chitCombinationDetail1 = em.find(ChitCombinationDetail.class,chitCombinationDetail.getId());
        if(chitCombinationDetail1 != null){
            em.remove(chitCombinationDetail1);
        }
    }
}

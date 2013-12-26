package org.zkoss.essentials.dao;

import org.springframework.stereotype.Repository;
import org.zkoss.essentials.entity.Chit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ChitDao {
    @PersistenceContext
    private EntityManager em;

    public List<Chit> queryAll(){
        Query query = em.createQuery("select o from Chit o");
        List<Chit> list = query.getResultList();
        return list;
    }

    public Chit reload(Chit chit){
        return em.find(Chit.class,chit.getId());
    }

    public Chit get(Long id){
        return em.find(Chit.class,id);
    }

    public Chit save(Chit chit){
        em.persist(chit);
        return chit;
    }


    public Chit update(Chit chit){
        chit = em.merge(chit);
        return chit;
    }


    public void delete(Chit chit){
        Chit c = get(chit.getId());
        if(c != null){
            em.remove(c);
        }
    }


}

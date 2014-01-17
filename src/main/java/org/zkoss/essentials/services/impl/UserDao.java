package org.zkoss.essentials.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.essentials.entity.User;

import java.util.List;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
    public User get(String account){
        return em.find(User.class, account);
    }
 
    @Transactional
    public User save(User user){
        em.persist(user);
        return user;
    }
    
    @Transactional
    public User update(User user){
    	user = em.merge(user);
        return user;
    }

    public List<User> queryAll(){
        Query query = em.createQuery("select o from User o");
        List<User> list = query.getResultList();
        return list;
    }

    //prob here, user is not deleted yet???
    public void delete(User user){
        User u = get(user.getAccount());
        System.out.println("============ user deleted: "+u.getAccount());
        if(u != null){
            em.remove(u);
        }
    }
}

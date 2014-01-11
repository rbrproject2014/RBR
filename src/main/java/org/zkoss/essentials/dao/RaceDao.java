package org.zkoss.essentials.dao;

import org.zkoss.essentials.entity.Race;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RaceDao {
    @PersistenceContext
    private EntityManager em;

    public List<Race> queryAll(){
        Query query = em.createQuery("SELECT o FROM Race o");
        return query.getResultList();
    }

    public List<Race> queryAllByDate(Date date){

        Query query1 = em.createQuery(getCriteriaQuery("raceDate",date));
        return query1.getResultList();
    }

    public List<Race> queryAllbyMeetingPlace(String meetingPlace){
        return em.createQuery(getCriteriaQuery("meetingPlace",meetingPlace)).getResultList();
    }

    public Race reload(Race race){
        return em.find(Race.class,race.getRaceSerialNo());
    }

    public Race get(Long serialNumber){
        return em.find(Race.class,serialNumber);
    }

    public Race save(Race race){
        em.persist(race);
        return race;
    }

    public Race update(Race race){
        race = em.merge(race);
        return race;
    }

    public void delete(Race race){
        Race race1 = em.find(Race.class,race.getRaceSerialNo());
        if(race1 != null){
            em.remove(race1);
        }
    }

    private CriteriaQuery<Race> getCriteriaQuery(String field, Object value){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Race> query = cb.createQuery(Race.class);
        Root<Race> race = query.from(Race.class);
        query.where(cb.equal(race.get(field),value));
        return query;
    }
}

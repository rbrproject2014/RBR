package org.zkoss.essentials.dao;

import org.springframework.stereotype.Repository;
import org.zkoss.essentials.entity.Race;
import org.zkoss.essentials.entity.RaceDetail;
import org.zkoss.zul.ListModelList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RaceDetailDao {
    @PersistenceContext
    private EntityManager em;

    public List<RaceDetail> queryAll(){
        Query query = em.createQuery("SELECT o FROM RaceDetail o");
        return query.getResultList();
    }

    public List<RaceDetail> queryAllByRaceDate(Date date){
        Query query = em.createQuery("SELECT e FROM RaceDetail e JOIN e.race r WHERE r.raceDate=:d");
        query.setParameter("d",date);
        return query.getResultList();
    }

    public List<RaceDetail> getRaceDetailsPerGivenRaceID(long raceSerialNo){
        Query query = em.createQuery("SELECT e FROM RaceDetail e JOIN e.race r WHERE r.raceSerialNo=:d");
        query.setParameter("d",raceSerialNo);
        List<RaceDetail> raceDetailList = query.getResultList();
        /******************* Debug Code **************************************/
//        Iterator<RaceDetail> iterator = raceDetailList.iterator();
//        while (iterator.hasNext()){
//            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^ "+iterator.next().getHorseId());
//        }
        /*********************************************************************/
        return raceDetailList;
    }

    public List<RaceDetail> getRaceDetailsToBeRemovedLOV(RaceDetail raceDetail){
        //System.out.println("**************************************************************");
        long raceSerialNo = raceDetail.getRace().getRaceSerialNo();
        //System.out.println("raceSerialNo: "+raceSerialNo);
        return getRaceDetailsPerGivenRaceID(raceSerialNo);
    }

    public RaceDetail reload(RaceDetail raceDetail){
        return em.find(RaceDetail.class,raceDetail.getRaceDetId());
    }

    public RaceDetail get(Long id){
        return em.find(RaceDetail.class,id);
    }

    public RaceDetail save(RaceDetail raceDetail){
        em.persist(raceDetail);
        return  raceDetail;
    }

    public RaceDetail update(RaceDetail raceDetail){
        raceDetail = em.merge(raceDetail);
        return raceDetail;
    }

    public void delete(RaceDetail raceDetail){
        RaceDetail rd = em.find(RaceDetail.class,raceDetail.getRaceDetId());
        if(rd != null){
            em.remove(rd);
        }
    }

}

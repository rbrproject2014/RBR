package org.zkoss.essentials.services.impl;

import org.zkoss.essentials.entity.Race;
import org.zkoss.essentials.dao.RaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.essentials.dao.RaceDetailDao;
import org.zkoss.essentials.entity.RaceDetail;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.zul.ListModelList;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("raceService")
@Scope(value="singleton",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class RaceServiceImpl implements RaceService {
    @Autowired
    RaceDao raceDao;
    @Autowired
    RaceDetailDao raceDetailDao;

    @Override
    @Transactional(readOnly = true)
    public List<Race> getRaceList() {
        return raceDao.queryAll();

    }

    @Override
    @Transactional
    public List<Race> getRaceListByDate(Date date) {
        return raceDao.queryAllByDate(date);

    }

    @Override
    public List<Race> getRaceListByMeetingPlace(String meetingPlace) {
        return raceDao.queryAllbyMeetingPlace(meetingPlace);
    }

    @Override
    public List<RaceDetail> getRaceDetailListByRaceDate(Date date) {
        List<RaceDetail> raceDetailList = raceDetailDao.queryAll();
        //System.out.println(" ------------- Query all results:"+ raceDetailList.size());
        return raceDetailList;
    }

    public List<RaceDetail> getToBeRemovedRaceDetailList(RaceDetail raceDetail){
        return raceDetailDao.getRaceDetailsToBeRemovedLOV(raceDetail);
    }

    public List<RaceDetail> getRaceDetailByRaceSerialNo(long raceSerialNo){
        return raceDetailDao.getRaceDetailsPerGivenRaceID(raceSerialNo);
    }


    @Override
    @Transactional
    public Race getRace(Long id) {
        return raceDao.get(id);
    }

    @Override
    @Transactional
    public Race saveRace(Race race) {
        raceDao.save(race);
        //Iterator<RaceDetail> iterator = race.getRaceDetails().iterator();
        List<RaceDetail> raceDetails = getRaceDetailByRaceSerialNo(race.getRaceSerialNo());
        Iterator<RaceDetail> iterator = raceDetails.iterator();
        while(iterator.hasNext()){
            RaceDetail raceDetail = iterator.next();
            raceDetailDao.save(raceDetail);
        }
        return race;
    }

    @Transactional
    public RaceDetail saveRaceDetail(RaceDetail raceDetail){
        raceDetailDao.save(raceDetail);
        return raceDetail;
    }

    @Override
    @Transactional
    public Race updateRace(Race race) {
//        System.out.println("0000000 total race details per race "+race.getRaceDetails().size());
        return raceDao.update(race);
    }

    @Override
    @Transactional
    public void deleteRace(Race race) {
        // Iterator<RaceDetail> iterator = race.getRaceDetails().iterator();
        List<RaceDetail> raceDetails = getRaceDetailByRaceSerialNo(race.getRaceSerialNo());
        Iterator<RaceDetail> iterator = raceDetails.iterator();
        while (iterator.hasNext()){
            raceDetailDao.delete(iterator.next());
        }

        raceDao.delete(race);

    }

    @Transactional
    public void deleteRaceDetail(RaceDetail raceDetail){
        raceDetailDao.delete(raceDetail);
    }
}

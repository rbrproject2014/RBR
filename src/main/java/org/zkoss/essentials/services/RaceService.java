package org.zkoss.essentials.services;

import org.zkoss.essentials.entity.Race;
import org.zkoss.essentials.entity.RaceDetail;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RaceService {

    public List<Race> getRaceList();
    public List<Race> getRaceListByDate(Date date);
    public List<Race> getRaceListByMeetingPlace(String meetingPlace);
    public List<RaceDetail> getRaceDetailListByRaceDate(Date date);

    public Race getRace(Long id);
    public Race saveRace(Race race);
    public Race updateRace(Race race);
    public void deleteRace(Race race);
}

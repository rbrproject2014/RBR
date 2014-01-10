package org.zkoss.essentials.services;

import org.zkoss.essentials.entity.Chit;
import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.ChitCombinationDetail;
import org.zkoss.essentials.entity.RaceDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ChitService {

    public void saveChit(Chit chit);
    public void updateChit(Chit chit);
    public List<Chit> getAllChits();
    public List<ChitCombination> getAllChitCombinationsByChitID(long chitID);
    public List<ChitCombinationDetail> getAllChitCombinationDetailsByChitCombinationID(long chitCombinationID);

}

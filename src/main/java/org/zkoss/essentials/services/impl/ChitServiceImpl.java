package org.zkoss.essentials.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.essentials.dao.ChitCombinationDao;
import org.zkoss.essentials.dao.ChitCombinationDetailDao;
import org.zkoss.essentials.dao.ChitDao;
import org.zkoss.essentials.entity.Chit;
import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.ChitCombinationDetail;
import org.zkoss.essentials.services.ChitService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 11/30/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("chitService")
@Scope(value="singleton",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ChitServiceImpl implements ChitService {

    @Autowired
    @Qualifier("chitDAO")
    ChitDao chitDao;
    @Autowired
    ChitCombinationDao chitCombinationDao;
    @Autowired
    ChitCombinationDetailDao chitCombinationDetailDao;

    @Override
    @Transactional
    public void saveChit(Chit chit) {
        //saving chit master record
        chitDao.save(chit);

        Iterator<ChitCombination> iterator = chit.getChitCombinations().iterator();
        while (iterator.hasNext()){
            ChitCombination chitCombination = iterator.next();
            //saving each chit combination
            chitCombination = chitCombinationDao.save(chitCombination);
            Iterator<ChitCombinationDetail> detailIterator = chitCombination.getChitCombinationDetails().iterator();
            while(detailIterator.hasNext()){
                ChitCombinationDetail chitCombinationDetail = detailIterator.next();
                //saving each chit combination detail
                chitCombinationDetail = chitCombinationDetailDao.save(chitCombinationDetail);
            }
        }



    }



    @Override
    @Transactional
    public void updateChit(Chit chit) {

    }

    //@Override
    public List<Chit> getAllChits() {
        List<Chit> raceDetailList = chitDao.queryAll();
        System.out.println(" ------------- Query all Chit results:"+ raceDetailList.size());
        return raceDetailList;
    }

    public List<ChitCombination> getAllChitCombinationsByChitID(long chitID) {
        List<ChitCombination> chitCombinationListList = chitCombinationDao.queryAllByChitID(chitID);
        System.out.println(" ------------- Query all Chit Combinations:"+ chitCombinationListList.size());
        return chitCombinationListList;
    }

    public List<ChitCombinationDetail> getAllChitCombinationDetailsByChitCombinationID(long chitCombinationID) {
        List<ChitCombinationDetail> chitCombinationDetailList = chitCombinationDetailDao.queryAllByChitCombinationID(chitCombinationID);
        System.out.println(" ------------- Query all Chit Combination Details:"+ chitCombinationDetailList.size());
        return chitCombinationDetailList;
    }

}

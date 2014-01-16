package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.entity.*;
import org.zkoss.essentials.services.ChitService;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suhan
 * Date: 1/16/14
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class raceViewController extends SelectorComposer<Component>{
    Race race = new Race();
    @Wire
    Listbox raceListbox;
    @Wire
    Listbox raceDetailsListbox;
    @Wire
    Label totalRacesOutput;

    //data for the view
    List<Race> races = new ArrayList<Race>();
    List<RaceDetail> raceDetailss = new ArrayList<RaceDetail>();
    ListModelList<Race> raceListModelList;
    ListModelList<RaceDetail> raceDetailListModelList;
    Chit chit;
    ChitCombination chitCombination;
    ChitCombinationDetail chitCombinationDetail;


    @WireVariable
    RaceService raceService;

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        //part 1
        super.doAfterCompose(comp);
        raceListModelList = new ListModelList<Race>();
        raceListModelList.setMultiple(true);
        raceDetailListModelList = new ListModelList<RaceDetail>();
        raceDetailListModelList.setMultiple(true);
        System.out.println(raceService==null?"Race Service is null ************************":"Race Service is not null***************");
        raceListModelList.addAll(raceService.getRaceList());
        raceListbox.setModel(raceListModelList);
        //part 2
        totalRacesOutput.setValue(raceListModelList.getSize()+"");
    }

    //when user select one row from the Race list
    @Listen("onSelect = #raceListbox")
    public void onRaceRowSelect() {
        raceDetailListModelList.clear();
        raceDetailListModelList.addAll(raceService.getRaceDetailByRaceSerialNo(raceListModelList.getSelection().iterator().next().getRaceSerialNo()));
        raceDetailsListbox.setModel(raceDetailListModelList);
    }

    //when user clicks the delete button of each race on the list
    @Listen("onRaceDelete = #raceListbox")
    public void doRaceDelete(ForwardEvent evt){
        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem listitem = (Listitem)btn.getParent().getParent();

        race = listitem.getValue();
        System.out.println(">>>>>>>>> Race to remove:"+race.getRaceId());
        //raceService.deleteRace(race);
        //raceListModelList.addAll(raceService.getRaceList());
        //raceListbox.setModel(raceListModelList);

        raceListModelList.remove(race);
        raceDetailListModelList.clear();

        //ERROR
        //failed to lazily initialize a collection of role: org.zkoss.essentials.entity.Race.raceDetails, no session or session was closed
        //raceService.deleteRace(race);

    }

}

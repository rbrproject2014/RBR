package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.Race;
import org.zkoss.essentials.entity.RaceDetail;
import org.zkoss.essentials.services.ChitService;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import org.zkoss.zul.Button;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: suhand
 * Date: 12/28/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class raceEntryController extends SelectorComposer<Component>{
    private static final long serialVersionUID = 1L;
    Race race = new Race();
    RaceDetail raceDetail = new RaceDetail();

    //wire components
    @Wire
    Combobox combo;
    @Wire
    Button addRaceDetail;
    @Wire
    Listbox horseListbox;
    @Wire
    Component selectedHorseBlock;
    @Wire
    Textbox selectedHorseID;
    @Wire
    Textbox selectedHorseTrainer;
    @Wire
    Textbox selectedHorseJockey;
//    @Wire
//    Textbox selectedHorseDescription;
    @Wire
    Button newRace;
    @Wire
    Button saveRace;
    @Wire
    Button newRaceDetail;
    @Wire
    Button saveRaceDetail;
    @Wire
    Listbox raceListbox;
    @Wire
    Listbox raceDetailsListbox;
    @Wire
    Intbox noOfHorsesInput;
    @Wire
    Intbox onePlaceAmountInput;
    @Wire
    Intbox twoPlaceAmountInput;
    @Wire
    Intbox threePlaceAmountInput;
    @Wire
    Intbox fourPlaceAmountInput;
    @Wire
    Intbox winAmountInput;
    @Wire
    Textbox raceIdInput;
    @Wire
    Textbox meetingPlaceInput;
    @Wire
    Datebox dateInput;

    @Wire
    Textbox horseIdInput;
    @Wire
    Textbox jockeyInput;
    @Wire
    Textbox trainerInput;
    @Wire
    Intbox resultPositionInput;
    @Wire
    Intbox drawInput;

    @WireVariable
    RaceService raceService;

    //data for the view
    List<RaceDetail> raceDetails = new ArrayList<RaceDetail>();
    ListModelList<RaceDetail> blankHorseListModel;
    ListModelList<RaceDetail> selectedHorseListModel;
    RaceDetail selectedRaceDetail;

    ListModelList<Race> raceListModelList;
    ListModelList<RaceDetail> raceDetailListModelList;
    private boolean raceSaved = false;
    private boolean raceDetailSaved = false;

    ListModelList<ChitCombination> listModelListCombinations = new ListModelList<ChitCombination>();

    private org.zkoss.zul.Window win;

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);
        raceListModelList = new ListModelList<Race>();
        raceListModelList.setMultiple(true);
        raceDetailListModelList = new ListModelList<RaceDetail>();
        raceDetailListModelList.setMultiple(true);
        System.out.println(raceService == null ? "Race Service is null ************************" : "Race Service is not null***************");
        raceListModelList.addAll(raceService.getRaceList());
        raceListbox.setModel(raceListModelList);

        newRace.setDisabled(true);
        saveRace.setDisabled(true);
        newRaceDetail.setDisabled(true);
        saveRaceDetail.setDisabled(true);
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
        raceService.deleteRace(race);
        Clients.showNotification("Race successfully removed ", null, null, null, 2000);

    }
    //when user clicks the delete button of each race detail on the list
    @Listen("onRaceDetailDelete = #raceDetailsListbox")
    public void doRaceDetailDelete(ForwardEvent evt){
        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem listitem = (Listitem)btn.getParent().getParent();

        raceDetail = listitem.getValue();
        System.out.println(">>>>>>>>> Race Detail to remove:"+raceDetail.getHorseId());
        //raceService.deleteRace(race);
        //raceListModelList.addAll(raceService.getRaceList());
        //raceListbox.setModel(raceListModelList);

        raceDetailListModelList.remove(raceDetail);

//      race = raceDetail.getRace();
        race = raceListModelList.getSelection().iterator().next();
        BigDecimal newNumberOfHorses = race.getNumberOfHorses().subtract(new BigDecimal(1));
        race.setNumberOfHorses(newNumberOfHorses);
        //ERROR
        //failed to lazily initialize a collection of role: org.zkoss.essentials.entity.Race.raceDetails, no session or session was closed
        raceService.deleteRaceDetail(raceDetail);

        raceService.updateRace(race);
        raceListbox.setModel(raceListModelList);
        Clients.showNotification("Race Detail successfully removed ", null, null, null, 2000);

    }

    @Listen("onClick = #saveRace")
    public void saveRace(){
        race.setRaceId(raceIdInput.getValue());
        race.setMeetingPlace(meetingPlaceInput.getValue());
        race.setRaceDate(dateInput.getValue());
        race.setRaceTime(new Timestamp(dateInput.getValue().getTime()));
        race.setNumberOfHorses(new BigDecimal(noOfHorsesInput.getValue()));
        race.setFirstPlaceAmount(new BigDecimal(onePlaceAmountInput.getValue()));
        race.setSecondPlaceAmount(new BigDecimal(twoPlaceAmountInput.getValue()));
        race.setThirdPlaceAmount(new BigDecimal(threePlaceAmountInput.getValue()));
        race.setFourthPlaceAmount(new BigDecimal(fourPlaceAmountInput.getValue()));
        race.setWinnersWinAmount(new BigDecimal(winAmountInput.getValue()));
        raceService.saveRace(race);
        saveRace.setDisabled(true);
        raceListModelList.add(race);
        raceSaved = true;
        Clients.showNotification("Race successfully saved ", null, null, null, 2000);

    }

    @Listen("onChanging = #raceIdInput")
    public void enableSaveRaceButton(){
        newRace.setDisabled(false);
        saveRace.setDisabled(false);
    }

    @Listen("onClick = #newRace")
    public void NewRace(){
        if (!raceSaved) {
            Messagebox.show("Current Race is not saved. Are you sure you want to discard it?",new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO},new org.zkoss.zk.ui.event.EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent clickEvent) throws Exception {
                    if(Messagebox.ON_YES == clickEvent.getName()){
                        Executions.sendRedirect("//rbr/races/raceEntry-mvc.zul");
                    }
                    else if (Messagebox.ON_NO == clickEvent.getName()){
                        return;
                    }
                }
            });
        }
        else{
            Executions.sendRedirect("//rbr/races/raceEntry-mvc.zul");
        }

    }

    @Listen("onClick = #saveRaceDetail")
    public void saveRaceDetail(){

        raceDetail.setHorseId(horseIdInput.getValue());
        raceDetail.setJockey(jockeyInput.getValue());
        raceDetail.setTrainer(trainerInput.getValue());
        raceDetail.setResultPosition(new BigDecimal(resultPositionInput.getValue()));
        raceDetail.setDraw(new BigDecimal(drawInput.getValue()));

        race = raceListModelList.getSelection().iterator().next();
        BigDecimal newNumberOfHorses = race.getNumberOfHorses().add(new BigDecimal(1));
        race.setNumberOfHorses(newNumberOfHorses);
        raceListbox.setModel(raceListModelList);

        //System.out.println("$$$$$$$$$$$$$$$$$$$$ Parent Race: "+race.getRaceId());
        raceDetail.setRace(race);
        raceService.updateRace(race);
        raceService.saveRaceDetail(raceDetail);

        saveRaceDetail.setDisabled(true);

        raceDetailListModelList.add(raceDetail);

        raceDetailSaved = true;
        Clients.showNotification("Race Detail successfully saved ", null, null, null, 2000);

    }

    @Listen("onChanging = #horseIdInput")
    public void enableSaveRaceDetailButton(){
        if(raceListModelList.getSelection().isEmpty()){
            Clients.showNotification("Please select Race to add Race Detail", null, null, null, 2000);
            horseIdInput.setValue(null);
            horseIdInput.setFocus(true);
            return;
        }
        newRaceDetail.setDisabled(false);
        saveRaceDetail.setDisabled(false);
    }

    @Listen("onClick = #newRaceDetail")
    public void newRaceDetail(){
        if (!raceDetailSaved) {
            Messagebox.show("Current Race Detail is not saved. Are you sure you want to discard it?",new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO},new org.zkoss.zk.ui.event.EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent clickEvent) throws Exception {
                    if(Messagebox.ON_YES == clickEvent.getName()){
                        Executions.sendRedirect("//rbr/races/raceEntry-mvc.zul");
                    }
                    else if (Messagebox.ON_NO == clickEvent.getName()){
                        return;
                    }
                }
            });
        }
        else{
            Executions.sendRedirect("//rbr/races/raceEntry-mvc.zul");
        }

    }

}

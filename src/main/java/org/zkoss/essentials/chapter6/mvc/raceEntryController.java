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

    @WireVariable
    ChitService chitService;

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
    public void enableSaveUserButton(){
        newRace.setDisabled(false);
        saveRace.setDisabled(false);
    }

    @Listen("onClick = #newRace")
    public void NewUser(){
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

    //when user selects a horse of the listbox
    @Listen("onSelect = #horseListbox")
    public void doHorseSelect() {
        if(blankHorseListModel.isSelectionEmpty()){
            //just in case for the no selection
            selectedRaceDetail = null;
        }else{
            selectedRaceDetail = blankHorseListModel.getSelection().iterator().next();
        }
        refreshDetailView();
    }

    //when user clicks the update button
    @Listen("onClick = #saveRaceDetail")
    public void doUpdateClick(){
//        int Age = 0;
//
//        selectedHorse.setComplete(selectedHorseCheck.isChecked());
//        selectedHorse.setTrainer(selectedHorseTrainer.getValue());
//        selectedHorse.setJockey(selectedHorseJockey.getValue());
//        selectedHorse.setAge(selectedHorseAge.getValue());
//        selectedHorse.setDescription(selectedHorseDescription.getValue());
//
//        //save data and get updated Horse object
//        selectedHorse = horseService.updateHorse(selectedHorse);
//
//        //replace original Horse object in listmodel with updated one
//        horseListModel.set(horseListModel.indexOf(selectedHorse), selectedHorse);
//
//        //testing MySQL db write
//        //this.submit();
////        horseService.saveHorseDB(selectedHorseSubject.getValue().isEmpty()?null:selectedHorseSubject.getValue(),
////                selectedHorseTrainer.getValue().isEmpty()?null:selectedHorseTrainer.getValue(),
////                selectedHorseJockey.getValue().isEmpty()?null:selectedHorseJockey.getValue(),
////                selectedHorseAge.intValue(),
////                selectedHorseDescription.getValue().isEmpty()?null:selectedHorseDescription.getValue()
////                );
//        //chit detail successful method show user
//        Clients.showNotification("Chit detail saved");
    }

    private void refreshDetailView() {
        //refresh the detail view of selected
        if(selectedRaceDetail==null){
            //clean
            selectedHorseBlock.setVisible(false);
            //selectedHorseCheck.setChecked(false);
            selectedHorseID.setValue(null);
            selectedHorseTrainer.setValue(null);
            selectedHorseJockey.setValue(null);
            //selectedHorseAge.setValue(null);
            //selectedHorseDescription.setValue(null);

        }else{
            selectedHorseBlock.setVisible(true);
            //selectedHorseCheck.setChecked(selectedHorse.isComplete());
            selectedHorseID.setValue(selectedRaceDetail.getHorseId());
            selectedHorseTrainer.setValue(selectedRaceDetail.getTrainer());
            selectedHorseJockey.setValue(selectedRaceDetail.getJockey());
            //selectedHorseAge.setValue(selectedHorse.getAge().toString());
            //selectedHorseDescription.setValue(selectedRaceDetail.getDescription());

        }
    }

}

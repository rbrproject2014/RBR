package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.RaceDetail;
import org.zkoss.essentials.services.ChitService;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;
import org.zkoss.zul.Button;

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
    Button saveRaceDetail;

    @WireVariable
    ChitService chitService;

    @WireVariable
    RaceService raceService;

    //data for the view
    List<RaceDetail> raceDetails = new ArrayList<RaceDetail>();
    ListModelList<RaceDetail> blankHorseListModel;
    ListModelList<RaceDetail> selectedHorseListModel;
    RaceDetail selectedRaceDetail;
    private boolean chitSaved = false;

    ListModelList<ChitCombination> listModelListCombinations = new ListModelList<ChitCombination>();

    private org.zkoss.zul.Window win;

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);
        System.out.println(raceService==null?"Raceservice null":"RaceService not null");
        blankHorseListModel = new ListModelList<RaceDetail>(raceService.getRaceDetailListByRaceDate(new Date()));
        blankHorseListModel.setMultiple(true);
        horseListbox.setModel(blankHorseListModel);
        combo.setModel(new ListModelList<RaceDetail>());
    }

    //when user clicks on the button or enters on the comboBox
    @Listen("onClick = #addRaceDetail; onOK = #combo")
    public void addRaceDetail(){
        //get user input from view
        if (!"".equalsIgnoreCase(combo.getValue().toString())) {
            RaceDetail raceDetail = new RaceDetail();
            raceDetail.setHorseId(combo.getValue().toString());
            blankHorseListModel.add(raceDetail);
            horseListbox.setModel(blankHorseListModel);
            //refresh detail view
            refreshDetailView();
            combo.setValue("");
        }else{
            return;
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
            saveRaceDetail.setDisabled(true);

        }else{
            selectedHorseBlock.setVisible(true);
            //selectedHorseCheck.setChecked(selectedHorse.isComplete());
            selectedHorseID.setValue(selectedRaceDetail.getHorseId());
            selectedHorseTrainer.setValue(selectedRaceDetail.getTrainer());
            selectedHorseJockey.setValue(selectedRaceDetail.getJockey());
            //selectedHorseAge.setValue(selectedHorse.getAge().toString());
            //selectedHorseDescription.setValue(selectedRaceDetail.getDescription());
            saveRaceDetail.setDisabled(false);

        }
    }

}

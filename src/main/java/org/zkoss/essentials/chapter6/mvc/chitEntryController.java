package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.entity.Chit;
import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.ChitCombinationDetail;
import org.zkoss.essentials.entity.RaceDetail;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.essentials.services.impl.ChitServiceImpl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sashika
 * Date: 12/1/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class chitEntryController extends SelectorComposer<Component>{
    Chit chit = new Chit();
    List<RaceDetail> raceDetailList;

    private int iWinAmount;
    private int iPlaceAmount;

    //wire components
    @Wire
    Combobox combo;
    @Wire
    Listbox emptyHorseListbox;
    @Wire
    Listbox selectedHorseListbox;
    @Wire
    private Label selectedText;
    @Wire
    Window modalDialog;

    @Wire
    Intbox winInput;
    @Wire
    Intbox placeInput;
    @Wire
    Listbox combinationsListbox;



    //data for the view
    List<RaceDetail> raceDetails = new ArrayList<RaceDetail>();
    ListModelList<RaceDetail> blankHorseListModel;
    ListModelList<RaceDetail> selectedHorseListModel;
    ListModelList<RaceDetail> listModelList;
    RaceDetail selectedRaceDetail;

    ListModelList<ChitCombination> listModelListCombinations = new ListModelList<ChitCombination>();

    private Window win;

    @WireVariable
    ChitServiceImpl chitService;

    @WireVariable
    RaceService raceService;



    public void init(){

        chit.setChitCentreNo(new BigDecimal(252362462));
        chit.setChitSerialNo("35273517");
        chit.setChitValue(new BigDecimal(230));

    }

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);
        blankHorseListModel = new ListModelList<RaceDetail>();
        blankHorseListModel.setMultiple(true);
        selectedHorseListModel = new ListModelList<RaceDetail>();
        selectedHorseListModel.setMultiple(true);
        System.out.println(raceService==null?"Raceservice null":"RaceService not null");
        if (combo!=null) {
            combo.setModel(new ListModelList<RaceDetail>(raceService.getRaceDetailListByRaceDate(new Date())));
        }
    }

    /*
    * After entering value in the place input value OR
    * upon pressing the addWindPlaceInfo button,
    * process chit combination information
    * Two separate records will be added per combination,
    * one for Win value and one for Place value
    * */
    @Listen("onClick = #addWinPlaceInfo; onOK = #placeInput")
    public void processChitCombination(){

        Set<Listitem> selectedRaceDetails = emptyHorseListbox.getSelectedItems();

        if(placeInput.getValue()!=null){
            ChitCombination chitCombination = new ChitCombination();
            chitCombination.setCombinedElements(new BigDecimal(selectedRaceDetails.size()));
            chitCombination.setBetValue(new BigDecimal(placeInput.intValue()));
            chitCombination.setWinPlace("P");
            System.out.println("Chit combination (P) Combined elements:" + selectedRaceDetails.size() + " Value:" + placeInput.intValue());
            addCombinationDetails(chitCombination, selectedRaceDetails);
            chit.addChitCombinatiion(chitCombination);
            listModelListCombinations.add(chitCombination);
        }

        if(winInput.getValue()!=null){
            ChitCombination chitCombination = new ChitCombination();
            chitCombination.setCombinedElements(new BigDecimal(selectedRaceDetails.size()));
            chitCombination.setBetValue(new BigDecimal(winInput.intValue()));
            chitCombination.setWinPlace("W");
            System.out.println("Chit combination (W) Combined elements:" + selectedRaceDetails.size() + " Value:" + winInput.intValue());
            addCombinationDetails(chitCombination, selectedRaceDetails);
            chit.addChitCombinatiion(chitCombination);
            listModelListCombinations.add(chitCombination);
        }

        combinationsListbox.setModel(listModelListCombinations);

        emptyHorseListbox.clearSelection();
        winInput.setValue(null);
        placeInput.setValue(null);
        emptyHorseListbox.setFocus(true);
        Clients.showNotification("Chit combination added");
//        chit.setChitCentreNo(new BigDecimal(1));
//        chit.setChitSerialNo("AAA001");
//        chit.setChitValue(new BigDecimal(100));
//        chit.setChitDate(new Date());
//        chitService.saveChit(chit);
}

    private void addCombinationDetails(ChitCombination chitCombination,Set<Listitem> selectedRaceDetails){
        Iterator<Listitem> iterator = selectedRaceDetails.iterator();
        while(iterator.hasNext()){
            RaceDetail raceDetail = iterator.next().getValue();
            System.out.println("Combination detail Race detail:"+raceDetail.getHorseId());
            ChitCombinationDetail chitCombinationDetail = new ChitCombinationDetail(raceDetail);
            chitCombination.addCombinationDetail(chitCombinationDetail);
        }
    }

    public void saveChit(){

        ChitServiceImpl chitServiceImpl = new ChitServiceImpl();
        chitServiceImpl.saveChit(chit);
    }

    //when user click Add(plus) sign of race detail or upon pressing enter
    @Listen("onClick = #addHorseBet; onOK = #combo")
    public void addRaceDetailClickOREnter(){
        System.out.println("Clicked...");

        if (combo.getSelectedItem()!=null) {   // not working redo
            RaceDetail raceDetail = combo.getSelectedItem().getValue();
            System.out.println(">>>>>>>>> Horse ID:"+raceDetail.getHorseId());
            raceDetails.add(raceDetail);
            listModelList = new ListModelList<RaceDetail>(raceDetails);
            listModelList.setMultiple(true);
            emptyHorseListbox.setModel(listModelList);
            combo.setValue("");
        }else if(emptyHorseListbox.getModel().getSize()>0){
            emptyHorseListbox.setFocus(true);
        }
    }

    //when user selects the check box of Race Detail list
    @Listen("onSelect = #emptyHorseListbox")
    public void raceDetailSelectCheckBox() {
       selectedText.setValue(emptyHorseListbox.getSelectedItems().size() + " horse(s) selected");
    }

    //when user clicks the palce bet button
//    @Listen("onClick = #addWinPlaceInfo")
//    public void doAddWinPlaceClick() {
//        //Set<Listitem> selectedList = horseListbox.getSelectedItems();
//        Set<Listitem> selectedList = emptyHorseListbox.getSelectedItems();
//        if (selectedList.isEmpty()) {
//            alert("Please select at least one Race Detail to enter bet information");
//            return;
//        }
//        //System.out.println(selectedHorseListModel.isEmpty()?"selectedHorseListModel is empty":"-- not empty -- ");
//        //selectedHorseListbox.setModel(selectedHorseListModel);
//        final Window dialog = (Window) Executions.createComponents("betwindow.zul", win, null);
//        dialog.doModal();
//    }

//    @Listen("onClick = #popupSaveButton")
//    public void showModal1(Event e) {
//          Intbox ibWin = (Intbox) modalDialog.getFellow("winInput");
//          Intbox ibPlc = (Intbox) modalDialog.getFellow("placeInput");
//          iWinAmount = ibWin.getValue();
//          iPlaceAmount = ibPlc.getValue();
//          System.out.println(" --------- Win Amount: "+iWinAmount);
//          System.out.println(" --------- Place Amount: "+iPlaceAmount);
//          System.out.println(" --------- BetSize: ");        //error here
//          modalDialog.detach();
//    }
}

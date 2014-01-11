package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.entity.Chit;
import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.ChitCombinationDetail;
import org.zkoss.essentials.services.ChitService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: suhan
 * Date: 1/10/14
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class chitViewController extends SelectorComposer<Component>{
    //Chit chit = new Chit();
    //List<RaceDetail> raceDetailList;

//    private int iWinAmount;
//    private int iPlaceAmount;
//
//    //wire components
//    @Wire
//    Combobox combo;
    @Wire
    Listbox chitListbox;
    @Wire
    Listbox chitCombinationsListbox;
    @Wire
    Listbox chitCombinationsDetailsListbox;
//    @Wire
//    Listbox selectedHorseListbox;
//    @Wire
//    private Label selectedText;
//    @Wire
//    Window modalDialog;
//
//    @Wire
//    Button newChit;
//    @Wire
//    Button saveChit;
//
//    @Wire
//    Intbox winInput;
//    @Wire
//    Intbox placeInput;
//    @Wire
//    Intbox serialNumberInput;
//    @Wire
//    Intbox centreNumberInput;
//    @Wire
//    Intbox chitValueInput;
//
//    @Wire
//    Listbox combinationsListbox;
//
//
//
    //data for the view
    List<Chit> chits = new ArrayList<Chit>();
    List<ChitCombination> chitCombinations = new ArrayList<ChitCombination>();
    List<ChitCombinationDetail> chitCombinationDetails = new ArrayList<ChitCombinationDetail>();
    ListModelList<Chit> chitListModelList;
    ListModelList<ChitCombination> chitCombinationListModelList;
    ListModelList<ChitCombinationDetail> chitCombinationDetailListModelList;
    Chit chit;
    ChitCombination chitCombination;
    ChitCombinationDetail chitCombinationDetail;

//    private boolean chitSaved = false;
//
//    ListModelList<ChitCombination> listModelListCombinations = new ListModelList<ChitCombination>();
//
//    private Window win;
//
    @WireVariable
    ChitService chitService;
//
//    @WireVariable
//    RaceService raceService;
//
    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);
        chitListModelList = new ListModelList<Chit>();
        chitListModelList.setMultiple(true);
        chitCombinationListModelList = new ListModelList<ChitCombination>();
        chitCombinationListModelList.setMultiple(true);
        chitCombinationDetailListModelList = new ListModelList<ChitCombinationDetail>();
        chitCombinationDetailListModelList.setMultiple(true);
        System.out.println(chitService==null?"Service is null ************************":"Service isnot null***************");
        chitListModelList.addAll(chitService.getAllChits());
        chitListbox.setModel(chitListModelList);
    }

    //when user select one row from the Chit list
    @Listen("onSelect = #chitListbox")
    public void onChitRowSelect() {
        chitCombinationListModelList.clear();
        //System.out.println("Chit row selected");
        chitCombinationListModelList.addAll(chitService.getAllChitCombinationsByChitID(chitListModelList.getSelection().iterator().next().getId()));
        chitCombinationsListbox.setModel(chitCombinationListModelList);
    }

    //when user select one row from the Chit combination list
    @Listen("onSelect = #chitCombinationsListbox")
    public void onChitCombinationRowSelect() {
        chitCombinationDetailListModelList.clear();
        //System.out.println("Chit row selected");
        chitCombinationDetailListModelList.addAll(chitService.getAllChitCombinationDetailsByChitCombinationID(chitCombinationListModelList.getSelection().iterator().next().getId()));
        chitCombinationsDetailsListbox.setModel(chitCombinationDetailListModelList);
    }
//
//    /*
//    * After entering value in the place input value OR
//    * upon pressing the addWindPlaceInfo button,
//    * process chit combination information
//    * Two separate records will be added per combination,
//    * one for Win value and one for Place value
//    * */
//    @Listen("onClick = #addWinPlaceInfo; onOK = #placeInput")
//    public void processChitCombination(){
//
//        Set<Listitem> selectedRaceDetails = emptyHorseListbox.getSelectedItems();
//
//        if(placeInput.getValue()!=null){
//            ChitCombination chitCombination = new ChitCombination();
//            chitCombination.setCombinedElements(new BigDecimal(selectedRaceDetails.size()));
//            chitCombination.setBetValue(new BigDecimal(placeInput.intValue()));
//            chitCombination.setWinPlace("P");
//            System.out.println("Chit combination (P) Combined elements:" + selectedRaceDetails.size() + " Value:" + placeInput.intValue());
//            addCombinationDetails(chitCombination, selectedRaceDetails);
//            chit.addChitCombinatiion(chitCombination);
//            listModelListCombinations.add(chitCombination);
//        }
//
//        if(winInput.getValue()!=null){
//            ChitCombination chitCombination = new ChitCombination();
//            chitCombination.setCombinedElements(new BigDecimal(selectedRaceDetails.size()));
//            chitCombination.setBetValue(new BigDecimal(winInput.intValue()));
//            chitCombination.setWinPlace("W");
//            System.out.println("Chit combination (W) Combined elements:" + selectedRaceDetails.size() + " Value:" + winInput.intValue());
//            addCombinationDetails(chitCombination, selectedRaceDetails);
//            chit.addChitCombinatiion(chitCombination);
//            listModelListCombinations.add(chitCombination);
//        }
//
//        combinationsListbox.setModel(listModelListCombinations);
//
//        emptyHorseListbox.clearSelection();
//        winInput.setValue(null);
//        placeInput.setValue(null);
//        selectedText.setValue("");
//        emptyHorseListbox.setFocus(true);
//
//        newChit.setDisabled(false);
//        saveChit.setDisabled(false);
//        Clients.showNotification("Chit combination added",null,null,null,2000);
//}
//
//    private void addCombinationDetails(ChitCombination chitCombination,Set<Listitem> selectedRaceDetails){
//        Iterator<Listitem> iterator = selectedRaceDetails.iterator();
//        while(iterator.hasNext()){
//            RaceDetail raceDetail = iterator.next().getValue();
//            System.out.println("Combination detail Race detail:"+raceDetail.getHorseId());
//            ChitCombinationDetail chitCombinationDetail = new ChitCombinationDetail(raceDetail);
//            chitCombination.addCombinationDetail(chitCombinationDetail);
//        }
//    }
//
//
//    //when user click Add(plus) sign of race detail or upon pressing enter
//    @Listen("onClick = #addHorseBet; onOK = #combo")
//    public void addRaceDetailClickOREnter(){
//        System.out.println("Clicked...");
//
//        System.out.println(emptyHorseListbox.getModel()!=null?"list box is not null":"list box is null");
//
//        if (combo.getSelectedItem()!=null) {
//            RaceDetail raceDetail = combo.getSelectedItem().getValue();
//            System.out.println(">>>>>>>>> Horse ID:"+raceDetail.getHorseId());
//            raceDetails.add(raceDetail);
//            listModelList = new ListModelList<RaceDetail>(raceDetails);
//            listModelList.setMultiple(true);
//            emptyHorseListbox.setModel(listModelList);
//            combo.removeItemAt(combo.getSelectedIndex());
//            combo.setValue("");
////        }else if (combo.getSelectedItem()==null){
////            Clients.showNotification("Race detail entered does not exist");
//        }else if(emptyHorseListbox.getModel().getSize()>0){
//            emptyHorseListbox.setFocus(true);
//        }
//    }
//
//
//    //when user clicks the palce bet button
////    @Listen("onClick = #addWinPlaceInfo")
////    public void doAddWinPlaceClick() {
////        //Set<Listitem> selectedList = horseListbox.getSelectedItems();
////        Set<Listitem> selectedList = emptyHorseListbox.getSelectedItems();
////        if (selectedList.isEmpty()) {
////            alert("Please select at least one Race Detail to enter bet information");
////            return;
////        }
////        //System.out.println(selectedHorseListModel.isEmpty()?"selectedHorseListModel is empty":"-- not empty -- ");
////        //selectedHorseListbox.setModel(selectedHorseListModel);
////        final Window dialog = (Window) Executions.createComponents("betwindow.zul", win, null);
////        dialog.doModal();
////    }
//
////    @Listen("onClick = #popupSaveButton")
////    public void showModal1(Event e) {
////          Intbox ibWin = (Intbox) modalDialog.getFellow("winInput");
////          Intbox ibPlc = (Intbox) modalDialog.getFellow("placeInput");
////          iWinAmount = ibWin.getValue();
////          iPlaceAmount = ibPlc.getValue();
////          System.out.println(" --------- Win Amount: "+iWinAmount);
////          System.out.println(" --------- Place Amount: "+iPlaceAmount);
////          System.out.println(" --------- BetSize: ");        //error here
////          modalDialog.detach();
////    }
//
//
//    //when user clicks the delete button of each race detail on the list
//    @Listen("onHorseDelete = #emptyHorseListbox")
//    public void doChitCombinationDetailDelete(ForwardEvent evt){
//        Button btn = (Button)evt.getOrigin().getTarget();
//        Listitem listitem = (Listitem)btn.getParent().getParent();
//
//        RaceDetail raceDetail = listitem.getValue();
//        raceDetails.remove(raceDetail);
//        listModelList = new ListModelList<RaceDetail>(raceDetails);
//        listModelList.setMultiple(true);
//        emptyHorseListbox.setModel(listModelList);
//        System.out.println(">>>>>>>>> Horse ID to remove:"+raceDetail.getHorseId());
//
//    }
//
//    @Listen("onClick = #saveChit")
//    public void saveChit(){
//        chit.setChitCentreNo(new BigDecimal(centreNumberInput.getValue()));
//        chit.setChitSerialNo(serialNumberInput.getValue().toString());
//        chit.setChitValue(new BigDecimal(chitValueInput.getValue()));
//        chit.setChitDate(new Date());
//        chitService.saveChit(chit);
//        saveChit.setDisabled(true);
//        chitSaved = true;
//        Clients.showNotification("Chit successfully saved ",null,null,null,2000);
//
//    }
//
//    @Listen("onClick = #newChit")
//    public void newChit(){
//        if (!chitSaved) {
//            Messagebox.show("Current chit is not saved. Are you sure you want to discard it?",new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO},new EventListener<Messagebox.ClickEvent>() {
//                @Override
//                public void onEvent(Messagebox.ClickEvent clickEvent) throws Exception {
//                    if(Messagebox.ON_YES == clickEvent.getName()){
//                        Executions.sendRedirect("/chapter6/indexChit.zul");
//                    }
//                    else if (Messagebox.ON_NO == clickEvent.getName()){
//                        return;
//                    }
//                }
//            });
//        }
//        else{
//            Executions.sendRedirect("/chapter6/indexChit.zul");
//        }
//
//    }
}

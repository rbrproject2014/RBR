package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.services.ChitService;
import org.zkoss.essentials.entity.Chit;
import org.zkoss.essentials.entity.ChitCombination;
import org.zkoss.essentials.entity.ChitCombinationDetail;
import org.zkoss.essentials.entity.RaceDetail;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
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
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
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
    Button newChit;
    @Wire
    Button saveChit;

    @Wire
    Intbox winInput;
    @Wire
    Intbox placeInput;
    @Wire
    Intbox serialNumberInput;
    @Wire
    Intbox centreNumberInput;
    @Wire
    Intbox chitValueInput;

    @Wire
    Listbox combinationsListbox;
    @Wire
    Listbox combinationsViewDetListbox;



    //data for the view
    List<RaceDetail> raceDetails = new ArrayList<RaceDetail>();
    ListModelList<RaceDetail> blankHorseListModel;
    ListModelList<RaceDetail> selectedHorseListModel;
    ListModelList<RaceDetail> listModelList;
    RaceDetail selectedRaceDetail;
    private boolean chitSaved = false;

    ListModelList<ChitCombination> listModelListCombinations = new ListModelList<ChitCombination>();
    ListModelList<ChitCombinationDetail> listModelListCombinationDetails = new ListModelList<ChitCombinationDetail>();
    ListModelList<RaceDetail> raceDetailsSetToBeRemoved = new ListModelList<RaceDetail>();
    ListModelList<RaceDetail> raceDetailsToLOV = new ListModelList<RaceDetail>();

    private Window win;

    @WireVariable
    ChitService chitService;

    @WireVariable
    RaceService raceService;

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


        newChit.setDisabled(true);
        saveChit.setDisabled(true);
        //centreNumberInput.setFocus(true);  this is removed since at page load and after clicking anywhere in the page error shows
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
        selectedText.setValue("");
        emptyHorseListbox.setFocus(true);

        newChit.setDisabled(false);
        saveChit.setDisabled(false);
        Clients.showNotification("Chit combination added",null,null,null,2000);
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


    //when user click Add(plus) sign of race detail or upon pressing keyboard Enter button
    @Listen("onClick = #addHorseBet; onOK = #combo")
    public void addRaceDetailClickOREnter(){

        //DEBUG CODE
        //System.out.println("Clicked...");
        //System.out.println(emptyHorseListbox.getModel()!=null?"list box is not null":"list box is null");

        //When there is a selection in the LOV
        if (combo.getSelectedItem()!=null) {

            //PART 1 - ADD THE RACE DETAIL TO THE SELECTED LIST
            //get the selected Race detail from the LOV
            RaceDetail raceDetail = combo.getSelectedItem().getValue();
            System.out.println(">>>>>>>>> LOV selected Horse ID:"+raceDetail.getHorseId());

            //add the selected LOV item to the race details list
            raceDetails.add(raceDetail);

            //add the list to a ListModelList
            listModelList = new ListModelList<RaceDetail>(raceDetails);
            listModelList.setMultiple(true);

            //set the ListModelList to the selected horse detail list grid
            emptyHorseListbox.setModel(listModelList);

            //PART 2 - REMOVE THE CORRESPONDING RACE DETAILS FROM THE LOV

            //LEGACY METHOD
            //primitive, only removes the selected race detail from the LOV
            //combo.removeItemAt(combo.getSelectedIndex());

            //NEW METHOD
            //set the model to the combo LOV
            combo.setModel(getNewRaceDetailLOV());

            //initialize LOV field to make ready for the next selection
            combo.setValue("");

        //When the LOV selection is Null
        }else{
            //If there are no race details in the selected race detail list, cannot proceed further
            //Therefore inform User to add race detail
            if (emptyHorseListbox.getModel()==null){
                Clients.showNotification("Please select race detail to add");
            }
            //If there is at least one selection for the selected Horse list,
            //proceed to the next step
            else {
                emptyHorseListbox.setFocus(true);
            }
        }
    }

    //this method is generalized in order to use in both adding and deleting race detail
    public ListModelList<RaceDetail> getNewRaceDetailLOV(){

        //NEW METHOD
        //Step 1 - Get total race detail list per day and put to ListModelList
        ListModelList<RaceDetail> raceDetailsToLOV = new ListModelList<RaceDetail>(raceService.getRaceDetailListByRaceDate(new Date()));

        //Step 2 - define empty race detail ListModelList list to be used to remove race detail
        // set per Race under the given/selected race detail and loop for each selected detail list

        //Detailed explanation:
        //    User clicks/selects a race detail and add to the bet detail table.
        //    But we cannot allow the user to select another race detail from the same Race.
        //    Therefore we need to remove the rest of the race details from the ListOfValue combo
        //    under the same Race, upon selecting a race detail
        //moved up to class level
        //ListModelList<RaceDetail> raceDetailsSetToBeRemoved = new ListModelList<RaceDetail>();

        //Define the iterator for the already selected race detail list ListModelList
        Iterator<RaceDetail> iterator = raceDetails.iterator();

        //for Each race detail containing in the selected list, get the rest of the race details list
        //for the corresponding race and add to the toBeRemovedRaceDetail list
        //loop for each and every race detail in the corresponding selected race detail list and
        //build up the race detail list to be removed from the main LOV list

        //*************** drawbacks of this method *************************************************
        //this can cause severe performance impact since backend calls are generated for each iterator.next
        //revisit the code
        while(iterator.hasNext()){
            //When want to test below output either one can be tested one at a time since iterator.next is executed
            // two times and can cause null pointer exception
            //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%% "+iterator.next().getHorseId());
            raceDetailsSetToBeRemoved.addAll(raceService.getToBeRemovedRaceDetailList(iterator.next()));
        }

        //Step 3 - Remove the race detail list relating to the selected race detail list under the given Race
        //from the race Details LOV
        raceDetailsToLOV.removeAll(raceDetailsSetToBeRemoved);

        return raceDetailsToLOV;

    }



    //when user selects the check box of Race Detail list
    //this method is no functional/business use, this is to add value to UI
    @Listen("onSelect = #emptyHorseListbox")
    public void raceDetailSelectCheckBox() {
       selectedText.setValue(emptyHorseListbox.getSelectedItems().size() + " horse(s) selected");
    }

    //when user selects the check box of Chit Combination list
    @Listen("onSelect = #combinationsListbox")
    public void chitCombinationSelectCheckBox() {
        //This is for the popup data population
        //When user clicks on a combination, corresponding combination details will be populated for this
        //ListModelListCombinationDetails model which is linked to the popup listBox
        listModelListCombinationDetails.clear();
        Iterator<ChitCombination> iterator = listModelListCombinations.getSelection().iterator();
        Iterator<ChitCombinationDetail> iterator1 = iterator.next().getChitCombinationDetails().iterator();
        while(iterator1.hasNext()){
            listModelListCombinationDetails.add(iterator1.next());
        }
        combinationsViewDetListbox.setModel(listModelListCombinationDetails);
    }


    //when user clicks the delete button of each race detail on the list
    @Listen("onHorseDelete = #emptyHorseListbox")
    public void doChitCombinationDetailDelete(ForwardEvent evt){
        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem listitem = (Listitem)btn.getParent().getParent();

        RaceDetail raceDetail = listitem.getValue();
        raceDetails.remove(raceDetail);
        listModelList = new ListModelList<RaceDetail>(raceDetails);
        listModelList.setMultiple(true);
        emptyHorseListbox.setModel(listModelList);

//        ListModelList<RaceDetail> raceDetailsLOV = new ListModelList<RaceDetail>(raceService.getRaceDetailListByRaceDate(new Date()));
//        raceDetailsLOV.removeAll(listModelList);
//        combo.setModel(raceDetailsLOV);
        combo.setModel(getNewRaceDetailLOV());
        System.out.println(">>>>>>>>> Horse ID to remove:"+raceDetail.getHorseId());

    }

    @Listen("onClick = #saveChit")
    public void saveChit(){
        chit.setChitCentreNo(new BigDecimal(centreNumberInput.getValue()));
        chit.setChitSerialNo(serialNumberInput.getValue().toString());
        chit.setChitValue(new BigDecimal(chitValueInput.getValue()));
        chit.setChitDate(new Date());
        chitService.saveChit(chit);
        saveChit.setDisabled(true);
        chitSaved = true;
        Clients.showNotification("Chit successfully saved ",null,null,null,2000);

    }

    @Listen("onClick = #newChit")
    public void newChit(){
        if (!chitSaved) {
            Messagebox.show("Current chit is not saved. Are you sure you want to discard it?",new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO},new EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent clickEvent) throws Exception {
                    if(Messagebox.ON_YES == clickEvent.getName()){
                        Executions.sendRedirect("//rbr/chits/chitEntry-mvc.zul");
                    }
                    else if (Messagebox.ON_NO == clickEvent.getName()){
                        return;
                    }
                }
            });
        }
        else{
            Executions.sendRedirect("//rbr/chits/chitEntry-mvc.zul");
        }

    }
}

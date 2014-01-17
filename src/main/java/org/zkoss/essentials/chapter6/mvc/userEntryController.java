package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.entity.*;
import org.zkoss.essentials.services.ChitService;
import org.zkoss.essentials.services.RaceService;
import org.zkoss.essentials.services.UserInfoService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
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
 * User: suhan
 * Date: 17/1/14
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class userEntryController extends SelectorComposer<Component>{
    User user = new User();

    //wire components
    @Wire
    Listbox userListbox;
    @Wire
    Listbox userViewDetailsListbox;

    @Wire
    Button newUser;
    @Wire
    Button saveUser;

    @Wire
    Textbox accountInput;
    @Wire
    Textbox passwordInput;
    @Wire
    Textbox fullNameInput;
    @Wire
    Textbox emailInput;
    @Wire
    Textbox roleInput;

    //data for the view
    List<User> users = new ArrayList<User>();
    ListModelList<User> userListModelList;
    private boolean userSaved = false;

    @WireVariable
    UserInfoService userInfoService;

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);
        userListModelList = new ListModelList<User>();
        userListModelList.setMultiple(true);
        System.out.println(userInfoService==null?"userInfoService null":"userInfoService not null");
        userListModelList.addAll(userInfoService.getAllUsers());
        userListbox.setModel(userListModelList);
        newUser.setDisabled(true);
        saveUser.setDisabled(true);
    }

    @Listen("onClick = #saveUser")
    public void saveUser(){
        user.setAccount(accountInput.getValue());
        user.setPassword(passwordInput.getValue());
        user.setFullName(fullNameInput.getValue());
        user.setEmail(emailInput.getValue());
        user.setRole(roleInput.getValue());
        userInfoService.saveUser(user);
        saveUser.setDisabled(true);
        userSaved = true;
        userListModelList.add(user);
        Clients.showNotification("User successfully saved ",null,null,null,2000);

    }

    @Listen("onClick = #newUser")
    public void NewUser(){
        if (!userSaved) {
            Messagebox.show("Current User is not saved. Are you sure you want to discard it?",new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO},new EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent clickEvent) throws Exception {
                    if(Messagebox.ON_YES == clickEvent.getName()){
                        Executions.sendRedirect("//rbr/admin/userEntry-mvc.zul");
                    }
                    else if (Messagebox.ON_NO == clickEvent.getName()){
                        return;
                    }
                }
            });
        }
        else{
            Executions.sendRedirect("//rbr/admin/userEntry-mvc.zul");
        }

    }

    @Listen("onChanging = #accountInput")
    public void enableSaveUserButton(){
        newUser.setDisabled(false);
        saveUser.setDisabled(false);
    }

    //when user clicks the delete button of each user on the list
    @Listen("onUserDelete = #userListbox")
    public void doChitCombinationDetailDelete(ForwardEvent evt){
        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem listitem = (Listitem)btn.getParent().getParent();
        user = listitem.getValue();
        userListModelList.remove(user);
        userInfoService.deleteUser(user);
        System.out.println(">>>>>>>>> User to remove:"+user.getFullName());

    }

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
//    //when user click Add(plus) sign of race detail or upon pressing keyboard Enter button
//    @Listen("onClick = #addHorseBet; onOK = #combo")
//    public void addRaceDetailClickOREnter(){
//
//        //DEBUG CODE
//        //System.out.println("Clicked...");
//        //System.out.println(emptyHorseListbox.getModel()!=null?"list box is not null":"list box is null");
//
//        //When there is a selection in the LOV
//        if (combo.getSelectedItem()!=null) {
//
//            //PART 1 - ADD THE RACE DETAIL TO THE SELECTED LIST
//            //get the selected Race detail from the LOV
//            RaceDetail raceDetail = combo.getSelectedItem().getValue();
//            System.out.println(">>>>>>>>> LOV selected Horse ID:"+raceDetail.getHorseId());
//
//            //add the selected LOV item to the race details list
//            raceDetails.add(raceDetail);
//
//            //add the list to a ListModelList
//            listModelList = new ListModelList<RaceDetail>(raceDetails);
//            listModelList.setMultiple(true);
//
//            //set the ListModelList to the selected horse detail list grid
//            emptyHorseListbox.setModel(listModelList);
//
//            //PART 2 - REMOVE THE CORRESPONDING RACE DETAILS FROM THE LOV
//
//            //LEGACY METHOD
//            //primitive, only removes the selected race detail from the LOV
//            //combo.removeItemAt(combo.getSelectedIndex());
//
//            //NEW METHOD
//            //set the model to the combo LOV
//            combo.setModel(getNewRaceDetailLOV());
//
//            //initialize LOV field to make ready for the next selection
//            combo.setValue("");
//
//        //When the LOV selection is Null
//        }else{
//            //If there are no race details in the selected race detail list, cannot proceed further
//            //Therefore inform User to add race detail
//            if (emptyHorseListbox.getModel()==null){
//                Clients.showNotification("Please select race detail to add");
//            }
//            //If there is at least one selection for the selected Horse list,
//            //proceed to the next step
//            else {
//                emptyHorseListbox.setFocus(true);
//            }
//        }
//    }
//
//    //this method is generalized in order to use in both adding and deleting race detail
//    public ListModelList<RaceDetail> getNewRaceDetailLOV(){
//
//        //NEW METHOD
//        //Step 1 - Get total race detail list per day and put to ListModelList
//        ListModelList<RaceDetail> raceDetailsToLOV = new ListModelList<RaceDetail>(raceService.getRaceDetailListByRaceDate(new Date()));
//
//        //Step 2 - define empty race detail ListModelList list to be used to remove race detail
//        // set per Race under the given/selected race detail and loop for each selected detail list
//
//        //Detailed explanation:
//        //    User clicks/selects a race detail and add to the bet detail table.
//        //    But we cannot allow the user to select another race detail from the same Race.
//        //    Therefore we need to remove the rest of the race details from the ListOfValue combo
//        //    under the same Race, upon selecting a race detail
//        //moved up to class level
//        //ListModelList<RaceDetail> raceDetailsSetToBeRemoved = new ListModelList<RaceDetail>();
//
//        //Define the iterator for the already selected race detail list ListModelList
//        Iterator<RaceDetail> iterator = raceDetails.iterator();
//
//        //for Each race detail containing in the selected list, get the rest of the race details list
//        //for the corresponding race and add to the toBeRemovedRaceDetail list
//        //loop for each and every race detail in the corresponding selected race detail list and
//        //build up the race detail list to be removed from the main LOV list
//
//        //*************** drawbacks of this method *************************************************
//        //this can cause severe performance impact since backend calls are generated for each iterator.next
//        //revisit the code
//        while(iterator.hasNext()){
//            //When want to test below output either one can be tested one at a time since iterator.next is executed
//            // two times and can cause null pointer exception
//            //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%% "+iterator.next().getHorseId());
//            raceDetailsSetToBeRemoved.addAll(raceService.getToBeRemovedRaceDetailList(iterator.next()));
//        }
//
//        //Step 3 - Remove the race detail list relating to the selected race detail list under the given Race
//        //from the race Details LOV
//        raceDetailsToLOV.removeAll(raceDetailsSetToBeRemoved);
//
//        return raceDetailsToLOV;
//
//    }
//
//
//
//    //when user selects the check box of Race Detail list
//    //this method is no functional/business use, this is to add value to UI
//    @Listen("onSelect = #emptyHorseListbox")
//    public void raceDetailSelectCheckBox() {
//       selectedText.setValue(emptyHorseListbox.getSelectedItems().size() + " horse(s) selected");
//    }
//
//    //when user selects the check box of Chit Combination list
//    @Listen("onSelect = #combinationsListbox")
//    public void chitCombinationSelectCheckBox() {
//        //This is for the popup data population
//        //When user clicks on a combination, corresponding combination details will be populated for this
//        //ListModelListCombinationDetails model which is linked to the popup listBox
//        listModelListCombinationDetails.clear();
//        Iterator<ChitCombination> iterator = listModelListCombinations.getSelection().iterator();
//        Iterator<ChitCombinationDetail> iterator1 = iterator.next().getChitCombinationDetails().iterator();
//        while(iterator1.hasNext()){
//            listModelListCombinationDetails.add(iterator1.next());
//        }
//        combinationsViewDetListbox.setModel(listModelListCombinationDetails);
//    }
//
//

//

//

}

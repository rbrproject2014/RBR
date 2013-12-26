package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.essentials.dao.ChitDao;
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

    //data for the view
    List<RaceDetail> raceDetails = new ArrayList<RaceDetail>();
    ListModelList<RaceDetail> blankHorseListModel;
    ListModelList<RaceDetail> selectedHorseListModel;
    ListModelList<RaceDetail> listModelList;
    RaceDetail selectedRaceDetail;

    private Window win;

    //services
    ChitServiceImpl chitService = new ChitServiceImpl();

    @WireVariable
    RaceService raceService;



    public void init(){

        chit.setChitCentreNo(new BigDecimal(252362462));
        chit.setChitSerialNo("35273517");
        chit.setChitValue(new BigDecimal(230));

    }

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        //System.out.println("11111111111111111111111111111>>>>>>>>>>>>>>>>>>>>");
        super.doAfterCompose(comp);
        blankHorseListModel = new ListModelList<RaceDetail>();
        blankHorseListModel.setMultiple(true);

        selectedHorseListModel = new ListModelList<RaceDetail>();
        selectedHorseListModel.setMultiple(true);

        //System.out.println("22222222222222222222222222222>>>>>>>>>>>>>>>>>>>>");
        System.out.println(raceService==null?"Raceservice null":"RaceService not null");
        if (combo!=null) {
            combo.setModel(new ListModelList<RaceDetail>(raceService.getRaceDetailListByRaceDate(new Date())));
        }

        //System.out.println("33333333333333333333333333333>>>>>>>>>>>>>>>>>>>>");
    }

    public void addCombination(){

        Set<Listitem> selectedRaceDetails = emptyHorseListbox.getSelectedItems();
        ChitCombination chitCombination = new ChitCombination();
        chitCombination.setCombinedElements(new BigDecimal(selectedRaceDetails.size()));
        chitCombination.setBetValue(new BigDecimal(100));
        chitCombination.setWinPlace("P");
        // two entries for chit combination for W and P

        Iterator<Listitem> iterator = selectedRaceDetails.iterator();
        while(iterator.hasNext()){
            RaceDetail raceDetail = iterator.next().getValue();
            ChitCombinationDetail chitCombinationDetail = new ChitCombinationDetail(raceDetail);
            chitCombination.addCombinationDetail(chitCombinationDetail);
        }

        chit.addChitCombinatiion(chitCombination);






    }

    public void saveChit(){

        ChitServiceImpl chitServiceImpl = new ChitServiceImpl();
        chitServiceImpl.saveChit(chit);
    }

    //when user click Add(plus) sign of race detail or upon pressing enter
    @Listen("onClick = #addHorseBet; onOK = #combo")
    public void addRaceDetailClickOREnter(){
        System.out.println("Clicked...");

        if (combo!=null) {   // not working redo
            RaceDetail raceDetail = combo.getSelectedItem().getValue();
            System.out.println(">>>>>>>>> Horse ID:"+raceDetail.getHorseId());
            raceDetails.add(raceDetail);
            listModelList = new ListModelList<RaceDetail>(raceDetails);
            listModelList.setMultiple(true);
            emptyHorseListbox.setModel(listModelList);
            combo.setValue("");
        }
    }

    //when user selects the check box of Race Detail list
    @Listen("onSelect = #emptyHorseListbox")
    public void raceDetailSelectCheckBox() {
        System.out.println("Inside raceDetailSelectCheckBox");
        if(listModelList.isSelectionEmpty()){
            System.out.println("Selected list is empty :(");
            //just in case for the no selection
            selectedRaceDetail = null;
            selectedText.setValue("0 horse(s) selected");
        }else{
            System.out.println("Selected list is not empty :)))");
            selectedHorseListModel.clear();
            for (int i=0; i<emptyHorseListbox.getSelectedItems().size(); i++) {
                selectedRaceDetail = listModelList.getSelection().iterator().next();
                selectedHorseListModel.add(selectedRaceDetail);
                System.out.println("inside not empty: HorseID/Jockey/Trainer >>>>> " + selectedRaceDetail.getHorseId()+"/"+selectedRaceDetail.getJockey()+"/"+selectedRaceDetail.getTrainer());
            }
            selectedText.setValue(emptyHorseListbox.getSelectedItems().size() + " horse(s) selected");
        }
    }

    //when user clicks the palce bet button
    @Listen("onClick = #addWinPlaceInfo")
    public void doAddWinPlaceClick() {
        //Set<Listitem> selectedList = horseListbox.getSelectedItems();
        Set<Listitem> selectedList = emptyHorseListbox.getSelectedItems();
        if (selectedList.isEmpty()) {
            alert("Please select at least one Race Detail to enter bet information");
            return;
        }
        //System.out.println(selectedHorseListModel.isEmpty()?"selectedHorseListModel is empty":"-- not empty -- ");
        //selectedHorseListbox.setModel(selectedHorseListModel);
        final Window dialog = (Window) Executions.createComponents("betwindow.zul", win, null);
        dialog.doModal();
    }

    @Listen("onClick = #popupSaveButton")
    public void showModal1(Event e) {
          Intbox ibWin = (Intbox) modalDialog.getFellow("winInput");
          Intbox ibPlc = (Intbox) modalDialog.getFellow("PlaceInput");
          iWinAmount = ibWin.getValue();
          iPlaceAmount = ibPlc.getValue();
          System.out.println(" --------- Win Amount: "+iWinAmount);
          System.out.println(" --------- Place Amount: "+iPlaceAmount);
          System.out.println(" --------- BetSize: ");        //error here
          modalDialog.detach();
    }
}

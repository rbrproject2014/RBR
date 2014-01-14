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
    @Wire
    Listbox chitListbox;
    @Wire
    Listbox chitCombinationsListbox;
    @Wire
    Listbox chitCombinationsDetailsListbox;
    @Wire
    Label totalBetsOutput;
    @Wire
    Label totalBetValueOutput;

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


    @WireVariable
    ChitService chitService;

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        //part 1
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
        //part 2
        totalBetsOutput.setValue(chitListModelList.getSize()+"");
        totalBetValueOutput.setValue(chitService.getTotalDailyChitValue().toString()+" Rs.");
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

}

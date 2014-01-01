package org.zkoss.essentials.chapter6.mvc;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;

import java.awt.*;


/**
 * Created with IntelliJ IDEA.
 * User: suhand
 * Date: 12/28/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class raceEntryController extends SelectorComposer<Component>{
    private static final long serialVersionUID = 1L;

    //wire components
    @Wire
    Combobox combo;

}

/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.essentials.chapter4;

import org.zkoss.essentials.services.SidebarPage;
import org.zkoss.essentials.services.SidebarPageConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SidebarPageConfigChapter4Impl implements SidebarPageConfig {

	HashMap<String,SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigChapter4Impl(){		
		pageMap.put("fn1",new SidebarPage("zk","Search/Update Race Information","/imgs/demo.png","//chapter6/index.zul"));
		pageMap.put("fn2",new SidebarPage("demo","Enter Chit Details","/imgs/doc.png","//chapter6/indexChit.zul"));
		pageMap.put("fn3",new SidebarPage("devref","View Race results","/imgs/doc.png","http://books.zkoss.org/wiki/ZK_Developer's_Reference"));
	}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
}

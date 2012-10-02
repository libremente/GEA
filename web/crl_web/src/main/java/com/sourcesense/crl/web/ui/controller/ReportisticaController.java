package com.sourcesense.crl.web.ui.controller;


import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode; 



@ManagedBean(name = "reportisticaController")
@ViewScoped
public class ReportisticaController implements Serializable {
	
	private TreeNode root;
	  
	private TreeNode selectedNode;  
		
	
	public ReportisticaController(){
		
		root = new DefaultTreeNode("Root", null);
		
		
		TreeNode servizioCommissioni = new DefaultTreeNode("Servizio commissioni", root);
		TreeNode commissione = new DefaultTreeNode("Commissione", root);
		TreeNode aula = new DefaultTreeNode("Aula", root);
		
		TreeNode estratti = new DefaultTreeNode("Estratti", servizioCommissioni);
		TreeNode fascicoli = new DefaultTreeNode("Fascicoli", servizioCommissioni);
		TreeNode statistiche = new DefaultTreeNode("Statistiche", servizioCommissioni);
		
 
		TreeNode xxx = new DefaultTreeNode("document","XXX.doc", fascicoli);
		TreeNode yyy = new DefaultTreeNode("document", "YYY.doc", fascicoli);
		

	}
	
	public TreeNode getRoot() {
		return root;
	}
	
	public TreeNode getSelectedNode() {  
	    return selectedNode;  
	}  

	public void setSelectedNode(TreeNode selectedNode) {  
	    this.selectedNode = selectedNode;  
	}  

}

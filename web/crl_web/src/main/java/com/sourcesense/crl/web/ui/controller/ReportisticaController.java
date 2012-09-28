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
        TreeNode node0 = new DefaultTreeNode("Segment 0", root);
        TreeNode node1 = new DefaultTreeNode("Segment 1", root);
        TreeNode node2 = new DefaultTreeNode("Segment 2", root);
        TreeNode node00 = new DefaultTreeNode("Segment 0.0", node0);
        TreeNode node01 = new DefaultTreeNode("Segment 0.1", node0);
        TreeNode node10 = new DefaultTreeNode("Segment 1.0", node1);
        TreeNode node11 = new DefaultTreeNode("Segment 1.1", node1);
        TreeNode node000 = new DefaultTreeNode("Segment 0.0.0", node00);
        TreeNode node001 = new DefaultTreeNode("Segment 0.0.1", node00);
        TreeNode node010 = new DefaultTreeNode("Segment 0.1.0", node01);
        TreeNode node100 = new DefaultTreeNode("Segment 1.0.0", node10);
 


	}

}

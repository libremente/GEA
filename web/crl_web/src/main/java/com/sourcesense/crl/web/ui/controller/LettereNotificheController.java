package com.sourcesense.crl.web.ui.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sourcesense.crl.business.service.LettereNotificheServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;

@ManagedBean(name = "lettereNotificheController")
@ViewScoped
public class LettereNotificheController {

	private TreeNode root;

	private TreeNode selectedNode;

	@ManagedProperty(value = "#{lettereNotificheServiceManager}")
	private LettereNotificheServiceManager lettereNotificheServiceManager;

	@PostConstruct
	public void init() {
		root = new DefaultTreeNode("root", null);

		// SERVIZIO COMMISSIONI
		TreeNode nodeServComm = new DefaultTreeNode("Servizio Commissioni",
				root);

		// COMMISSIONI
		TreeNode nodeComm = new DefaultTreeNode("Commissioni", root);

		// AULA
		TreeNode nodeAula = new DefaultTreeNode("Aula", root);

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

	public void onNodeExpand(NodeExpandEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Expanded", event.getTreeNode().toString());

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onNodeCollapse(NodeCollapseEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Collapsed", event.getTreeNode().toString());

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onNodeSelect(NodeSelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Selected", event.getTreeNode().toString());

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onNodeUnselect(NodeUnselectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Unselected", event.getTreeNode().toString());

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public LettereNotificheServiceManager getLettereNotificheServiceManager() {
		return lettereNotificheServiceManager;
	}

	public void setLettereNotificheServiceManager(
			LettereNotificheServiceManager lettereNotificheServiceManager) {
		this.lettereNotificheServiceManager = lettereNotificheServiceManager;
	}
	
	
	

}

/* 
 * Copyright (C) 2019 Consiglio Regionale della Lombardia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcesense.crl.web.ui.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.CollegamentoLeggiRegionali;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;

/**
 * Gestisce i collegamenti agli atti
 * 
 * @author sourcesense
 *
 */
@ManagedBean(name = "collegamentiController")
@ViewScoped
public class CollegamentiController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	private Atto atto = new Atto();

	private List<Collegamento> attiCollegatiList = new ArrayList<Collegamento>();
	private String collegamentoToDelete;

	private List<String> tipiAttoSindacato = new ArrayList<String>();
	private String tipoAttoSindacato;
	private String annoCreazione;
	private List<CollegamentoAttiSindacato> numeriAttoSindacato = new ArrayList<CollegamentoAttiSindacato>();
	private List<CollegamentoAttiSindacato> collegamentiAttiSindacato = new ArrayList<CollegamentoAttiSindacato>();
	private List<CollegamentoAttiSindacato> attiSindacato = new ArrayList<CollegamentoAttiSindacato>();

	private String idAttoSindacato;
	private String descrizioneAttoSindacato;
	private String attoSindacatoToDelete;

	private String noteCollegamenti;

	private List<String> tipiAttoRegionale = new ArrayList<String>();

	private List<CollegamentoLeggiRegionali> collegamentiLeggiRegionali = new ArrayList<CollegamentoLeggiRegionali>();
	private String tipoAttoRegionale;
	private String numeroAttoRegionale;
	private String descrizioneAttoRegionale;
	private String attoRegionaleToDelete;

	private String statoCommitAttiInterni = CRLMessage.COMMIT_DONE;
	private String statoCommitAttiIndirizzo = CRLMessage.COMMIT_DONE;
	private String statoCommitLeggiRegionali = CRLMessage.COMMIT_DONE;

	/**
	 * 
	 */
	@PostConstruct
	protected void init() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext().getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());
		setAttiCollegatiList(attoServiceManager.findAttiCollegatiById(getAtto().getId()));

		setCollegamentiAttiSindacato(attoServiceManager.findAttiSindacatoById(getAtto().getId()));
		setTipiAttoSindacato(attoServiceManager.findTipoAttiSindacato());
		setCollegamentiLeggiRegionali(Clonator.cloneList(atto.getCollegamentiLeggiRegionali()));
		setNoteCollegamenti(atto.getNoteCollegamenti());
	}

	public void updateAttiInterniHandler() {
		setStatoCommitAttiInterni(CRLMessage.COMMIT_UNDONE);
	}

	public void updateAttiIndirizzoHandler() {
		setStatoCommitAttiIndirizzo(CRLMessage.COMMIT_UNDONE);
	}

	public void updateLeggiRegionaliHandler() {
		setStatoCommitLeggiRegionali(CRLMessage.COMMIT_UNDONE);
	}

	/**
	 * 
	 */
	public void changeTabHandler() {

		if (statoCommitAttiInterni.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Le modifiche agli Atti Interni non sono state salvate ", ""));
		}

		if (statoCommitAttiIndirizzo.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Le modifiche ad Atti Indirizzo e Sindacato Ispettivo non sono state salvate ", ""));
		}

		if (statoCommitLeggiRegionali.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Le modifiche alle Leggi Regionali non sono state salvate ", ""));
		}
	}

	/**
	 * 
	 * @param idAttoToAdd
	 */
	public void addCollegamento(String idAttoToAdd) {

		if (!idAttoToAdd.trim().equals("")) {
			if (!checkCollegamenti(idAttoToAdd)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già abbinato ", ""));

			} else {
				Atto attoDaCollegare = attoServiceManager.findById(idAttoToAdd);
				Collegamento collegamento = new Collegamento();
				collegamento.setIdAttoCollegato(attoDaCollegare.getId());
				collegamento.setNumeroAttoCollegato(attoDaCollegare.getNumeroAtto());
				collegamento.setTipoAttoCollegato(attoDaCollegare.getTipoAtto());
				attiCollegatiList.add(collegamento);
				updateAttiInterniHandler();
			}
		}
	}

	/**
	 * 
	 * @param idAttoToAdd
	 * @param tipoAtto
	 */
	public void addCollegamento(String idAttoToAdd, String tipoAtto) {

		if (!idAttoToAdd.trim().equals("")) {
			if (!checkCollegamenti(idAttoToAdd)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già collegato ", ""));

			} else {

				Atto attoDaCollegare = null;
				boolean collega = true;

				if ("EAC".equalsIgnoreCase(tipoAtto)) {

					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Non è possibile collegare atti di questo tipo", ""));

					collega = false;

				} else if ("MIS".equalsIgnoreCase(tipoAtto)) {

					collega = false;
				} else {

					attoDaCollegare = attoServiceManager.findById(idAttoToAdd);
				}

				if (collega) {
					Collegamento collegamento = new Collegamento();
					collegamento.setIdAttoCollegato(attoDaCollegare.getId());
					collegamento.setNumeroAttoCollegato(attoDaCollegare.getNumeroAtto());
					collegamento.setTipoAttoCollegato(attoDaCollegare.getTipoAtto());
					attiCollegatiList.add(collegamento);
					updateAttiInterniHandler();
				}
			}
		}
	}

	/**
	 * 
	 */
	public void removeCollegamento() {

		for (Collegamento element : attiCollegatiList) {

			if (element.getNumeroAttoCollegato().equals(collegamentoToDelete)) {

				attiCollegatiList.remove(element);
				updateAttiInterniHandler();
				break;
			}
		}
	}

	/**
	 * 
	 * @param idAttoToAdd
	 * @return
	 */
	private boolean checkCollegamenti(String idAttoToAdd) {

		for (Collegamento element : attiCollegatiList) {

			if (element.getIdAttoCollegato().equals(idAttoToAdd)) {

				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	public void salvaCollegamenti() {
		atto.setCollegamenti(getAttiCollegatiList());
		atto.setNoteCollegamenti(getNoteCollegamenti());
		attoServiceManager.salvaCollegamenti(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext().getSessionMap().get("attoBean"));

		attoBean.getAtto().setCollegamenti(Clonator.cloneList(getAttiCollegatiList()));
		attoBean.getAtto().setNoteCollegamenti(getNoteCollegamenti());
		setStatoCommitAttiInterni(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Atti Interni salvati con successo", ""));
	}

	/**
	 * 
	 */
	public void handleAttoSindacatoChange() {

		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(annoCreazione), 0, 1);
		Date dataCreazioneDa = c.getTime();
		c.set(Integer.parseInt(annoCreazione), 11, 31);
		Date dataCreazioneA = c.getTime();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		setAttiSindacato(attoServiceManager.findAllAttiSindacato(tipoAttoSindacato, formatter.format(dataCreazioneDa),
				formatter.format(dataCreazioneA)));
		getNumeriAttoSindacato().clear();
		setNumeriAttoSindacato(attiSindacato);

		/*
		 * for (CollegamentoAttiSindacato collegamento : attiSindacato) {
		 * 
		 * if (collegamento.getTipoAtto().equals(tipoAttoSindacato)) {
		 * 
		 * getNumeriAttoSindacato().add(collegamento);
		 * 
		 * }
		 * 
		 * }
		 */

	}

	/**
	 * 
	 */
	public void addCollegamentoAttoSindacato() {

		if (!"".equals(idAttoSindacato)) {
			if (!checkCollegamentiAttiSindacati()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già collegato ", ""));

			} else {

				for (CollegamentoAttiSindacato collegamento : attiSindacato) {

					if (collegamento.getIdAtto().equals(idAttoSindacato)) {

						collegamento.setDescrizione(descrizioneAttoSindacato);
						collegamentiAttiSindacato.add(collegamento);
						break;
					}

				}

				updateAttiIndirizzoHandler();
			}
		}
	}

	/**
	 * 
	 */
	public void removeCollegamentoAttoSindacato() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getNumeroAtto().equals(attoSindacatoToDelete)) {

				collegamentiAttiSindacato.remove(element);
				updateAttiIndirizzoHandler();
				break;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkCollegamentiAttiSindacati() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getNumeroAtto().equals(idAttoSindacato)) {

				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	public void salvaAttiIndirizzo() {

		atto.setCollegamentiAttiSindacato(getCollegamentiAttiSindacato());

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext().getSessionMap().get("attoBean"));

		attoServiceManager.salvaCollegamentiAttiSindacato(atto);

		attoBean.getAtto().setCollegamentiAttiSindacato(Clonator.cloneList(getCollegamentiAttiSindacato()));

		setStatoCommitAttiIndirizzo(CRLMessage.COMMIT_DONE);
		context.addMessage(null,
				new FacesMessage("Atti di indirizzo e di Sindacato Ispettivo salvati con successo", ""));
	}

	/**
	 * 
	 */
	public void addCollegamentoAttoRegionale() {

		if (!numeroAttoRegionale.trim().equals("")) {
			if (!checkCollegamentiAttiRegionali()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già collegato ", ""));

			} else {

				CollegamentoLeggiRegionali collegamento = new CollegamentoLeggiRegionali();
				collegamento.setDescrizione(getDescrizioneAttoRegionale());
				collegamento.setNumeroAtto(getNumeroAttoRegionale());
				collegamento.setTipoAtto(getTipoAttoRegionale());
				collegamentiLeggiRegionali.add(collegamento);

				updateLeggiRegionaliHandler();
			}
		}
	}

	/**
	 * 
	 */
	public void removeCollegamentoAttoRegionale() {

		for (CollegamentoLeggiRegionali element : collegamentiLeggiRegionali) {

			if (element.getNumeroAtto().equals(attoRegionaleToDelete)) {

				collegamentiLeggiRegionali.remove(element);
				updateLeggiRegionaliHandler();
				break;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkCollegamentiAttiRegionali() {

		for (CollegamentoLeggiRegionali element : collegamentiLeggiRegionali) {

			if (element.getNumeroAtto().equals(numeroAttoRegionale)) {

				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	public void salvaLeggiRegionali() {
		atto.setCollegamentiLeggiRegionali(getCollegamentiLeggiRegionali());

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext().getSessionMap().get("attoBean"));

		attoBean.getAtto().setCollegamentiLeggiRegionali(Clonator.cloneList(getCollegamentiLeggiRegionali()));

		setStatoCommitLeggiRegionali(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Leggi Regionali salvate con successo", ""));
	}

	public Atto getAtto() {
		return atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public List<Collegamento> getAttiCollegatiList() {
		return attiCollegatiList;
	}

	public void setAttiCollegatiList(List<Collegamento> attiCollegatiList) {
		this.attiCollegatiList = attiCollegatiList;
	}

	public String getCollegamentoToDelete() {
		return collegamentoToDelete;
	}

	public void setCollegamentoToDelete(String collegamentoToDelete) {
		this.collegamentoToDelete = collegamentoToDelete;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public String getStatoCommitAttiInterni() {
		return statoCommitAttiInterni;
	}

	public void setStatoCommitAttiInterni(String statoCommitAttiInterni) {
		this.statoCommitAttiInterni = statoCommitAttiInterni;
	}

	public String getStatoCommitAttiIndirizzo() {
		return statoCommitAttiIndirizzo;
	}

	public void setStatoCommitAttiIndirizzo(String statoCommitAttiIndirizzo) {
		this.statoCommitAttiIndirizzo = statoCommitAttiIndirizzo;
	}

	public String getStatoCommitLeggiRegionali() {
		return statoCommitLeggiRegionali;
	}

	public void setStatoCommitLeggiRegionali(String statoCommitLeggiRegionali) {
		this.statoCommitLeggiRegionali = statoCommitLeggiRegionali;
	}

	public List<CollegamentoAttiSindacato> getCollegamentiAttiSindacato() {
		return collegamentiAttiSindacato;
	}

	public void setCollegamentiAttiSindacato(List<CollegamentoAttiSindacato> collegamentiAttiSindacato) {
		this.collegamentiAttiSindacato = collegamentiAttiSindacato;
	}

	public String getTipoAttoSindacato() {
		return tipoAttoSindacato;
	}

	public void setTipoAttoSindacato(String tipoAttoSindacato) {
		this.tipoAttoSindacato = tipoAttoSindacato;
	}

	public String getIdAttoSindacato() {
		return idAttoSindacato;
	}

	public void setIdAttoSindacato(String idAttoSindacato) {
		this.idAttoSindacato = idAttoSindacato;
	}

	public String getDescrizioneAttoSindacato() {
		return descrizioneAttoSindacato;
	}

	public void setDescrizioneAttoSindacato(String descrizioneAttoSindacato) {
		this.descrizioneAttoSindacato = descrizioneAttoSindacato;
	}

	public String getAttoSindacatoToDelete() {
		return attoSindacatoToDelete;
	}

	public void setAttoSindacatoToDelete(String attoSindacatoToDelete) {
		this.attoSindacatoToDelete = attoSindacatoToDelete;
	}

	public List<CollegamentoLeggiRegionali> getCollegamentiLeggiRegionali() {
		return collegamentiLeggiRegionali;
	}

	public void setCollegamentiLeggiRegionali(List<CollegamentoLeggiRegionali> collegamentiLeggiRegionali) {
		this.collegamentiLeggiRegionali = collegamentiLeggiRegionali;
	}

	public String getAttoRegionaleToDelete() {
		return attoRegionaleToDelete;
	}

	public void setAttoRegionaleToDelete(String attoRegionaleToDelete) {
		this.attoRegionaleToDelete = attoRegionaleToDelete;
	}

	public String getTipoAttoRegionale() {
		return tipoAttoRegionale;
	}

	public void setTipoAttoRegionale(String tipoAttoRegionale) {
		this.tipoAttoRegionale = tipoAttoRegionale;
	}

	public String getNumeroAttoRegionale() {
		return numeroAttoRegionale;
	}

	public void setNumeroAttoRegionale(String numeroAttoRegionale) {
		this.numeroAttoRegionale = numeroAttoRegionale;
	}

	public String getDescrizioneAttoRegionale() {
		return descrizioneAttoRegionale;
	}

	public void setDescrizioneAttoRegionale(String descrizioneAttoRegionale) {
		this.descrizioneAttoRegionale = descrizioneAttoRegionale;
	}

	public List<String> getTipiAttoSindacato() {
		return tipiAttoSindacato;
	}

	public void setTipiAttoSindacato(List<String> tipiAttoSindacato) {
		this.tipiAttoSindacato = tipiAttoSindacato;
	}

	public List<CollegamentoAttiSindacato> getNumeriAttoSindacato() {
		return numeriAttoSindacato;
	}

	public void setNumeriAttoSindacato(List<CollegamentoAttiSindacato> numeriAttoSindacato) {
		this.numeriAttoSindacato = numeriAttoSindacato;
	}

	public List<String> getTipiAttoRegionale() {
		return tipiAttoRegionale;
	}

	public void setTipiAttoRegionale(List<String> tipiAttoRegionale) {
		this.tipiAttoRegionale = tipiAttoRegionale;
	}

	public String getNoteCollegamenti() {
		return noteCollegamenti;
	}

	public void setNoteCollegamenti(String noteCollegamenti) {
		this.noteCollegamenti = noteCollegamenti;
	}

	public List<CollegamentoAttiSindacato> getAttiSindacato() {
		return attiSindacato;
	}

	public void setAttiSindacato(List<CollegamentoAttiSindacato> attiSindacato) {
		this.attiSindacato = attiSindacato;
	}

	public String getAnnoCreazione() {
		return annoCreazione;
	}

	public void setAnnoCreazione(String annoCreazione) {
		this.annoCreazione = annoCreazione;
	}

}

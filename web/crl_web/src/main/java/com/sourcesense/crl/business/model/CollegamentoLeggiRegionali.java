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
package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("collegamentoLeggiRegionali")
@JsonTypeName("collegamentoLeggiRegionali")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class CollegamentoLeggiRegionali implements Cloneable{

	private String descrizione;
	private String tipoAtto;
	private String numeroAtto;
	private String link;
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override public String toString() {
	    return Pojomatic.toString(this);
	 }
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	

}

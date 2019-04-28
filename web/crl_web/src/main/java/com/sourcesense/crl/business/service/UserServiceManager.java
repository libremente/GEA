package com.sourcesense.crl.business.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.security.AlfrescoSessionTicket;
import com.sourcesense.crl.business.service.rest.UserService;
import com.sourcesense.crl.util.ServiceAuthenticationException;
import com.sourcesense.crl.util.URLBuilder;

@Service("userServiceManager")
public class UserServiceManager implements ServiceManager {

	@Autowired
	private URLBuilder urlBuilder;

	@Autowired
	private UserService userService;

	public User authenticate(User user) throws ServiceAuthenticationException {

		urlBuilder.setAlfrescoSessionTicket(userService.getAuthenticationToken(
				urlBuilder.buildURL("alfresco_context_url",
						"alfresco_authentication"), user));

		User sessionUser = userService.completeAuthentication(urlBuilder
				.buildAlfrescoURL("alfresco_context_url", "alf_gruppi_utente",
						null), user);

		// Se gruppo vuoto errore
		if (sessionUser.getGruppi().get(0) == null) {

			sessionUser.setSessionGroup(null);
		} else {
			for (int i=0;i<sessionUser.getGruppi().size();i++){

				String nomeGruppo = sessionUser.getGruppi().get(i).getNome();

				// se commissione comincia con COMM_
				if (nomeGruppo.startsWith("COMM_")) {

					sessionUser.getGruppi().get(i).setCommissione(true);
					sessionUser
							.getGruppi()
							.get(i)
							.setNome(
									nomeGruppo.substring(nomeGruppo.indexOf("_") + 1));

			}
			}
			sessionUser.setSessionGroup(sessionUser.getGruppi().get(0));
		}

		if (urlBuilder.getAlfrescoSessionTicket() != null) {
			return sessionUser;
		} else {
			return null;
		}

	}

	public User authenticateReadOnly(User user)
			throws ServiceAuthenticationException {

		user.setPassword(urlBuilder.getMessageSource().getMessage("alfresco_guest_password", null, Locale.ITALY));
		user.setUsername(urlBuilder.getMessageSource().getMessage("alfresco_guest_username", null, Locale.ITALY));
		
		urlBuilder.setAlfrescoSessionTicket(userService.getAuthenticationToken(
				urlBuilder.buildURL("alfresco_context_url",
						"alfresco_authentication"), user));

		User sessionUser = userService.completeAuthentication(urlBuilder
				.buildAlfrescoURL("alfresco_context_url", "alf_gruppi_utente",
						null), user);

		String nomeGruppo = sessionUser.getGruppi().get(0).getNome();
		sessionUser.setSessionGroup(sessionUser.getGruppi().get(0));
		return sessionUser;

	}

	@Override
	public User persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

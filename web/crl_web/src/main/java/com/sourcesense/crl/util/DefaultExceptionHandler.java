package com.sourcesense.crl.util;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sourcesense.crl.business.security.SessionPhaseListener;

public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

	private static final String errorpage = "/exception.xhtml";
	
	private static final Log LOG = LogFactory.getLog(DefaultExceptionHandler.class);

	/** key for session scoped message detail */

	public static final String MESSAGE_DETAIL_KEY = "ip.client.jsftoolkit.messageDetail";

	private ExceptionHandler wrapped;

	public DefaultExceptionHandler(ExceptionHandler wrapped) {

		this.wrapped = wrapped;

	}

	@Override
	public Throwable getRootCause(Throwable t) {
		// TODO Auto-generated method stub
		return super.getRootCause(t);
	}
	
	@Override
	public ExceptionHandler getWrapped() {

		return this.wrapped;

	}

	@Override
	public void handle() throws FacesException {

		FacesContext fc = FacesContext.getCurrentInstance();

		if (fc.isProjectStage(ProjectStage.Development)) {

			getWrapped().handle();

		} else {

			for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {

				ExceptionQueuedEvent event = i.next();
				ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
				String redirectPage = null;
				Throwable t = context.getException();

				if (t instanceof AbortProcessingException) {

					LOG.error("An unexpected exception has occurred by event listener(s)",t);
					redirectPage = errorpage;
					fc.getExternalContext().getSessionMap().put(DefaultExceptionHandler.MESSAGE_DETAIL_KEY,t.getLocalizedMessage());

				} else if (t instanceof ViewExpiredException) {

					if (LOG.isDebugEnabled()) {

						LOG.debug("View '"+ ((ViewExpiredException) t).getViewId() + "' is expired",t);

					}
					
					HttpSession session = (HttpSession) fc.getExternalContext()
							.getSession(false);

					if (session != null) {
						session.invalidate();
					}


				} else if (t.getCause().getCause().getCause() instanceof ServiceNotAvailableException) {   

					String errorMsg =   ((ServiceNotAvailableException) t.getCause().getCause().getCause()).getServiceName();
					
					LOG.error( errorMsg, t.getCause().getCause().getCause());
					redirectPage = errorpage+"?error="+errorMsg;

				} else {

					String messageKey = handleUnexpected(fc, t);

					redirectPage = errorpage + messageKey;

					fc.getExternalContext().getSessionMap().put(DefaultExceptionHandler.MESSAGE_DETAIL_KEY,t.getLocalizedMessage());

				}

				SessionPhaseListener spl = new SessionPhaseListener();
				spl.doRedirect(fc, redirectPage);
				break;

			}

		}
	}

	protected String handleUnexpected(FacesContext facesContext,
			final Throwable t) {

		LOG.error("An unexpected internal error has occurred", t);

		return "jsftoolkit.exception.UncheckedException";

	}

}

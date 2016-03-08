package br.com.visaogeo.mb;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import br.com.visaogeo.ejb.PontosEJBLocal;
import br.com.visaogeo.to.LocalTO;

@ManagedBean
@ViewScoped
public class FPontoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(FPontoMB.class);
	
	private ResourceBundle bundle = ResourceBundle.getBundle("br.com.visaogeo.resourcebundle");
	
	@EJB
	private PontosEJBLocal pontosEJB;
	
	private LocalTO LocalTO;
	
	@PostConstruct
	public void init() {
		String idPonto = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idPonto");
		populaLocal(idPonto);
	}

	private void populaLocal(String idPonto) {
		try {
			
			if(idPonto == null || idPonto.compareTo("") == 0) {
				return;
			}
			
			LocalTO localTO = new LocalTO();
			localTO.setId(Long.valueOf(idPonto));
			
			this.LocalTO = this.pontosEJB.procuraLocal(localTO);
			
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
		
	}
	
	public String atualizar() {
		try {
			
			this.pontosEJB.atualizaLocal(this.LocalTO);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("operacaosucesso"), null));
			return "index";
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
		return null;
	}

	public LocalTO getLocalTO() {
		return LocalTO;
	}

	public void setLocalTO(LocalTO localTO) {
		LocalTO = localTO;
	}

}

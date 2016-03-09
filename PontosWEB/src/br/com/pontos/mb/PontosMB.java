package br.com.pontos.mb;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.jboss.logging.Logger;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.com.pontos.ejb.PontosEJBLocal;
import br.com.pontos.to.LocalTO;

@ManagedBean
@ViewScoped
public class PontosMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PontosMB.class);
	
	private LocalTO localTO;
	
	private List<LocalTO> lsLocalsTO;

	private MapModel emptyModel;
	
	private ResourceBundle bundle = ResourceBundle.getBundle("br.com.pontos.resourcebundle");
	
	@EJB
	private PontosEJBLocal pontosEJB;
	
	@PostConstruct
	public void init() {
		this.localTO = new LocalTO();
	    emptyModel = new DefaultMapModel();
	    try {
			this.lsLocalsTO = this.pontosEJB.listaLocal();
			
			populaMapa();
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
	}

	private void populaMapa() {
//		RequestContext context = RequestContext.getCurrentInstance();
		for (Iterator<LocalTO> iterator = this.lsLocalsTO.iterator(); iterator.hasNext();) {
			LocalTO localTO = iterator.next();
		    Marker marker = new Marker(new LatLng(localTO.getLatitude(), localTO.getLongitude()), localTO.getDescricao());
//		    marker.setDraggable(true);
		    emptyModel.addOverlay(marker);
		    
//			context.execute("markerAddPageLoad("+localTO.getLatitude()+","+localTO.getLongitude()+")");
		}
	}
	
	public void cadastroPontoReferencia() throws Exception {
		try {
			pontosEJB.cadastroLocal(this.localTO);
		} catch (EJBException e) {
			throw new EJBException(e.getMessage(), e);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	public void excluir(LocalTO localTO) {
		try {
			this.pontosEJB.excluiLocal(localTO);
			this.lsLocalsTO = this.pontosEJB.listaLocal();
			
			Marker markAux = null;
			
		    for(Marker premarker : this.emptyModel.getMarkers()) {
	            
	            if(localTO.getLatitude().compareTo((Double)premarker.getLatlng().getLat()) == 0
	            		&& localTO.getLongitude().compareTo((Double)premarker.getLatlng().getLng()) == 0
	            		|| (localTO.getDescricao() != null && localTO.getDescricao().equals(premarker.getTitle()))) {
	            	
	            	markAux = premarker;
	            	
	            }
	        }
		    this.emptyModel.getMarkers().remove(markAux);
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("operacaosucesso"), null));
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		}
	}
	
	public String editar() {
		try {
			  Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			  String idPonto = params.get("idPonto");
			  
			  Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			  flash.setKeepMessages(true);
			  flash.put("idPonto", idPonto);
			  
			  return "flocal?faces-redirect=true";
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
	  
	public void addMarker() {
//	    Marker marker = new Marker(new LatLng(lat, lng), title);
		try {
			this.cadastroPontoReferencia();
		    Marker marker = new Marker(new LatLng(this.localTO.getLatitude(), this.localTO.getLongitude()), this.localTO.getDescricao());
//		    marker.setDraggable(true);
		    emptyModel.addOverlay(marker);
		    this.lsLocalsTO = this.pontosEJB.listaLocal();
			
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("operacaosucesso"), null));
		} catch (EJBException e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		} catch (Exception e) {
			emptyModel.getMarkers().remove(emptyModel.getMarkers().size()-1);//verificar
			log.error(e.getMessage(), e);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, fm);
		} finally {			
			this.localTO = new LocalTO();
		}
	}

	public LocalTO getLocal() {
		return localTO;
	}

	public void setLocal(LocalTO local) {
		this.localTO = local;
	}
	  
	public MapModel getEmptyModel() {
	    return emptyModel;
	}
	
	public List<LocalTO> getLsLocalsTO() {
		return lsLocalsTO;
	}

	public void setLsLocalsTO(List<LocalTO> lsLocalsTO) {
		this.lsLocalsTO = lsLocalsTO;
	}

}
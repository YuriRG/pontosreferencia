package br.com.visaogeo.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.visaogeo.entidade.Classificacao;
import br.com.visaogeo.entidade.Endereco;
import br.com.visaogeo.entidade.Local;
import br.com.visaogeo.to.ClassificacaoTO;
import br.com.visaogeo.to.EnderecoTO;
import br.com.visaogeo.to.LocalTO;

/**
 * Session Bean implementation class PontoEJB
 */
@Stateless
public class PontosEJB implements PontosEJBLocal, PontosEJBRemoto {
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public Long cadastroLocal(LocalTO localTO) throws Exception {
		try {
			if(localTO.getClassificacao().getTipo() == null 
					|| localTO.getClassificacao().getTipo().trim().isEmpty()) {
				throw new EJBException("Campo Tipo � obrigat�rio !!!");
			}
			
			Local localAux = new Local(localTO);
			em.persist(localAux.getClassificacao());
			em.persist(localAux.getEndereco());
			em.persist(localAux);
			return localAux.getId();
		} catch (Exception e) {
			throw new EJBException(e.getMessage(), e);
		}
		
	}

	@Override
	public void atualizaLocal(LocalTO localTO) throws Exception {
		try {
			
			if(localTO.getLatitude() == null) {
				throw new EJBException("Campo Latitude � obrigat�rio !!!");
			}
			
			if(localTO.getLongitude() == null ) {
				throw new EJBException("Campo Longitude � obrigat�rio !!!");
			}
			
			if(localTO.getClassificacao().getTipo() == null 
					|| localTO.getClassificacao().getTipo().trim().isEmpty() ) {
				throw new EJBException("Campo Tipo � obrigat�rio !!!");
			}
			
			Local local = em.find(Local.class, localTO.getId());
			Classificacao classificacao = em.find(Classificacao.class, localTO.getClassificacao().getId());
			Endereco endereco = em.find(Endereco.class, localTO.getEndereco().getId());
						
			local.setId(localTO.getId());
			local.setLongitude(localTO.getLongitude());
			local.setLatitude(localTO.getLatitude());
			local.setDescricao(localTO.getDescricao());
			
			classificacao.setId(localTO.getClassificacao().getId());
			classificacao.setTipo(localTO.getClassificacao().getTipo());
			if("RESIDENCIAL".compareTo(localTO.getClassificacao().getTipo().toUpperCase()) == 0) {
				classificacao.setTamanho(localTO.getClassificacao().getTamanho());	
				classificacao.setAtividade(null);					
			} else {
				if("COMERCIAL".compareTo(localTO.getClassificacao().getTipo().toUpperCase()) == 0) {
					classificacao.setAtividade(localTO.getClassificacao().getAtividade());					
					classificacao.setTamanho(null);	
				}
			}
			
			endereco.setId(localTO.getEndereco().getId());
			endereco.setBairro(localTO.getEndereco().getBairro());
			endereco.setCidade(localTO.getEndereco().getCidade());
			endereco.setEstado(localTO.getEndereco().getEstado());
			endereco.setNumero(localTO.getEndereco().getNumero());
			endereco.setRua(localTO.getEndereco().getRua());
			
		} catch (Exception e) {
			throw new EJBException(e.getMessage(), e);
		}
	}

	@Override
	public void excluiLocal(LocalTO localTO) throws Exception {
		try {
			Local local = em.find(Local.class , localTO.getId()) ;
			em.remove(local);
		} catch (Exception e) {
			throw new EJBException(e.getMessage(), e);
		}
		
	}
	
	@Override
	public List<LocalTO> listaLocal() throws Exception {
		try {
			Query query = em.createQuery(
					"select x from Local x join fetch x.classificacao join fetch x.endereco order by x.id asc");
			 List<Local>  lsLocal = query.getResultList();
			 List<LocalTO> lsLocalTO = new ArrayList<LocalTO>();
			 
			 for (Iterator<Local> iterator = lsLocal.iterator(); iterator.hasNext();) {
				Local local = iterator.next();
				lsLocalTO.add(montaLocalTO(local));
			}

			 return lsLocalTO;
		} catch (Exception e) {
			throw new EJBException(e.getMessage(), e);
		}
		
	}
	
    public LocalTO procuraLocal(LocalTO localTO) throws Exception {
    	try {
    		
			TypedQuery<Local> query = em.createQuery(
					"select x from Local x join fetch x.classificacao join fetch x.endereco WHERE x.id = ? order by x.id asc", Local.class);
			query.setParameter(1, localTO.getId());
			
			return montaLocalTO(query.getSingleResult());
		} catch (Exception e) {
			throw new EJBException(e.getMessage(), e);
		}
    }
    
    public Long procuraUltimoLocal() throws Exception {
    	try {
    		
    		TypedQuery<Local> query = em.createQuery(
    				"SELECT x FROM Local order by id desc limit 1", Local.class);
    		
    		return query.getSingleResult().getId();
    	} catch (Exception e) {
    		throw new EJBException(e.getMessage(), e);
    	}
    }
	
	private LocalTO montaLocalTO(Local local) {
		LocalTO localTO = new LocalTO();
		
		localTO.setId(local.getId());
		localTO.setLongitude(local.getLongitude());
		localTO.setLatitude(local.getLatitude());
		localTO.setDescricao(local.getDescricao());
		localTO.setEndereco(montaEnderecoTO(local.getEndereco()));
		localTO.setClassificacao(montaClassificacaoTO(local.getClassificacao()));
		
		return localTO;
	}
	
	private EnderecoTO montaEnderecoTO(Endereco endereco) {
		EnderecoTO enderecoTO = new EnderecoTO();
		
		enderecoTO.setId(endereco.getId());
		enderecoTO.setRua(endereco.getRua());
		enderecoTO.setNumero(endereco.getNumero());
		enderecoTO.setBairro(endereco.getBairro());
		enderecoTO.setEstado(endereco.getEstado());
		enderecoTO.setCidade(endereco.getCidade());
		return enderecoTO;
	}
	
	private ClassificacaoTO montaClassificacaoTO(Classificacao classificacao) {
		ClassificacaoTO classificacaoTO = new ClassificacaoTO();
		
		classificacaoTO.setId(classificacao.getId());
		classificacaoTO.setTipo(classificacao.getTipo());
		classificacaoTO.setTamanho(classificacao.getTamanho());
		classificacaoTO.setAtividade(classificacao.getAtividade());
		
		return classificacaoTO;
	}

}

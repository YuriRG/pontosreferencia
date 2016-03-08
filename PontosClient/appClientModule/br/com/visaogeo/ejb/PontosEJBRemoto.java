package br.com.visaogeo.ejb;

import java.util.List;

import javax.ejb.Remote;

import br.com.visaogeo.to.LocalTO;

@Remote(PontosEJBRemoto.class)
public interface PontosEJBRemoto {
	public Long cadastroLocal(LocalTO localTO) throws Exception;
	public void atualizaLocal(LocalTO localTO) throws Exception;
	public void excluiLocal(LocalTO localTO) throws Exception;
	public <T> List<T> listaLocal() throws Exception;
	public LocalTO procuraLocal(LocalTO localTO) throws Exception;
	public Long procuraUltimoLocal() throws Exception;
}

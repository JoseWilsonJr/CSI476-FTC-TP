/**
*@author José Wilson da Silva Júnior 15.1.8156
*@author Leonardo Barcelos Nardy Dias - 13.2.8646
*@author Lucas Teotônio Lima - 15.1.5902
*/

import java.util.Iterator;

public class AutomatoDeterministico extends Automato
{
	private Estado estadoInicial;
	
	public AutomatoDeterministico()
	{
		super();
	}

	public void setEstadoInicial(int id)
	{
		estadoInicial = new Estado(id);
	}

	public Estado getEstadoInicial()
	{
		return estadoInicial;
	}

	public boolean eEstadoInicial(int id)
	{
		return estadoInicial.getId() == id;
	}

	public void setTransicao(int origem, int destino, String simbolo)
	{
		Transicao novaTransicao = new Transicao(
			new Estado(origem),
			new Estado(destino),
			simbolo
		);

		boolean flag = true;

		Iterator<Transicao> iterador = transicoes.iterator();
		while(iterador.hasNext()) {
			Transicao transicao = iterador.next();
			if (transicao.eIgualAFD(novaTransicao)) {
				flag = false;
			}
		}

		if (flag) {
			transicoes.add(novaTransicao);
		}
	}

	public Transicao getTransicao(int origem, String simbolo)
	{
		Iterator<Transicao> iterador = transicoes.iterator();
		while(iterador.hasNext()) {
			Transicao transicao = iterador.next();
			if (transicao.getOrigem().getId() == origem
				&& transicao.getSimbolo().equals(simbolo)
			) {
				return transicao;
			}
		}

		return null;
	}

	public void imprimirEstadoInicial()
	{
		System.out.println("Estados Inicial:" + estadoInicial.getNome());
	}
}
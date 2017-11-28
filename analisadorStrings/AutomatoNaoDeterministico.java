/**
*@author José Wilson da Silva Júnior 15.1.8156
*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class AutomatoNaoDeterministico extends Automato
{
	private HashMap<Integer, Estado> estadosIniciais;
	
	public AutomatoNaoDeterministico()
	{
		super();
		this.estadosIniciais = new HashMap<Integer, Estado>();
	}

	public void setEstadoInicial(int id)
	{
		estadosIniciais.put(id, new Estado(id));
	}

	public Estado getEstadoInicial(int id)
	{
		return estadosIniciais.get(id);
	}

	public HashMap<Integer, Estado> getEstadosIniciais()
	{
		return estadosIniciais;
	}

	public int getSizeEstadosIniciais()
	{
		return estadosIniciais.size();
	}

	public boolean eEstadoInicial(int id)
	{
		return estadosIniciais.containsKey(id);
	}

	public void removerEstadosIniciais()
	{
		estadosIniciais = new HashMap<Integer, Estado>();
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
			if (transicao.eIgualAFN(novaTransicao)) {
				flag = false;
			}
		}

		if (flag) {
			transicoes.add(novaTransicao);
		}
	}

	public HashSet<Transicao> getTransicao(int origem, String simbolo)
	{
		HashSet<Transicao> retorno = new HashSet<Transicao>();

		Iterator<Transicao> iterador = transicoes.iterator();
		while(iterador.hasNext()) {
			Transicao transicao = iterador.next();
			if (transicao.getOrigem().getId() == origem
				&& transicao.getSimbolo().equals(simbolo)
			) {
				retorno.add(transicao);
			}
		}

		return retorno;
	}

	public void imprimirEstadoInicial()
	{
		System.out.println("Estados Iniciais:");
		for (Integer key : estadosIniciais.keySet()) {
			Estado estado = estadosIniciais.get(key);
			System.out.println(estado.getNome());
		}
	}
}
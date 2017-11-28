/**
*@author José Wilson da Silva Júnior 15.1.8156
*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public abstract class Automato
{
	protected HashMap<Integer, Estado> estados;
	protected HashMap<Integer, Estado> estadosFinais;
	protected HashSet<Transicao> transicoes;
	protected static int startStateLimit = 0;

	public Automato()
	{
		this.estados = new HashMap<Integer, Estado>();
		this.estadosFinais = new HashMap<Integer, Estado>();
		this.transicoes = new HashSet<Transicao>();
	}

	public void setEstado(int id)
	{
		estados.put(id, new Estado(id));
	}

	public void setEstadoFinal(int id)
	{
		estadosFinais.put(id, new Estado(id));
	}

	public Estado getEstadoFinal(int id)
	{
		return estadosFinais.get(id);
	}

	public HashMap<Integer, Estado> getEstadosFinais()
	{
		return estadosFinais;
	}

	public int getSizeEstadosFinais()
	{
		return estadosFinais.size();
	}

	public HashMap<Integer, Estado> getEstados()
	{
		return estados;
	}

	public Estado getEstado(int id)
	{
		return estados.get(id);
	}

	public boolean eEstadoFinal(int id)
	{
		return estadosFinais.containsKey(id);
	}

	public HashSet<Transicao> getTransicoes()
	{
		return transicoes;
	}

	public void removerEstadosFinais()
	{
		estadosFinais = new HashMap<Integer, Estado>();
	}

	public void imprimirAutomatoFinito()
	{
		System.out.println("Estados:");
		for (Integer key : estados.keySet()) {
			Estado estado = estados.get(key);
			System.out.println(estado.getNome());
		}

		imprimirEstadoInicial();

		System.out.println("Estados Finais:");
		for (Integer key : estadosFinais.keySet()) {
			Estado estado = estadosFinais.get(key);
			System.out.println(estado.getNome());
		}

		Iterator<Transicao> iterador = transicoes.iterator();
		while(iterador.hasNext()) {
			Transicao transicao = iterador.next();
			System.out.println(transicao.getOrigem().getNome()
				+ " para " + transicao.getDestino().getNome()
				+ " consumindo " + transicao.getSimbolo());
		}
	}

	public abstract void imprimirEstadoInicial();
}
/**
*@author José Wilson da Silva Júnior 15.1.8156
*@author Leonardo Barcelos Nardy Dias - 13.2.8646
*@author Lucas Teotônio Lima - 15.1.5902
*/
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.HashMap;

public class CadastroDeTag
{
	Tag TAG = new Tag();
	Stack<AutomatoNaoDeterministico> pilhaAutomato = new Stack<AutomatoNaoDeterministico>();
	HashSet<String> alfabetoAutomato = new HashSet<String>();
	int idEstadoAutomato = 0;
	
	/*
		Método responsável por validar se a definição 
		da tag enviada por parâmetro está correta.
	*/
	public boolean validarDefinicaoTag(String tag, ArrayList<String> nomeTags)
	{
		boolean result = false;
		if (!tag.isEmpty()) {
			String nomeTag = "";
			String notacaoPolonesa = "";
			for (int i = 0; i < tag.length(); i++) {
				if (tag.charAt(i) != ':') {
					nomeTag += tag.charAt(i);
				} else {
					if (nomeTags.indexOf(nomeTag) == -1) {
						nomeTags.add(nomeTag);
						if (i < tag.length() - 1) {
							String delimitador = tag.substring(i, i+2);
							notacaoPolonesa = tag.substring(i+2);
							if (!nomeTag.isEmpty() && delimitador.equals(": ") && validarNotacaoPolonesa(notacaoPolonesa)) {
								result = true;
								TAG.setNomeTag(nomeTag);
							}
						}
					}
					break;
				}
			}
		}

		return result;
	}
	
	/*
		Método responsável por validar se a notação polonesa
		enviada por parâmetro está correta. O método além de
		validar a notação polonesa, converte a mesma para
		a expressão regular equivalente utilizando a estrutura
		de dados pilha.
	*/
	public boolean validarNotacaoPolonesa(String notacaoPolonesa)
	{
		if (!notacaoPolonesa.isEmpty()) {
			String subExpressaoRegular;

			for (int i = 0; i < notacaoPolonesa.length(); i++) {
				char caractere = notacaoPolonesa.charAt(i);
				if (codigoAsciiPermitido((int) caractere)) {
					if (caractere == '*') {
						if (pilhaAutomato.size() >= 1) {
							fechoKleeneAutomato();

						} else {
							return false;
						}
					} else if (caractere == '+' || caractere == '.') {
						if (pilhaAutomato.size() >= 2) {
							if (caractere == '+') {
								uniaoAutomato();
							} else {
								concatenacaoAutomato();
							}
						} else {
							return false;
						}
					} else {
						if (pilhaAutomato.size() < 2) {
							if (notacaoPolonesa.length() == 1 || i < notacaoPolonesa.length() - 1) {
								String proximoCaractere = "";
								if (caractere == '\\') {
									if (notacaoPolonesa.length() == 2 || (notacaoPolonesa.length() > 1 && i < notacaoPolonesa.length() - 2)) {
										if (codigoAsciiPermitido((int) notacaoPolonesa.charAt(i+1))) {
											proximoCaractere = Character.toString(notacaoPolonesa.charAt(++i));
										} else {
											return false;
										}
									} else {
										return false;
									}
								}
								alfabetoAutomato.add(caractere + proximoCaractere);
								construirAutomato(caractere + proximoCaractere);
							} else {
								return false;
							}
						} else {
							return false;
						}	
					}
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
		
		TAG.setAFN(pilhaAutomato.pop());
		return true;
	}
	
	/*
		Método responsável por verificar se o código ascii
		enviado por parâmetro representa um caracter válido
		definido pela aplicação.
	*/
	public boolean codigoAsciiPermitido(int codigo)
	{
		if (codigo >= 32 && codigo <= 126 || codigo == 955) {
			return true;
		} else {
			return false;
		}
	}

	public void construirAutomato(String simbolo)
	{
		AutomatoNaoDeterministico automato = new AutomatoNaoDeterministico();
		automato.setEstado(++this.idEstadoAutomato);
		automato.setEstadoInicial(this.idEstadoAutomato);
		automato.setTransicao(this.idEstadoAutomato, ++this.idEstadoAutomato, simbolo);
		automato.setEstado(this.idEstadoAutomato);
		automato.setEstadoFinal(this.idEstadoAutomato);

		pilhaAutomato.push(automato);
	}

	public void concatenacaoAutomato()
	{
		AutomatoNaoDeterministico automato2 = pilhaAutomato.pop();
		AutomatoNaoDeterministico automato1 = pilhaAutomato.pop();

		ArrayList<Integer> finaisAutomato1 = new ArrayList<Integer>();
		for (Integer key : automato1.getEstadosFinais().keySet()) {
			finaisAutomato1.add(key);
		}

		automato1.removerEstadosFinais();

		Integer idEstado;
		ArrayList<Integer> iniciaisAutomato2 = new ArrayList<Integer>();
		for (Integer key : automato2.getEstados().keySet()) {
			idEstado = automato2.getEstados().get(key).getId();
			if (!automato2.eEstadoInicial(idEstado)) {
				automato1.setEstado(idEstado);
			} else {
				iniciaisAutomato2.add(idEstado);
			}

			if (automato2.eEstadoFinal(idEstado)) {
				automato1.setEstadoFinal(idEstado);
			}
		}

		int origem, destino;
		String simbolo;
		Transicao transicao;
		Iterator<Transicao> iterador = automato2.getTransicoes().iterator();
		while(iterador.hasNext()) {
			transicao = iterador.next();
			origem = transicao.getOrigem().getId();
			destino = transicao.getDestino().getId();
			simbolo = transicao.getSimbolo();

			if (iniciaisAutomato2.contains(origem)) {
				for (Integer novaOrigem : finaisAutomato1) {
					automato1.setTransicao(novaOrigem, destino, simbolo);
				}
			} else if (iniciaisAutomato2.contains(destino)) {
				for (Integer novoDestino : finaisAutomato1) {
					automato1.setTransicao(origem, novoDestino, simbolo);
				}
			} else {
				automato1.setTransicao(origem, destino, simbolo);
			}
		}

		// automato1.imprimirAutomatoFinito();
		pilhaAutomato.push(automato1);
	}

	public void uniaoAutomato()
	{
		AutomatoNaoDeterministico automato2 = pilhaAutomato.pop();
		AutomatoNaoDeterministico automato1 = pilhaAutomato.pop();

		for (Integer idEstado : automato2.getEstados().keySet()) {
			automato1.setEstado(idEstado);
		}

		for (Transicao transicao : automato2.getTransicoes()) {
			automato1.setTransicao(
				transicao.getOrigem().getId(),
				transicao.getDestino().getId(),
				transicao.getSimbolo()
			);
		}

		automato1.setEstado(++this.idEstadoAutomato);
		
		for (Integer idEstado : automato1.getEstadosIniciais().keySet()) {
			automato1.setTransicao(this.idEstadoAutomato, idEstado, "");
		}

		automato1.removerEstadosIniciais();
		automato1.setEstadoInicial(this.idEstadoAutomato);

		for (Integer idEstado : automato2.getEstadosIniciais().keySet()) {
			automato1.setTransicao(this.idEstadoAutomato, idEstado, "");
		}
		
		automato1.setEstado(++this.idEstadoAutomato);
		
		for (Integer idEstado : automato1.getEstadosFinais().keySet()) {
			automato1.setTransicao(idEstado, this.idEstadoAutomato, "");
		}

		automato1.removerEstadosFinais();
		automato1.setEstadoFinal(this.idEstadoAutomato);

		for (Integer idEstado : automato2.getEstadosFinais().keySet()) {
			automato1.setTransicao(idEstado, this.idEstadoAutomato, "");
		}

		// automato1.imprimirAutomatoFinito();
		pilhaAutomato.push(automato1);
	}

	public void fechoKleeneAutomato()
	{
		AutomatoNaoDeterministico automato = pilhaAutomato.pop();
		ArrayList<Integer> estadosIniciais = new ArrayList<Integer>();

		automato.setEstado(++this.idEstadoAutomato);

		for (Integer idEstadoInicial : automato.getEstadosIniciais().keySet()) {
			automato.setTransicao(this.idEstadoAutomato, idEstadoInicial, "");
			estadosIniciais.add(idEstadoInicial);
		}

		automato.removerEstadosIniciais();
		automato.setEstadoInicial(this.idEstadoAutomato);
		automato.setTransicao(this.idEstadoAutomato, ++this.idEstadoAutomato, "");
		automato.setEstado(this.idEstadoAutomato);

		for (Integer idEstadoFinal : automato.getEstadosFinais().keySet()) {
			automato.setTransicao(idEstadoFinal, this.idEstadoAutomato, "");
			for (Integer idEstadoInicial : estadosIniciais) {
				automato.setTransicao(idEstadoFinal, idEstadoInicial, "");
			}
		}

		automato.removerEstadosFinais();
		automato.setEstadoFinal(this.idEstadoAutomato);

		// automato.imprimirAutomatoFinito();
		pilhaAutomato.push(automato);
	}

	// public void converterAutomato()
	// {
	// 	AutomatoNaoDeterministico AFN = pilhaAutomato.pop();
	// 	AutomatoDeterministico AFD = new AutomatoDeterministico();
	// 	HashMap<Integer, HashSet<Integer>> estadosNaoAnalisados = new HashMap<Integer, HashSet<Integer>>();
	// 	HashMap<Integer, HashSet<Integer>> novosEstados = new HashMap<Integer, HashSet<Integer>>();
	// 	int idEstado = 1;

	// 	AFN.imprimirAutomatoFinito();

	// 	estadosNaoAnalisados.put(idEstado, fechoDeLambda(AFN.getEstadosIniciais().keySet(), AFN));
	// 	novosEstados.put(idEstado, fechoDeLambda(AFN.getEstadosIniciais().keySet(), AFN));
	// 	AFD.setEstado(idEstado);
	// 	AFD.setEstadoInicial(idEstado);

	// 	for (Integer key: estadosNaoAnalisados.keySet()) {
	// 		HashSet<Integer> conjunto = estadosNaoAnalisados.get(key);
			
	// 		for (String simbolo : this.alfabetoAutomato) {
	// 			HashSet<Integer> tmp = new HashSet<Integer>();
	// 			for (Integer estado : conjunto) {
	// 				for (Transicao transicao : AFN.getTransicao(estado, simbolo)) {
	// 					tmp.add(transicao.getDestino().getId());
	// 				}
	// 			}
				
	// 			boolean flag = false;
	// 			int novaTransicao;
	// 			for (Integer i : tmp) {
	// 				for (Integer key : novosEstados) {
	// 					for (HashSet<Integer> j : novosEstados.get(key)) {
	// 						if (j.containsKey(i))  {
	// 							flag = true;
	// 							novaTransicao = key;
	// 						}
	// 					}
	// 				}
	// 			}

	// 			if (flag) {
	// 				AFD.setTransicao(idEstado, ++idEstado, simbolo);
	// 				AFD.setEstado(idEstado);
	// 				for (Integer j : fechoDeLambda(tmp, AFN)) {
	// 					if (AFN.eEstadoFinal(j)) {
	// 						AFD.setEstadoFinal(idEstado);
	// 					}
	// 				}
	// 				estadosNaoAnalisados.put(idEstado, fechoDeLambda(tmp, AFN));
	// 			} else {
	// 				AFD.setTransicao(idEstado, idEstado, simbolo);
	// 			}
	// 		}

	// 		estadosNaoAnalisados.remove(conjunto);
	// 	}

	// 	AFD.imprimirAutomatoFinito();
	// }

	public HashSet<Integer> fechoDeLambda(
		Set<Integer> idEstados,
		AutomatoNaoDeterministico automato
	) {
		HashSet<Integer> estadosNaoAnalisados = new HashSet<Integer>();
		for (Integer idEstado : idEstados) {
			estadosNaoAnalisados.add(idEstado);
			PriorityQueue<Integer> fila = new PriorityQueue<Integer>();
			fila.add(idEstado);

			Integer estadoAtual, destino;
			while (!fila.isEmpty()) {
				estadoAtual = fila.remove();
				for (Transicao transicao : automato.getTransicao(estadoAtual, "")) {
					destino = transicao.getDestino().getId();
					if (!estadosNaoAnalisados.contains(destino)) {
						estadosNaoAnalisados.add(destino);
						fila.add(destino);
					}
				}
			}
		}

		return estadosNaoAnalisados;
	}

	public Tag getTag()
	{
		return TAG;
	}
}

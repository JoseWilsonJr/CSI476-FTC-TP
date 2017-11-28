import java.util.HashSet;
import java.util.Iterator;

public class Teste2
{
	public static void main(String[] args) {
		// inicializa um novo autômato
		AutomatoNaoDeterministico newAutomato = new AutomatoNaoDeterministico();
		// faz a leitura da palavra a ser reconhecida
		String simbolo = "11101100100";
		// finaliza a aplicação caso não haja entra de simbolos
		if (simbolo.isEmpty()) {
			System.out.println("Este autômato não aceita transições vazias!");
			System.exit(0);
		}

		HashSet<Transicao> transicao;
		Estado destino = new Estado(0);
		// Definição dos estados do autômato
		newAutomato.setEstado(0);
		newAutomato.setEstado(1);
		newAutomato.setEstado(2);
		newAutomato.setEstado(3);
		newAutomato.setEstado(4);
		newAutomato.setEstado(5);
		newAutomato.setEstado(6);
		newAutomato.setEstado(7);
		// Definindo qual estado é Inicial e quais são estados Finais
		newAutomato.setEstadoInicial(0);
		newAutomato.setEstadoFinal(2);
		newAutomato.setEstadoFinal(4);
		newAutomato.setEstadoFinal(6);
		newAutomato.setEstadoFinal(7);
		int finalEstados[] = {2,4,6,7};
		// Definindo todas as transições do autômato
		// (origem, destino, simbolo)
		newAutomato.setTransicao(0, 0, "0");
		newAutomato.setTransicao(0, 1, "1");
		newAutomato.setTransicao(1, 3, "0");
		newAutomato.setTransicao(1, 5, "1");
		newAutomato.setTransicao(2, 0, "0");
		newAutomato.setTransicao(2, 1, "1");
		newAutomato.setTransicao(3, 2, "0");
		newAutomato.setTransicao(3, 4, "1");
		newAutomato.setTransicao(4, 3, "0");
		newAutomato.setTransicao(4, 5, "1");
		newAutomato.setTransicao(5, 6, "0");
		newAutomato.setTransicao(5, 7, "1");
		newAutomato.setTransicao(6, 2, "0");
		newAutomato.setTransicao(6, 4, "1");
		newAutomato.setTransicao(7, 6, "0");
		newAutomato.setTransicao(7, 7, "1");
		// uma mensagem de apresentação da aplicação
		headerMessage(simbolo, newAutomato, finalEstados);
		
		int i = 0;
		int origin = 0;
		
		while (i < simbolo.length()) {
			if (!(simbolo.charAt(i) == ' ')) {
				transicao = newAutomato.getTransicao(origin, "" + simbolo.charAt(i));

				Iterator<Transicao> it = transicao.iterator();
				while (it.hasNext()) {
					destino = it.next().getDestino();
				}

				origin = destino.getId();
				System.out.println("Leu o símbolo \"" + simbolo.charAt(i) + "\" foi para o \"" + newAutomato.getEstado(origin).getNome());
				i++;
			} else {
				System.out.println("Este autômato não aceita transições vazias!!!");
				System.exit(0);
			}
		} // end while
		// Exibe o estado em que o autômato se encontra ao final da leitura da palavra.
		finalEstadoOfAutomato(simbolo, newAutomato, origin);
	} // end main

	// Mensagem de apresentação da aplicação
	public static void headerMessage(String simbolo, AutomatoNaoDeterministico myAutomato, int finalEstados[])
	{
		System.out.println("\nDFA reconhecedor da expressão regular (0+1)*1(0+1)(0+1) ");
		System.out.println("Verifica a entrada \"" + simbolo + "\"\n");
		System.out.println("Definição dos Estados:\n\t" + myAutomato.getEstadoInicial(0).getNome());
		for (int j = 0; j < myAutomato.getSizeEstadosFinais(); j++) {
			System.out.println("\n\t" + myAutomato.getEstadoFinal(finalEstados[j]).getNome() + "\n");
		} // for
	}	

	// Exibe a estado final do autômato e indica o reconhecimento ou não da palavra fornecida.
	public static void finalEstadoOfAutomato(String simbolo, Automato myAutomato, int origin)
	{
		if (myAutomato.eEstadoFinal(origin)) {
			System.out.println("\nA entrada \"" + simbolo + "\" foi aceita !!!\n");
		} else {
			System.out.println("\nA entrada \"" + simbolo + "\" foi rejeitada !!!\n");
		}
	}
}
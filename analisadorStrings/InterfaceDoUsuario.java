/**
*@author José Wilson da Silva Júnior 15.1.8156
*/
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;

public class InterfaceDoUsuario
{	
	HashMap<Integer, Tag> tagsCadastradas = new HashMap<Integer, Tag>();
	ArrayList<String> tags = new ArrayList<String>();
	ArrayList<String> nomesTags = new ArrayList<String>();
	String arquivoDeSaida;
	int idTag = 0;

	/*
		Interpretador que faz a interface com o usuário.
		Esse método possui um conjunto de casos que referenciam
		os comandos que usuário irá digitar no modo de 
		interpretação do sistema. Cada comando digitado
		pelo usuário levará a uma ação definida nesse
		interpretador.
	*/
	public void interpretador()
	{
		String comando;
		String parametro;
		Scanner scanner = new Scanner(System.in);
		
		do {
			comando = scanner.nextLine();
			parametro = "";
			if (comando.length() >= 2) {
				if (comando.length() >= 3) {
					parametro = comando.substring(3);
				}
				
				switch (comando.substring(0, 2)) {
					case ":f":
						if (!parametro.isEmpty() && comando.charAt(2) == ' ') {
							String string = carregarStringArquivo(parametro);
							classificarTags(string);
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					case ":l":
						if (!parametro.isEmpty() && comando.charAt(2) == ' ') {
							carregarArquivoDeTags(parametro);
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					case ":o":
						if (!parametro.isEmpty() && comando.charAt(2) == ' ') {
							setArquivoDeSaida(parametro);
							System.out.println("[INFO] Arquivo de saída para divisão em tags definido.");
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					case ":p":
						if (!parametro.isEmpty() && comando.charAt(2) == ' ') {
							classificarTags(parametro);
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					case ":q":
						if (comando.length() == 2) {
							System.out.println("[INFO] Programa encerrado.");
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					case ":s":
						if (!parametro.isEmpty() && comando.charAt(2) == ' ') {
							salvarTags(parametro);
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					case ":t":						
						if (comando.length() == 2) {
							System.out.println("[INFO] Tags validas já cadastradas:");
							imprimirTags(tags);
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
					default:
						if (!comando.substring(0, 1).equals(":")) {
							if (validarTag(comando)) {
								tags.add(comando);
								System.out.println("[INFO] Definição de tag realizada com sucesso.");
							} else {
								System.out.println("[ERROR] Definição de tag inválida ou já cadastrada.");
							}
						} else {
							System.out.println("[ERROR] Comando inválido, insira um comando válido.");
						}
						break;
				}
			} else {
				System.out.println("[ERROR] Comando inválido, insira um comando válido.");
			}
		} while (comando.length() < 2 || !comando.substring(0, 2).equals(":q") || comando.length() > 2);
		
		scanner.close();
	}

	/*
		Método para carregar o arquivo de tags, utilzando o leitor
		de tags da classe GerenciadorDeArquivo.
	*/

	public void carregarArquivoDeTags(String nomeArquivo)
	{
		try {
			GerenciadorDeArquivo gerenciadorArquivo = new GerenciadorDeArquivo();
			ArrayList<Integer> result = gerenciadorArquivo.leitorDeTags(nomeArquivo, tags, nomesTags);
			for (Tag tmp : gerenciadorArquivo.getTag()) {
				tagsCadastradas.put(++idTag, tmp);
			}
			if (result.isEmpty()) {
				System.out.println("[INFO] As definições de tags foram carregadas.");
			} else {
				String msg = "";
				for (Integer linha : result) {
					msg = msg + ", " + linha;
				}
				System.out.println("[ERROR] Tags nas linhas" + msg.substring(1) + " do arquivo não foram carregadas.");
			}
		} catch (IOException e) {
			System.out.println("[ERROR] Não foi possível fazer a leitura do arquivo.");
		}
	}
	
	/*
		Método para salvar as tags do sistema, utilizando
		o escritor de tags da classe GerenciadorDeArquivo.
	*/
	public void salvarTags(String nomeArquivo)
	{
		try {
			GerenciadorDeArquivo gerenciadorArquivo = new GerenciadorDeArquivo();
			gerenciadorArquivo.escritorDeTags(nomeArquivo, tags);
			System.out.println("[INFO] Tags salvas com sucesso.");
		} catch (IOException e) {
			System.out.println("[ERROR] Não foi possível salvar tags no arquivo solicitado.");
		}
	}
	
	/*
		Chama o validador de tag da classe CadastroDeTag.
	*/
	public boolean validarTag(String tag) {
		CadastroDeTag cadastroTag = new CadastroDeTag();
		boolean result = cadastroTag.validarDefinicaoTag(tag, nomesTags);
		if (result) {
			tagsCadastradas.put(++idTag, cadastroTag.getTag());
		}
		return result;
	}

	/*
		Método set do atributo arquivoDeSaida
	*/
	public void setArquivoDeSaida(String arquivoDeSaida)
	{
		this.arquivoDeSaida = arquivoDeSaida;
	}

	
	public void classificarTags(String palavra) {
		AutomatoNaoDeterministico AFN;
		Integer estadoAtual = 0, posicaoAtual = 0, contador = 0;
		String simbolo, divisaoTags = "";
		boolean teste = true;
		
		do {
			for (Integer tmp : tagsCadastradas.keySet()) {
				AFN = tagsCadastradas.get(tmp).getAFN();
				
				for (Integer j : AFN.getEstadosIniciais().keySet()) {
					estadoAtual = j;
				}

				contador = busca(posicaoAtual, palavra, estadoAtual, 0, AFN);

				if (contador > posicaoAtual) {
					divisaoTags = divisaoTags + " " + tagsCadastradas.get(tmp).getNomeTag();
					teste = false;
					break;
				}
			}

			if (teste) {
				break;
			}

			posicaoAtual++;

		} while (posicaoAtual < palavra.length());
		
		if (!divisaoTags.isEmpty()) {
			System.out.println(divisaoTags);
		} else {
			System.out.println("[ERROR] String de entrada não reconhecida pelas tags cadastradas.");
		}
	}

	public int busca(
		int posicaoAtual,
		String palavra,
		int estadoAtual,
		int contador,
		AutomatoNaoDeterministico AFN
	) {
		int auxiliar = 0;
		
		if (AFN.eEstadoFinal(estadoAtual) && posicaoAtual > contador) {
			contador = posicaoAtual;
		}

		for (Transicao transicao : AFN.getTransicoes()) {
			String simbolo;
			if (posicaoAtual == palavra.length()) {
				simbolo = palavra.substring(posicaoAtual);
			} else {
				simbolo = palavra.substring(posicaoAtual, posicaoAtual + 1);
			}


			if (transicao.getOrigem().getId() == estadoAtual && transicao.getSimbolo().equals("")) {
				auxiliar = busca(
					posicaoAtual,
					palavra,
					transicao.getDestino().getId(),
					contador,
					AFN
				);
			} else if (transicao.getOrigem().getId() == estadoAtual && transicao.getSimbolo().equals(simbolo)) {
				auxiliar = busca(
					posicaoAtual + 1,
					palavra,
					transicao.getDestino().getId(),
					contador,
					AFN
				);
			}

			if (auxiliar > contador) {
				contador = auxiliar;
			}
		}

		return contador;
	}

	public String carregarStringArquivo(String nomeArquivo)
	{
		try {
			GerenciadorDeArquivo gerenciadorArquivo = new GerenciadorDeArquivo();
			return gerenciadorArquivo.lerArquivo(nomeArquivo);
		} catch (IOException e) {
			System.out.println("[ERROR] Não foi possível ler o arquivo solicitado.");
			return "";
		}
	}

	public void imprimirTags(ArrayList<String> tags)
	{
		for (String tag : tags) {
			System.out.println(tag);
		}
	}
	/*
		Método principal para iniciar aplicação.
	*/
	public static void main(String[] args) {
		InterfaceDoUsuario interfaceUsuario = new InterfaceDoUsuario();
		interfaceUsuario.interpretador();
	}
}

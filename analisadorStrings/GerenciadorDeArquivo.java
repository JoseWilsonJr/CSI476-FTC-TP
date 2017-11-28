/**
*@author José Wilson da Silva Júnior 15.1.8156
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GerenciadorDeArquivo
{
    ArrayList<Tag> cadastroTag = new ArrayList<Tag>();

    /*
        Chama o validador de tag da classe CadastroDeTag.
    */
    public boolean validarTag(String tag, ArrayList<String> nomesTags) {
        CadastroDeTag cadastro = new CadastroDeTag();
        boolean result =  cadastro.validarDefinicaoTag(tag, nomesTags);
        if (result) {
            cadastroTag.add(cadastro.getTag());
        }
        return result;
    }

    /*
        Método responsável por ler as tags do arquivo informado por parâmetro
        validando se a definição é válida e adicionando no ArrayList enviado
        por parâmetro.
    */
	public ArrayList<Integer> leitorDeTags(String nomeArquivo, ArrayList<String> tags, ArrayList<String> nomesTags) throws IOException
	{
		BufferedReader buffRead = new BufferedReader(new FileReader(nomeArquivo));
        
        String linha = buffRead.readLine();
        ArrayList<Integer> linhasErro = new ArrayList<Integer>();
        int i = 1;
        while (true) {
            if (linha != null) {
                if (validarTag(linha, nomesTags)) {
                    tags.add(linha);
                } else {
                    linhasErro.add(i);
                }
            } else {
                break;
            }
            linha = buffRead.readLine();
            i++;
        }
        buffRead.close();
        
        return linhasErro;
    }

    /*
        Método responsável por escrever as tags do ArrayList enviado por parâmetro
        no arquivo com o nome enviado por parâmetro.
    */
    public void escritorDeTags(String nomeArquivo, ArrayList<String> tags) throws IOException
    {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nomeArquivo));
        
        for (String tag: tags) {
          buffWrite.append(tag + "\n");
        }
        
        buffWrite.close();
    }

    public String lerArquivo(String nomeArquivo) throws IOException
    {
        BufferedReader buffRead = new BufferedReader(new FileReader(nomeArquivo));
        String linha = buffRead.readLine();
        buffRead.close();
        
        return linha;
    }

    public ArrayList<Tag> getTag()
    {
        return cadastroTag;
    }
}

/**
*@author José Wilson da Silva Júnior 15.1.8156
*@author Leonardo Barcelos Nardy Dias - 13.2.8646
*@author Lucas Teotônio Lima - 15.1.5902
*/

public class Estado
{
	private int id;
	private String nome;

	public Estado(int id)
	{
		this.id = id;
		this.nome = "q"+ id;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String name)
	{
		this.nome = nome;
	}
}
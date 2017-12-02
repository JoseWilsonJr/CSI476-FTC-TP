/**
*@author José Wilson da Silva Júnior 15.1.8156
*@author Leonardo Barcelos Nardy Dias - 13.2.8646
*@author Lucas Teotônio Lima - 15.1.5902
*/

public class Transicao
{
	private Estado origem;
	private Estado destino;
	private String simbolo;

	public Transicao(Estado origem, Estado destino, String simbolo)
	{
		this.origem = origem;
		this.destino = destino;
		this.simbolo = simbolo;
	}

	public Estado getOrigem()
	{
		return origem;
	}

	public void setOrigem(Estado origem)
	{
		this.origem = origem;
	}

	public Estado getDestino()
	{
		return destino;
	}

	public void setDestino(Estado destino)
	{
		this.destino = destino;
	}

	public String getSimbolo()
	{
		return simbolo;
	}

	public void setSimbolo(String simbolo)
	{
		this.simbolo = simbolo;
	}

	public boolean eIgualAFD(Transicao transicao)
	{
		if (this.origem.getId() == transicao.getOrigem().getId()
			&& this.simbolo.equals(transicao.getSimbolo())
		) {
			return true;
		} else {
			return false;
		}
	}

	public boolean eIgualAFN(Transicao transicao)
	{
		if (this.origem.getId() == transicao.getOrigem().getId()
			&& this.destino.getId() == transicao.getDestino().getId()
			&& this.simbolo.equals(transicao.getSimbolo())
		) {
			return true;
		} else {
			return false;
		}
	}
}
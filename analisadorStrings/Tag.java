/**
*@author José Wilson da Silva Júnior - 15.1.8156
*@author Leonardo Barcelos Nardy Dias - 13.2.8646
*@author Lucas Teotônio Lima - 15.1.5902
*/
public class Tag {
	
	private AutomatoNaoDeterministico AFN;
	private String nomeTag;


	public Tag() {}

	public Tag(AutomatoNaoDeterministico afn, String tag)
	{
		AFN = afn;
		nomeTag = tag;
	}

	public AutomatoNaoDeterministico getAFN()
	{
		return AFN;
	}

	public void setAFN(AutomatoNaoDeterministico afn)
	{
		AFN = afn;
	}

	public String getNomeTag()
	{
		return nomeTag;
	}

	public void setNomeTag(String tag)
	{
		nomeTag = tag;
	}
}
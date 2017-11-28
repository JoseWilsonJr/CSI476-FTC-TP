/**
*@author José Wilson da Silva Júnior - 15.1.8156
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
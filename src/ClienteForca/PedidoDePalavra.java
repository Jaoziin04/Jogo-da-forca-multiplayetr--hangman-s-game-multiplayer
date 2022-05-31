package ClienteForca;

public class PedidoDePalavra extends Comunicado
{

    private String palavra;

    public PedidoDePalavra(String palavra)
    {
        this.palavra = palavra;
    }

    public String getPalavra()
    {
        return palavra;
    }

    public void setPalavra(String palavra)
    {
        this.palavra = palavra;
    }


}

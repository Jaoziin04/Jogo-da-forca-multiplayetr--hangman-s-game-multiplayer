package ClienteForca;

public class PedidoDeLetra extends Comunicado
{

    private char letra;

    public char getLetra()
    {
        return letra;
    }

    public void setLetra(char letra)
    {
        this.letra = letra;
    }

    public PedidoDeLetra(char letra)
    {
        this.letra = letra;
    }

}

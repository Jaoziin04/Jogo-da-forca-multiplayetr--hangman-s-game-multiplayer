package ClienteForca;

public class ComunicadoDeVitoria extends Comunicado
{
    private boolean vencedor = false;

    public ComunicadoDeVitoria(boolean vencedor)
    {
        this.vencedor = vencedor;
    }

    public String Vitoria()
    {
        String s = "Você venceu PARABÉNS!!!!";
        return this.vencedor + s;
    }
}

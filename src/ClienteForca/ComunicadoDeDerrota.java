package ClienteForca;

public class ComunicadoDeDerrota extends Comunicado
{
    private boolean isPerdedor;
    private int perdedor;

    public ComunicadoDeDerrota(int perdedor)
    {
        this.perdedor = perdedor;
        this.isPerdedor = false;
    }


    public ComunicadoDeDerrota(boolean isPerdedor)
    {
        this.isPerdedor = isPerdedor;
    }


    public String Derrota()
    {

        String s = "Você antingiu o máximo de erros, por isso perdeu. Mais sorte na póxima vez";
        return this.isPerdedor + s;
    }

}

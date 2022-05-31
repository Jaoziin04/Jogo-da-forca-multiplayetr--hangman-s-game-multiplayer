package ClienteForca;

public class ComunicadoDeVez extends Comunicado
{

    ComunicadoDeDados comunicadoDeDados;

    public ComunicadoDeDados getComunicadoDeDados()
    {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados)
    {

        this.comunicadoDeDados = comunicadoDeDados;
    }


    public ComunicadoDeVez(ComunicadoDeDados comunicadoDeDados)
    {
        this.comunicadoDeDados = comunicadoDeDados;
    }

}

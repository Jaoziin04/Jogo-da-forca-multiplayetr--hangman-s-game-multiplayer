package ClienteForca;

public class ComunicadoDeInicio extends Comunicado
{
    public ComunicadoDeDados getComunicadoDeDados()
    {
        return comunicadoDeDados;
    }

    private ComunicadoDeDados comunicadoDeDados;

    public ComunicadoDeInicio(ComunicadoDeDados comunicadoDeDados)
    {
        this.comunicadoDeDados = comunicadoDeDados;
    }
}

package ClienteForca;

public class ComunicadoAcertouDeLetra extends Comunicado
{

    ComunicadoDeDados comunicadoDeDados;

    public ComunicadoAcertouDeLetra(ComunicadoDeDados comunicadoDeDados)
    {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

}

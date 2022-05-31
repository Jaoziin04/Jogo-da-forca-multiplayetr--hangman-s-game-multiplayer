package ClienteForca;

public class ComunicadoErrouLetra extends Comunicado
{
    ComunicadoDeDados comunicadoDeDados;

    public ComunicadoErrouLetra(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

}

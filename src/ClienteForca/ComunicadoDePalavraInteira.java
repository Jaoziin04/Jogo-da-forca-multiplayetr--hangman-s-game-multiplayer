package ClienteForca;

public class ComunicadoDePalavraInteira extends Comunicado
{
    private boolean certo;
    private ComunicadoDeDados comunicadoDeDados;

    public ComunicadoDePalavraInteira(boolean certo, ComunicadoDeDados comunicadoDeDados) {
        this.certo = certo;
        this.comunicadoDeDados = comunicadoDeDados;
    }

    public boolean isCerto() {
        return certo;
    }

    public void setCerto(boolean certo) {
        this.certo = certo;
    }

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }


}

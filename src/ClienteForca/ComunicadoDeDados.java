package ClienteForca;
import ForcaJogo.*;


public class ComunicadoDeDados extends Comunicado
{
    private  int player1;
    private  int player2;
    private  int player3;
    private  int players;
    private Palavra palavra;
    private Tracinhos tracinhos;
    private ControladorDeErros controladorDeErros;
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
    private ControladorDePalvrasJaDigitadas controladorDePalvrasJaDigitadas;

  public ComunicadoDeDados(
          Palavra palavra,
          Tracinhos tracinhos,
          ControladorDeErros controladorDeErros,
          ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas, ControladorDePalvrasJaDigitadas controladorDePalvrasJaDigitadas)
  {
        this.palavra = palavra;
        this.tracinhos = tracinhos;
        this.controladorDeErros = controladorDeErros;
        this.controladorDeLetrasJaDigitadas = controladorDeLetrasJaDigitadas;
        this.controladorDePalvrasJaDigitadas = controladorDePalvrasJaDigitadas;
    }



        public Palavra getPalavra()
        {
        return this.palavra;
        }

        public Tracinhos getTracinhos()
        {
        return this.tracinhos;
        }

        public ControladorDeErros getControladorDeErros()
        {
        return this.controladorDeErros;
        }

        public ControladorDeLetrasJaDigitadas getControladorDeLetrasJaDigitadas()
        {
        return this.controladorDeLetrasJaDigitadas;
        }




    public int getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(int player1)
    {
        this.player1 = player1;
    }

    public int getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(int player2)
    {
        this.player2 = player2;
    }

    public int getPlayer3()
    {
        return player3;
    }

    public void setPlayer3(int player3)
    {
        this.player3 = player3;
    }

    public int getPlayers()
    {
        return players;
    }

    public void setPlayers(int players)
    {
        this.players = players;
    }

    public ControladorDePalvrasJaDigitadas getControladorDePalvrasJaDigitadas()
    {
        return controladorDePalvrasJaDigitadas;
    }


    /*

    public ComunicadoDeDados(int usuarios) // construtor
    {
        try // instanciando variaveis
        {
            this.player1 = players - 3;
            this.player2 = players - 2;
            this.player3 = players - 1;
            this.players = players / 3;
            this.palavra = BancoDePalavras.getPalavraSorteada();
            this.controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
            this.controladorDeErros = new ControladorDeErros((int)((double)this.palavra.getTamanho() * 0.6D));
            this.tracinhos = new Tracinhos(this.palavra.getTamanho());
            this.controladorDePalvrasJaDigitadas = new ControladorDePalvrasJaDigitadas();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
     */



}

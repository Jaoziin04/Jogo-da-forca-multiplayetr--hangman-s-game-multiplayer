package ServervidorForca;

import ClienteForca.ComunicadoDeDados;

import java.io.*;
import java.net.*;
import java.util.*;
import ClienteForca.*;
import ForcaJogo.*;

public class SupervisoraDeConexao extends Thread
{

    private int idPlayer1 = -1;
    private int idPlayer2 = -1;
    private int idPlayer3 = -1;
    private int idPlayerAtual = -1;
    private Parceiro usuario;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;
    private Palavra palavra;
    private Tracinhos tracinhos;
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
    private ControladorDeErros controladorDeErros;
    private ControladorDePalvrasJaDigitadas controladorDePalvrasJaDigitadas;
    private ComunicadoDeDados comunicadoDeDados;


    public SupervisoraDeConexao
            (Socket conexao, ArrayList<Parceiro> usuarios)
            throws Exception
            {
                if (conexao == null)
                    throw new Exception("Conexao ausente");

                if (usuarios == null)
                    throw new Exception("Usuarios ausentes");

                this.conexao = conexao;
                this.usuarios = usuarios;
            }

    public void run() {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
                    new ObjectOutputStream(
                            this.conexao.getOutputStream());
        } catch (Exception erro)
        {
            return;
        }

        ObjectInputStream receptor = null;
        try
        {
            receptor =
                    new ObjectInputStream(
                            this.conexao.getInputStream());
        } catch (Exception err0)
        {
            try
            {
                transmissor.close();
            } catch (Exception falha) {} // so tentando fechar antes de acabar a thread

            return;
        }

        try
        {
            this.usuario =
                    new Parceiro(this.conexao,
                            receptor,
                            transmissor);
        } catch (Exception erro) {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.usuarios) // tenta sincronizar os usuarios
            {
                this.usuarios.add(this.usuario); // adiciona usuario

                if(this.usuarios.size() == 3)
                {
                    this.palavra = BancoDePalavras.getPalavraSorteada();
                    this.controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
                    this.controladorDeErros = new ControladorDeErros((int)((double)this.palavra.getTamanho() * 0.6D));
                    this.tracinhos = new Tracinhos(this.palavra.getTamanho());
                    this.controladorDePalvrasJaDigitadas = new ControladorDePalvrasJaDigitadas();


                    for (Parceiro jogadorzin :
                            this.usuarios)
                    {
                        jogadorzin.receba(new ComunicadoDeInicio(new ComunicadoDeDados(this.palavra, this.tracinhos, this.controladorDeErros,
                                this.controladorDeLetrasJaDigitadas, this.controladorDePalvrasJaDigitadas)));

                    }

                    this.usuarios.get(0).receba(new ComunicadoDeVez(new ComunicadoDeDados(this.palavra, this.tracinhos, this.controladorDeErros,
                            this.controladorDeLetrasJaDigitadas, this.controladorDePalvrasJaDigitadas)));  //Inicia o jogo pro primeiro jogador que entra

                }



                 idPlayerAtual =this.usuario.getId();




            }
            for (; ; )
            {
                Comunicado comunicado = this.usuario.envie();

                if (comunicado == null)
                    return;

                else if(comunicado instanceof ComunicadoDeDados)
                {
                    this.comunicadoDeDados = (ComunicadoDeDados)comunicado;
                    this.idPlayer1 = this.comunicadoDeDados.getPlayer1();
                    this.idPlayer2 = this.comunicadoDeDados.getPlayer2();
                    this.idPlayer3 = this.comunicadoDeDados.getPlayer3();
                }

                else  if(comunicado instanceof  ComunicadoDeDesligamento)
                {
                    try // exclui o jogador do jogo
                    {
                        this.idPlayer1 = this.usuario.getId();
                        if(idPlayerAtual == idPlayer1)
                            this.comunicadoDeDados.setPlayer1(-1);
                        else if (idPlayerAtual == idPlayer2)
                            this.comunicadoDeDados.setPlayer2(-1);
                        else if (idPlayerAtual == idPlayer3)
                            this.comunicadoDeDados.setPlayer3(-1);
                    }
                    catch (Exception err)
                    {
                        err.printStackTrace();
                    }
                    //this.LiberarProxPlayer();
                }

                else if(comunicado instanceof PedidoDePalavra)
                {
                    PedidoDePalavra pedidoDePalavra = (PedidoDePalavra) comunicado;
                    String palavra =  pedidoDePalavra.getPalavra().toUpperCase();
                    if(this.comunicadoDeDados.getControladorDePalvrasJaDigitadas().isJaDigitada(palavra)) // se a palavra já foi digitada
                        this.usuarios.get(idPlayerAtual).receba(new ComunicadoDePalavraJaDigitada());
                    else
                    {
                        this.comunicadoDeDados.getControladorDePalvrasJaDigitadas().registre(palavra); // registra a palavra no ControladorDePalavrasJaDigitadas
                        if(palavra == this.comunicadoDeDados.getPalavra().toString())
                        {
                            this.usuarios.get(idPlayerAtual).receba(new ComunicadoDePalavraInteira(true, this.comunicadoDeDados));
                            this.Perdeu(false);
                        }
                        else
                        {
                            try // exclui o jogador do jogo
                            {
                                this.idPlayer1 = this.usuario.getId();
                                if(idPlayerAtual == idPlayer1)
                                    this.comunicadoDeDados.setPlayer1(-1);
                                else if (idPlayerAtual == idPlayer2)
                                    this.comunicadoDeDados.setPlayer2(-1);
                                else if (idPlayerAtual == idPlayer3)
                                    this.comunicadoDeDados.setPlayer3(-1);
                            }
                            catch (Exception err)
                            {
                                err.printStackTrace();
                            }
                            ((Parceiro)this.usuarios.get(idPlayerAtual)).receba(new ComunicadoDePalavraInteira(false,
                                    this.comunicadoDeDados));
                            this.liberarProxPlayerExclusao();
                        }
                    }
                }
                else if (comunicado instanceof PedidoDeLetra)
                {
                    PedidoDeLetra pedidoDeLetra = (PedidoDeLetra) comunicado;

                    char letra = Character.toUpperCase(pedidoDeLetra.getLetra());

                    if (this.comunicadoDeDados.getTracinhos().isAindaComTracinhos()
                            && !this.comunicadoDeDados.getControladorDeErros().isAtingidoMaximoDeErros())
                    {
                        try
                        {
                            if (this.comunicadoDeDados.getControladorDeLetrasJaDigitadas().isJaDigitada(letra))
                            {
                                ((Parceiro)this.usuarios.get(idPlayerAtual)).receba(new ComunicadoDeLetraJaDigitada());
                            }
                            else
                            {
                                this.comunicadoDeDados.getControladorDeLetrasJaDigitadas().registre(letra);
                                int qtd = this.comunicadoDeDados.getPalavra().getQuantidade(letra);
                                if (qtd == 0)
                                {
                                    this.comunicadoDeDados.getControladorDeErros().registreUmErro();
                                    if (this.comunicadoDeDados.getControladorDeErros().isAtingidoMaximoDeErros())
                                    {
                                        this.Perdeu(true);
                                    }
                                    else
                                    {
                                        ((Parceiro)this.usuarios.get(idPlayerAtual)).receba(new ComunicadoErrouLetra(this.comunicadoDeDados));
                                        //this.LiberarProxPlayer();
                                    }
                                }
                                else
                                {
                                    for(int i = 0; i < qtd; ++i) // percorre a palavra
                                    {
                                        int posicao = this.comunicadoDeDados.getPalavra().getPosicaoDaIezimaOcorrencia(i, letra); // intanciado a posição
                                        this.comunicadoDeDados.getTracinhos().revele(posicao, letra); // revela a letra na tela
                                    }

                                    if (!this.comunicadoDeDados.getTracinhos().isAindaComTracinhos())
                                    {
                                        ((Parceiro)this.usuarios.get(idPlayerAtual)).receba(new ComunicadoDeVitoria(false));
                                        this.Perdeu(false);
                                    }
                                    else
                                    {
                                        ((Parceiro)this.usuarios.get(idPlayerAtual)).receba(new ComunicadoAcertouDeLetra(this.comunicadoDeDados));
                                        //this.LiberarProxPlayer();
                                    }
                                }
                            }
                        } catch (Exception errou)
                        {
                            System.err.println(errou.getMessage());
                        }
                    }
                }

                else if(comunicado instanceof ComunicadoDeVez)
                {
                    int proxJog = this.usuarios.indexOf(this.usuario) + 1; // soma um ao indice do primeiro jogador

                    if (proxJog == 3)
                    {
                        proxJog = 0; // volta ao playe1
                    }
                    else if(proxJog == 2 && this.usuarios.size() == 2)
                    {
                        proxJog = 0;
                    }

                    comunicadoDeDados = ((ComunicadoDeVez)comunicado).getComunicadoDeDados();

                    this.usuarios.get(proxJog).receba(new ComunicadoDeVez(comunicadoDeDados));
                }

                else if(comunicado instanceof ComunicadoDePassouVez)
                {
                    int proxPlay = this.usuarios.indexOf(this.usuario) + 1;

                    if(proxPlay == 3)
                        proxPlay = 0;

                    this.usuarios.get(1).receba(new ComunicadoDeVez(comunicadoDeDados));
                }


                else if (comunicado instanceof PedidoParaSair)  // se dentro do comunicado, há um pedido pra sair
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove(this.usuario); // remove o usuario
                    }
                    this.usuario.adeus();
                }


            }
        } catch (Exception erro)
        {
            try
            {
                transmissor.close();
                receptor.close();
            } catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }

    private void liberarProxPlayerExclusao()
    {
        try {
            this.idPlayerAtual = this.usuario.getId();

            if (this.idPlayerAtual == this.idPlayer1)
            {
                if (this.idPlayer2 != -1)
                {
                    if (this.idPlayer3 == -1)
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer2)).receba(new ComunicadoDeVitoria(true));
                    }
                    else
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer2)).receba(new ComunicadoDeVez(this.comunicadoDeDados));
                    }
                }
                else
                {
                    ((Parceiro)this.usuarios.get(this.idPlayer3)).receba(new ComunicadoDeVitoria(true));
                }
            }
            else if (this.idPlayerAtual == this.idPlayer2)
            {
                if (this.idPlayer3 != -1)
                {
                    if (this.idPlayer1 == -1)
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer3)).receba(new ComunicadoDeVitoria(true));
                    } else
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer3)).receba(new ComunicadoDeVez(this.comunicadoDeDados));
                    }
                }
                else
                {
                    ((Parceiro)this.usuarios.get(this.idPlayer1)).receba(new ComunicadoDeVitoria(true));
                }
            }
            else if (this.idPlayerAtual == this.idPlayer3)
            {
                if (this.idPlayer1 != -1)
                {
                    if (this.idPlayer2 == -1)
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer1)).receba(new ComunicadoDeVitoria(true));
                    }
                    else
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer1)).receba(new ComunicadoDeVez(this.comunicadoDeDados));
                    }
                }
                else
                {
                    ((Parceiro)this.usuarios.get(this.idPlayer2)).receba(new ComunicadoDeVitoria(true));
                }
            }
        } catch (Exception errin) {
            errin.printStackTrace();
        }
    }

    private void Perdeu(boolean b)
    {
        try
        {
            this.idPlayerAtual = this.usuario.getId();
            if (!b)
            {
                if (this.idPlayerAtual != this.idPlayer1 && this.idPlayer1 != -1)
                {
                    if (this.idPlayerAtual != this.idPlayer2)
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer1)).receba(new ComunicadoDeDerrota(3));
                    } else
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer1)).receba(new ComunicadoDeDerrota(2));
                    }
                }

                if (this.idPlayerAtual != this.idPlayer2 && this.idPlayer2 != -1)
                {
                    if (this.idPlayerAtual != this.idPlayer1)
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer2)).receba(new ComunicadoDeDerrota(3));
                    } else
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer2)).receba(new ComunicadoDeDerrota(1));
                    }
                }

                if (this.idPlayerAtual != this.idPlayer3 && this.idPlayer3 != -1) {

                    if (this.idPlayerAtual != this.idPlayer2)
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer3)).receba(new ComunicadoDeDerrota(1));
                    } else
                    {
                        ((Parceiro)this.usuarios.get(this.idPlayer3)).receba(new ComunicadoDeDerrota(2));
                    }
                }
            }
            else
            {
                if (this.idPlayer1 != -1) {
                    ((Parceiro)this.usuarios.get(this.idPlayer1)).receba(new ComunicadoDeDerrota(true));
                }

                if (this.idPlayer2 != -1) {
                    ((Parceiro)this.usuarios.get(this.idPlayer2)).receba(new ComunicadoDeDerrota(true));
                }

                if (this.idPlayer3 != -1) {
                    ((Parceiro)this.usuarios.get(this.idPlayer3)).receba(new ComunicadoDeDerrota(true));
                }
            }
        }
        catch (Exception er)
            {
                er.printStackTrace();
            }

    }


}


package ClienteForca;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Locale;


public class Cliente
{
    public static final String HOST_PADRAO = "localhost";
    public static final int PORTA_PADRAO = 3000;


    public static void main(String[] args) throws Exception {
        if (args.length > 2) {
            System.err.println("Uso esperado: java Cliente [HOST [PORTA]]\n");
            return;
        }

        Socket conexao = null;
        try
        {
            String host = Cliente.HOST_PADRAO;
            int porta = Cliente.PORTA_PADRAO;

            if (args.length > 0)
                host = args[0];

            if (args.length == 2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket(host, porta);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor = null;
        try {
            transmissor =
                    new ObjectOutputStream(
                            conexao.getOutputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor = null;
        try {
            receptor =
                    new ObjectInputStream(
                            conexao.getInputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor = null;
        try {
            servidor =
                    new Parceiro(conexao, receptor, transmissor);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try {
            tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
        } catch (Exception erro) {
        } // sei que servidor foi instanciado

        tratadoraDeComunicadoDeDesligamento.start();

        ComunicadoDeDados comunicadoDeDados = null; // instanciando o ComunicadoDeDados


        Comunicado comunicado = null; // instanciando o Comunicado

        try
        {
            System.out.println("Aguardando jogadores!\n");

            do {
                comunicado = (Comunicado) servidor.espie(); // busca algum comunicado

            }
            while (!(comunicado instanceof ComunicadoDeInicio)); // repete enquanto não tem comunicado
            ComunicadoDeInicio result = (ComunicadoDeInicio) servidor.envie(); // envia o comunicado de inicio
            comunicadoDeDados = result.getComunicadoDeDados();


            System.out.println("Grupo Completo!");
        } catch (Exception err) {
        }

        boolean emGame = true;
        do
        {


            try {
                servidor.receba(comunicadoDeDados);
            } catch (Exception e) {
                e.printStackTrace();
            }


            char opcao = ' ';

            try
            {

                System.out.println("Aguarde sua vez de jogar");

                do
                {
                    comunicado = servidor.espie(); // espera um comunicado
                }
                while (!(comunicado instanceof ComunicadoDeVez) && !(comunicado instanceof ComunicadoDeVitoria) &&
                        !(comunicado instanceof ComunicadoDeDerrota)); // enquanto comunicado não está instanciado com ComunicadoDeDerrota, ComunicadoDeVitoria e de Vez
                comunicado = servidor.envie();                                                                                                     // e com ComunicadoDeVez
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (comunicado instanceof ComunicadoDeVez) // se o comunicado está instaciado com ComunicadoDeVez
            {
                System.out.println("Sua vez de jogar!!!");
                ComunicadoDeVez vez = (ComunicadoDeVez) comunicado;
                comunicadoDeDados = vez.getComunicadoDeDados();


                System.out.println("Palavra......: " + comunicadoDeDados.getTracinhos()); // coloca a palavra com tracinhos na tela
                System.out.println("Letras Digitadas..: " + comunicadoDeDados.getControladorDeLetrasJaDigitadas()); // coloca letras digitadas na tela
                System.out.println("Erros....: " + comunicadoDeDados.getControladorDeErros() + "\n"); // coloca os erros na tela
                System.out.println("Plavras Digitadas...:" + comunicadoDeDados.getControladorDePalvrasJaDigitadas()); // coloca palavras ja digitadas na tela


                System.out.println("Escolha uma das seguntes opções \n");
                System.out.println("1 - Chutar uma letra");
                System.out.println("2 - Chutar a palavra");

                try {
                    opcao = Teclado.getUmChar();

                } catch (Exception e) {
                } // não vai dar erro
            }


            switch (opcao)
            {
                case '1':
                    System.out.println("Digite a letra");
                    char letra = Teclado.getUmChar();
                    if (comunicadoDeDados.getControladorDeLetrasJaDigitadas().isJaDigitada(letra))
                        comunicadoDeDados.getControladorDeErros().registreUmErro();
                    else
                    {
                        comunicadoDeDados.getControladorDeLetrasJaDigitadas().registre(letra);

                        int j = comunicadoDeDados.getPalavra().getQuantidade(letra);

                        if (j == 0)
                        {
                            System.out.println("Essa letra não existe\n");
                            comunicadoDeDados.getControladorDeErros().registreUmErro();

                        }
                        else
                        {
                            for (int i = 0; i < j; i++)
                            {
                                int posicao = comunicadoDeDados.getPalavra().getPosicaoDaIezimaOcorrencia(i, letra);
                                comunicadoDeDados.getTracinhos().revele(posicao, letra);
                            }
                        }
                    }
                    break;
                case '2':
                    System.out.print("Tente acertar a palavra\n");
                    String palavra = Teclado.getUmString().toUpperCase();


                    if (palavra.equals(comunicadoDeDados.getPalavra().toString())) // verifica se a palavra  existe
                    {
                        System.out.println("PARABÉNS, VOCÊ VENCEU !!!");
                        servidor.receba(new ComunicadoDeVitoria(true)); // comunicado de vitoria recebe verdadeiro
                        servidor.receba(new ComunicadoDeDesligamento());
                        System.exit(0);

                    }
                    else
                    {
                        System.out.println("Você Perdeu a palavra era" + comunicadoDeDados.getPalavra());
                        servidor.receba(new ComunicadoDeVez(comunicadoDeDados));
                        servidor.receba(new ComunicadoDeDesligamento());
                        System.exit(0);
                    }

                    break;

            }
            System.out.println("É a vez de outro jogador, aguarde");
            servidor.receba(new ComunicadoDeVez(comunicadoDeDados));

        } while (emGame);

        try {
            servidor.receba(new PedidoParaSair());
        } catch (Exception erro) {
        }

        System.out.println("Obrigado por usar este programa!");
        System.exit(0);
    }

    }






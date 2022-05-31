package ForcaJogo;

import ClienteForca.Comunicado;

import java.io.Serializable;

public class Palavra extends Comunicado implements Comparable<Palavra>, Serializable, Cloneable {
    private String texto;

    public Palavra (String texto) throws Exception
    {
        if(texto == null)
            throw new Exception("Este texto está vazio!");
        this.texto = texto;
    }

    public int getQuantidade (char letra)
    {

        byte quantasVezes = 0; // variavel para contar quantas vezes a letra aparece na palavra

        for(int i = 0; i < this.texto.length(); i++) // percorre a String this.texto
        {
            if((this.texto.trim()).charAt(i) == letra) // verifica se a letra está na palavra
            {
                quantasVezes++; // se a letra esatá na palavra, somamos 1 em quantasVezes
            }
        }
        return
                quantasVezes; // retornamos quantas vezes a letra, está na palavra

    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {
        // se i==0, retorna a posicao em que ocorre a primeira
        // aparicao de letra fornecida em this.texto;
        // se i==1, retorna a posicao em que ocorre a segunda
        // aparicao de letra fornecida em this.texto;
        // se i==2, retorna a posicao em que ocorre a terceira
        // aparicao de letra fornecida em this.texto;
        // e assim por diante.
        // lançar excecao caso nao encontre em this.texto
        // a Iézima aparição da letra fornecida.


        int quantasVezes = 0;

        for(int j = 0; j < this.texto.length(); j++) // percorre a String
        {
            if ((this.texto.trim()).charAt(j) == letra) //verifica se as leras são iguais
            {
                if(quantasVezes == i)
                {
                    return j;  // retorna a letra
                }
                quantasVezes++;
            }
        }
        throw new Exception("A posição não existe"); // exceçãoo caso a letra não exista


    }

    public int getTamanho ()
    {
        return this.texto.length();
    }

    public String toString ()
    {
        return this.texto;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo

        if((this.texto).equals(obj))
            return true;

        return false;
    }


    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this

        int ret = 725;

        ret = 5*ret + (this.texto).hashCode();

        return ret;

    }


    public int compareTo (Palavra palavra)
    {
        return this.texto.compareTo(palavra.texto);
    }
}


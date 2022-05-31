package ForcaJogo;

import ClienteForca.Comunicado;

import java.io.Serializable;

public class Tracinhos extends Comunicado implements Cloneable, Serializable {
    private char texto [];

    public Tracinhos (int qtd) throws Exception
    {
        // verifica se qtd não é positiva, lançando uma exceção.
        // instancia this.texto com um vetor com tamanho igual qtd.
        // preenche this.texto com underlines (_).

        if(qtd <= 0)
            throw new  Exception("A quantidade de tracinhos é inválida");

        this.texto = new char[qtd];

        for(int i = 0; i < this.texto.length; i++)
        {
            this.texto[i] = '_';
        }
    }

    public void revele (int posicao, char letra) throws Exception
    {
        // verifica se posicao é negativa ou então igual ou maior
        // do que this.texto.length, lançando uma exceção.
        // armazena a letra fornecida na posicao tambem fornecida
        // do vetor this.texto

        if(posicao < 0 || posicao >= this.texto.length)
            throw new Exception("Está posição não existe, ou é maior que o comprimento da palavra");

        this.texto[posicao] = letra;
    }

    public boolean isAindaComTracinhos ()
    {
        // percorre o vetor de char this.texto e verifica
        // se o mesmo ainda contem algum underline ou se ja
        // foram todos substituidos por letras; retornar true
        // caso ainda reste algum underline, ou false caso
        // contrario


        for(int i = 0; i < this.texto.length; i++)
        {
            if(this.texto[i] == '_')
                return true;

        }
        return false;
    }

    public String toString ()
    {
        // retorna um String com TODOS os caracteres que há
        // no vetor this.texto, intercalados com espaços em
        // branco

        String ret = "";

        for(int i = 0; i < this.texto.length; i++)
        {
            ret += this.texto[i] + " ";
        }

        return ret;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo

        if(this.texto.equals(obj))
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

    public Tracinhos (Tracinhos t) throws Exception // construtor de cópia
    {
        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copilar o conteúdo de t.texto para this.texto

        if(t == null)
            throw new Exception("o modelo está vazio");
        else
        {
            for(int i = 0; i < this.texto.length; i++)
            {
                this.texto[i] = this.texto[i];
            }
        }



    }

    public Object clone ()
    {
        // retornar uma copia de this

        Tracinhos Tr = null;

        try
        {
            Tr = new Tracinhos(this);
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }

        return Tr;
    }
}

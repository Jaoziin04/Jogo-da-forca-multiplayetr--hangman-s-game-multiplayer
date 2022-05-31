package ForcaJogo;

import java.io.Serializable;
import ClienteForca.Comunicado;

public class ControladorDeErros extends Comunicado implements Cloneable, Serializable  {
    private int qtdMax, qtdErr=0;

    public ControladorDeErros (int qtdMax) throws Exception
    {
        // verifica se qtdMax fornecida não é positiva, lançando
        // uma exceção.
        // armazena qtdMax fornecida em this.qtdMax.

        if(qtdMax <= 0)
            throw new Exception("A quantidade maxima de erros não é maior que zero");
        this.qtdMax = qtdMax;

    }

    public void registreUmErro () throws Exception
    {
        // verifica se this.qtdErr ja é igual a this.qtdMax,
        // lançando excecao em caso positivo ou
        // incrementando this.qtdErr em caso negativo

        if(this.qtdErr == this.qtdMax)
            throw new Exception("Você já cometeu o máximo de erros possiveis!");
        else
            this.qtdErr++;
    }

    public boolean isAtingidoMaximoDeErros  ()
    {
        // returna true se this.qtdErr for igual a this.qtdMax,
        // ou então false, caso contrario.

        if(this.qtdErr == this.qtdMax)
            return true;
        else
            return false;
    }

    public String toString ()
    {
        return this.qtdErr + "/" + this.qtdMax;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo

        if(this == obj)
            return true;

        if(obj == this)
            return false;

        if(obj.getClass() != ControladorDeErros.class)  // vefica se o objeto é da classe do ControladorDeErros
            return false;

        ControladorDeErros ctle = (ControladorDeErros)obj;

        if(this.qtdErr != ctle.qtdErr) // verifica se a quantidade de erros agora é igual a quantidade de erros original
            return false;

        if(this.qtdMax != ctle.qtdMax)  // verifica se a quantidade máxima atua é igual a quantidade máxima original
            return false;

        return true; // se nada disso for verdade retorna true
    }


    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this


        int ret = 725;


        ret = 5*ret + new Integer(this.qtdErr).hashCode();
        ret = 5*ret + new Integer(this.qtdMax).hashCode();

        return ret;
    }

    public ControladorDeErros (ControladorDeErros c) throws Exception // construtor de cópia
    {
        // copiar c.qtdMax e c.qtdErr, respectivamente em, this.qtdMax e this.qtdErr


        if(c == null)
            throw new Exception("o modelo está vazio");

        this.qtdMax = c.qtdMax;
        this.qtdErr = c.qtdErr;
    }

    public Object clone ()
    {
        // returnar uma cópia de this

        ControladorDeErros cdeErr = null;

        try
        {
            cdeErr = new ControladorDeErros(this);
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }

        return cdeErr;
    }
}


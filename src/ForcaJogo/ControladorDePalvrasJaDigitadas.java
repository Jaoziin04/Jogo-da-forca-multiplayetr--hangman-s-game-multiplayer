package ForcaJogo;

import ClienteForca.Comunicado;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class ControladorDePalvrasJaDigitadas extends Comunicado implements Cloneable, Serializable
{
    private ArrayList<String> palavrasJaDigitadas; // vetor de strings

    public ControladorDePalvrasJaDigitadas()
    {
        this.palavrasJaDigitadas = new ArrayList<>();
    }


    public boolean isJaDigitada (String palavra)
    {
        // percorre a ArrayList<String> palavrasJaDigitadas e verificar se ela
        // possui a palavra fornecida, retornando true em caso afirmativo
        // ou false em caso negativo

        boolean existe = false;

        for(int cont = 0; cont < this.palavrasJaDigitadas.size(); cont++)
        {
            if(palavra.toUpperCase() == this.palavrasJaDigitadas.get(cont)) // verifica se a palavra, está na determinada posição das palavrasJaDigitadas
            {
                existe = true;
            }

        }
        return
                existe; // retorna false ou true
    }


    public void registre (String palavra) throws Exception
    {
        // verifica se a palavra fornecida ja foi digitada (pode usar
        // o método this.isJaDigitada, para isso), lancando uma exceção
        // em caso afirmativo.
        // concatena a letra fornecida a this.palavrasJaDigitadas.

        if(this.isJaDigitada(palavra) == true)
            throw new Exception("Está palavra já foi digitada, tente novamnete");

        this.palavrasJaDigitadas.add(palavra);
    }

    public String toString()
    {
        String ret = "";


        for(int i = 0; i < this.palavrasJaDigitadas.size(); i++)
        {
                if (i == this.palavrasJaDigitadas.size() - 1) // verifica se o contador está na ultima posição do vetor
                    ret = ret + this.palavrasJaDigitadas.get(i);  // ret recebe ret mais a palavra na posição i
                ret = ret + this.palavrasJaDigitadas.get(i) + ","; // ret recebe ret, a palavra na posição i e uma virgula
        }
        return  ret;
    }


    public boolean equals (Object obj)
    {
        // verificar se this e obj são iguais

        if((this.palavrasJaDigitadas).equals(obj))
            return true;

        return false;
    }



    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this


        int ret = 725;

        ret = 5*ret + (this.palavrasJaDigitadas).hashCode();

        return ret;


    }

    public ControladorDePalvrasJaDigitadas(ControladorDePalvrasJaDigitadas controladorDePalvrasJaDigitadas) throws Exception // construtor de cópia
    {
        if(controladorDePalvrasJaDigitadas == null)
            throw new Exception("Palavra vazia");

        this.palavrasJaDigitadas = this.palavrasJaDigitadas;
    }

    public Object clone()
    {
        // criar uma cópia do this com o construtor de cópia e retornar

        ControladorDePalvrasJaDigitadas cdpd = null;

        try
        {
            cdpd = new ControladorDePalvrasJaDigitadas(this);
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }
        return cdpd;
    }



}

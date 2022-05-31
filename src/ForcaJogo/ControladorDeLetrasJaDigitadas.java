package ForcaJogo;

import ClienteForca.Comunicado;

import java.io.Serializable;

public class ControladorDeLetrasJaDigitadas extends Comunicado implements Cloneable, Serializable {
    private String letrasJaDigitadas;

    public ControladorDeLetrasJaDigitadas ()
    {
        // torna this.letrasJaDigitadas igual ao String vazio

        this.letrasJaDigitadas = "";
    }

    public boolean isJaDigitada (char letra)
    {
        // percorrer o String this.letrasJaDigitadas e verificar se ele
        // possui a letra fornecida, retornando true em caso afirmativo
        // ou false em caso negativo

        boolean existe = false;

        for(int cont = 0; cont < this.letrasJaDigitadas.length(); cont++)
        {
            if(letrasJaDigitadas.charAt(cont) == letra) // verifica se a letra, está na determinada posição das letrasJaDigitadas
            {
                existe = true;
            }

        }
        return
                existe; // retorna false ou true
    }

    public void registre (char letra) throws Exception
    {
        // verifica se a letra fornecida ja foi digitada (pode usar
        // o método this.isJaDigitada, para isso), lancando uma exceção
        // em caso afirmativo.
        // concatena a letra fornecida a this.letrasJaDigitadas.

        if(this.isJaDigitada(letra) == true)
            throw new Exception("Está letra já foi digitada, tente novamnete");

        this.letrasJaDigitadas += letra;
    }

    public String toString ()
    {

        // retorna um String com TODAS as letras presentes em
        // this.letrasJaDigitadas separadas por vírgula (,).

        String ret = "";

        for(char c: this.letrasJaDigitadas.toCharArray()) //pega todas as letras e acrescenta na string de retorno, separadas por virgula
        {
            ret = ret + c + ",";
        }


        return ret;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj são iguais

        if((this.letrasJaDigitadas).equals(obj))
            return true;

        return false;

    }

    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this


        int ret = 725;

        ret = 5*ret + (this.letrasJaDigitadas).hashCode();

        return ret;


    }

    public ControladorDeLetrasJaDigitadas(
            ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas)
            throws Exception // construtor de cópia
    {
        // copiar c.letrasJaDigitadas em this.letrasJaDigitadas

        if(controladorDeLetrasJaDigitadas == null)
            throw new Exception("Letras vazias");

        this.letrasJaDigitadas = this.letrasJaDigitadas;
    }

    public Object clone ()
    {
        // criar uma cópia do this com o construtor de cópia e retornar


        ControladorDeLetrasJaDigitadas cdeL = null;

        try
        {
            cdeL= new ControladorDeLetrasJaDigitadas(this);
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }

        return cdeL;
    }
}


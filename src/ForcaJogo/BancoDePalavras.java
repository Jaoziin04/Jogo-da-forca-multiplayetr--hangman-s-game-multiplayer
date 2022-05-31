package ForcaJogo;

import java.io.Serializable;

public class BancoDePalavras implements Serializable, Cloneable {
    private static String[] palavras =
            {
                    "PARALELEPIPEDO",
                    "OTORRINOLARINGOLOGISTA",
                    "FONOAUDIOLOGO",
                    "GRANDEZA",
                    "ILUMINISMO",
                    "ILUSIONISTA",
                    "HOMEM ARANHA",
                    "MALIGNO",
                    "MALIGNO JR",
                    "NEYMAR JR",
                    "ROCKET LEAGUE",
                    "PALMEIRAS NAO TEM MUNDIAL",
                    "PLAYSTATION",
            };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
                    new Palavra (BancoDePalavras.palavras[
                            (int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e)
        {}

        return palavra;
    }
}


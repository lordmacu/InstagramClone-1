package rodolfoizidoro.instagramclone.util;

import java.util.HashMap;

/**
 * Created by rodolfoizidoro on 09/03/17.
 */

public class ParseErros {

    private HashMap<Integer,String> erros;

    public ParseErros() {
        this.erros = new HashMap<>();
        erros.put(202,"Usuário ja existente, tente outro usuario");
    }

    public String getErro(int codErro){

        return this.erros.get(codErro);
    }

}

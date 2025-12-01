package org.matheus.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private enum TipoComando{
        ZERAR, NUMERO, DIVISAO, MULTIPLICACAO, SUBTRACAO, SOMA, IGUAL, VIRGULA
    }

    private static final Memoria instancia = new Memoria();

    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private String textAtual = "";

    private Memoria(){

    }

    public static Memoria getInstance(){
        return instancia;
    }

    public void adicionarObservador(MemoriaObservador observador){
        observadores.add(observador);
    }

    public String getTextAtual() {
        return textAtual.isEmpty() ? "0" : textAtual;
    }

    public void processadorComando(String texto){
        TipoComando tipoComando = detectarTipoComando(texto);

        if ("AC".equals(texto)){
            textAtual = "";
        }else {
            textAtual += texto;
        }
        observadores.forEach(o -> o.valorAlterado(getTextAtual()));
    }

    private TipoComando detectarTipoComando(String texto) {
        if (textAtual.isEmpty() && texto == "0"){
            return null;
        }

        try {
            Integer.parseInt(texto);
            return TipoComando.NUMERO;
        }catch (NumberFormatException e){
            if ("AC".equals(texto)){
                return TipoComando.ZERAR;
            }else if ("/".equals(texto)){
                return TipoComando.DIVISAO;
            }
            else if ("*".equals(texto)){
                return TipoComando.MULTIPLICACAO;
            }
            else if ("+".equals(texto)){
                return TipoComando.SOMA;
            }
            else if ("-".equals(texto)){
                return TipoComando.SUBTRACAO;
            }
            else if (",".equals(texto)){
                return TipoComando.VIRGULA;
            }
            else if ("=".equals(texto)){
                return TipoComando.IGUAL;
            }
        }
        return null;
    }

}

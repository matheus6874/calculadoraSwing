package org.matheus.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private enum TipoComando{
        ZERAR, NUMERO, DIVISAO, MULTIPLICACAO, SUBTRACAO, SOMA, IGUAL, VIRGULA, SINAL;
    }

    private static final Memoria instancia = new Memoria();

    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private String textAtual = "";
    private String textoBuffer = "";
    private boolean substituir = false;
    private TipoComando ultimaOpercao = null;

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

        if (tipoComando == null){
            return;
        }else if (tipoComando == TipoComando.ZERAR){
            textAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOpercao = null;
        }else if (tipoComando == TipoComando.SINAL && textAtual.contains("-")){
            textAtual = textAtual.substring(1);
        }else if (tipoComando == TipoComando.SINAL && !textAtual.contains("-")){
            textAtual = "-" + textAtual;
        }
        else if (tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA){
            textAtual = substituir ? texto : textAtual + texto;
            substituir = false;
        }else {
            substituir = true;
            textAtual = obterResultadoOperacao();
            textoBuffer = textAtual;
            ultimaOpercao = tipoComando;
        }


        observadores.forEach(o -> o.valorAlterado(getTextAtual()));
    }

    private String obterResultadoOperacao() {
        if (ultimaOpercao == null || ultimaOpercao == TipoComando.IGUAL){
            return textAtual;
        }

        double numeroBuffer = Double.parseDouble(textoBuffer.replace(",","."));
        double numeroAtual = Double.parseDouble(textAtual.replace(",","."));

        double resultado = 0;

        if (ultimaOpercao == TipoComando.SOMA){
            resultado  = numeroBuffer + numeroAtual;
        }else if (ultimaOpercao == TipoComando.SUBTRACAO){
            resultado = numeroBuffer - numeroAtual;
        }
        else if (ultimaOpercao == TipoComando.MULTIPLICACAO){
            resultado = numeroBuffer * numeroAtual;
        }
        else if (ultimaOpercao == TipoComando.DIVISAO){
            resultado = numeroBuffer / numeroAtual;
        }

        String texto = Double.toString(resultado).replace(".",",");
        boolean inteiro = texto.endsWith(",0");
        return inteiro ? texto.replace(",0","") : texto;
    }

    private TipoComando detectarTipoComando(String texto) {
        if (textAtual.isEmpty() && "0".equals(texto)) {
            return null;
        }

        try {
            Integer.parseInt(texto);
            return TipoComando.NUMERO;
        } catch (NumberFormatException e) {
            if ("AC".equals(texto)) {
                return TipoComando.ZERAR;
            } else if ("/".equals(texto)) {
                return TipoComando.DIVISAO;
            } else if ("*".equals(texto)) {
                return TipoComando.MULTIPLICACAO;
            } else if ("+".equals(texto)) {
                return TipoComando.SOMA;
            } else if ("-".equals(texto)) {
                return TipoComando.SUBTRACAO;
            } else if (",".equals(texto) && !textAtual.contains(",") && !textAtual.isEmpty()) {
                    return TipoComando.VIRGULA;
            } else if ("=".equals(texto)) {
                return TipoComando.IGUAL;
            } else if ("±".equals(texto)) {
                return TipoComando.SINAL;
            }
        }
        return null;
    }

}

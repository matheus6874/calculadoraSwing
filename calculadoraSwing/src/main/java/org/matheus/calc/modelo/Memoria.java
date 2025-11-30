package org.matheus.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

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

    public void processadorComando(String valor){
        if ("AC".equals(valor)){
            textAtual = "";
        }else {
            textAtual += valor;
        }
        observadores.forEach(o -> o.valorAlterado(getTextAtual()));
    }

}

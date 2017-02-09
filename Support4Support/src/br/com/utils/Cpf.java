package br.com.utils;

import java.util.ArrayList;

import javax.swing.text.MaskFormatter;

public class Cpf {
	
	private ArrayList<Integer> listaAleatoria = new ArrayList();
	private ArrayList<Integer> listaNumMultiplicados = null;
	public int geraNumAleatorio(){
	    int numero = (int) (Math.random() * 10);
	 
	    return numero;
	}
	
	public ArrayList geraCPFParcial(){
	    for(int i = 0; i < 9; i++){
	        listaAleatoria.add(geraNumAleatorio());
	    }
	    return listaAleatoria;
	}
	
	//Metodo para geracao do primeiro digito verificador (para isso nos baseamos nos 9 digitos aleatorios gerados anteriormente)
	public ArrayList geraPrimeiroDigito(){
	    listaNumMultiplicados = new ArrayList();
	    int primeiroDigito;
	    int totalSomatoria = 0;
	    int restoDivisao;
	    int peso = 10;
	    for(int item : listaAleatoria){
	        listaNumMultiplicados.add(item * peso);
	        peso--;
	    }
	    for(int item : listaNumMultiplicados){
	        totalSomatoria += item;
	    }
	        restoDivisao = (totalSomatoria % 11);
	    if(restoDivisao < 2){
	        primeiroDigito = 0;
	    } else{
	        primeiroDigito = 11 - restoDivisao;
	    }
	    listaAleatoria.add(primeiroDigito);
	    return listaAleatoria;
	}
	
	public ArrayList geraSegundoDigito(){
	    listaNumMultiplicados = new ArrayList();
	    int segundoDigito;
	    int totalSomatoria = 0;
	    int restoDivisao;
	    int peso = 11;
	    for(int item : listaAleatoria){
	        listaNumMultiplicados.add(item * peso);
	        peso--;
	    }
	    for(int item : listaNumMultiplicados){
	        totalSomatoria += item;
	    }
	    restoDivisao = (totalSomatoria % 11);
	    if(restoDivisao < 2){
	        segundoDigito = 0;
	    } else{
	        segundoDigito = 11 - restoDivisao;
	    }
	    listaAleatoria.add(segundoDigito);
	    return listaAleatoria;
	}
	
	public String geraCPFFinal() {
	    geraCPFParcial();
	    geraPrimeiroDigito();
	    geraSegundoDigito();
	    String cpf = "";
	    String texto = "";
	    for(int item : listaAleatoria){
	        texto += item;
	    }
	    try{
	        MaskFormatter mf = new MaskFormatter("###.###.###-##");
	        mf.setValueContainsLiteralCharacters(false);
	        cpf = mf.valueToString(texto);
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }
	return cpf;
	}
}

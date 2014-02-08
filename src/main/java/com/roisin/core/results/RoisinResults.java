package com.roisin.core.results;

import java.util.List;

/**
 * Interfaz del tipo RoisinResults que se encargar‡ del c‡lculo de resultados
 * para Roisin a partir de las reglas obtenidas.
 * 
 * @author FŽlix Miguel Sanju‡n Segovia <fmsanse@gmail.com>
 * 
 */
public interface RoisinResults {

	/**
	 * Devuelve una lista con el conjunto de reglas procesadas por Roisin.
	 * 
	 * @return rules lista de reglas
	 */
	List<RoisinRule> getRoisinRules();

}
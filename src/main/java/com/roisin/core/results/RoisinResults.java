package com.roisin.core.results;

import java.util.List;

/**
 * Interfaz del tipo RoisinResults que se encargar� del c�lculo de resultados
 * para Roisin a partir de las reglas obtenidas.
 * 
 * @author F�lix Miguel Sanju�n Segovia <fmsanse@gmail.com>
 * 
 */
public interface RoisinResults {

	/**
	 * Devuelve una lista con el conjunto de reglas procesadas por Roisin.
	 * 
	 * @return rules lista de reglas
	 */
	List<RoisinRule> getRoisinRules();

	/**
	 * Devuelve el �rea bajo la curva de un conjunto de reglas.
	 * 
	 * @return auc �rea bajo la curva
	 */
	public double getRulesAuc();

	/**
	 * Este método trunca todos los valores de tipo double de cada regla y del
	 * área bajo la curva.
	 */
	void truncateResults();

}
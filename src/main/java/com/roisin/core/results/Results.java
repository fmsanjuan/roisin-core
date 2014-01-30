package com.roisin.core.results;

import java.util.List;

public interface Results<T> {

	/**
	 * Devuelve el nœmero total de casos contenidos en los datos de ejemplo.
	 * 
	 * @return numCasos nœmero total de casos
	 */
	int getNumCasos();

	/**
	 * Devuelve una lista con los nombres de todas las predicciones posibles.
	 * 
	 * @return List<String> lista que contiene las predicciones
	 */
	List<String> getLabelNames();

	/**
	 * Devuelve la precisi—n de la regla.
	 * 
	 * @param rule
	 * @return
	 */
	Double getRuleConfidence(T rule);

	/**
	 * Devuelve el soporte de la regla
	 * 
	 * @param rule
	 * @return
	 */
	Double getRuleSupport(T rule);

}
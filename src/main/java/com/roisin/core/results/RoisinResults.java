package com.roisin.core.results;

import java.util.List;

public interface RoisinResults {

	/**
	 * Devuelve una lista con el conjunto de reglas procesadas por Roisin.
	 * 
	 * @return rules lista de reglas
	 */
	List<RoisinRule> getRoisinRules();

}
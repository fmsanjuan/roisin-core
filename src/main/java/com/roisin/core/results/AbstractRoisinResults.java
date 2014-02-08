package com.roisin.core.results;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rapidminer.example.Example;

/**
 * Clase abstracta con la implementación común a la obtención de resultados para
 * Roisin.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * 
 */
public abstract class AbstractRoisinResults implements RoisinResults {

	/**
	 * Lista de reglas (resultado).
	 */
	protected List<RoisinRule> rules;

	/**
	 * Conjunto de ejemplos que ya ha sido cubierto.
	 */
	protected Set<Example> alreadyCoveredExamples;

	public AbstractRoisinResults() {
		this.rules = new ArrayList<RoisinRule>();
		this.alreadyCoveredExamples = new HashSet<Example>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinResults#getRoisinRules()
	 */
	@Override
	public List<RoisinRule> getRoisinRules() {
		return rules;
	}

	/**
	 * Este método comprueba si el ejemplo que se pasa como parámetro ha sido ya
	 * cubierto por una regla.
	 * 
	 * @param example
	 *            ejemplo a comprobar
	 * @return hasBeenCovered
	 */
	protected boolean hasBeenCovered(Example example) {
		boolean hasBeenCovered = false;
		for (Example coveredExample : alreadyCoveredExamples) {
			if (coveredExample.getDataRow().equals(example.getDataRow())) {
				hasBeenCovered = true;
			}
		}
		return hasBeenCovered;
	}

	public String toString() {
		String res = new String();
		for (RoisinRule rule : getRoisinRules()) {
			res += "\n\nRegla número " + new Integer(getRoisinRules().indexOf(rule) + 1);
			res += "\n" + rule.toString();
		}
		return res;
	}

}

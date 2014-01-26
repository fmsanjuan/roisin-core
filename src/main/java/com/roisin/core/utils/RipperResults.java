package com.roisin.core.utils;

import java.util.List;

import com.rapidminer.operator.learner.rules.Rule;
import com.rapidminer.operator.learner.rules.RuleModel;

public interface RipperResults {

	/**
	 * Devuelve el ruleModel.
	 * 
	 * @return
	 */
	public abstract RuleModel getRuleModel();

	/**
	 * Devuelve el nœmero total de casos contenidos en los datos de ejemplo. El
	 * c‡lculo se realiza a partir de la frecuencia.
	 * 
	 * @return numCasos nœmero total de casos
	 */
	public abstract int getNumCasos();

	/**
	 * Devuelve una lista con los nombres de todas las predicciones posibles.
	 * 
	 * @return List<String> lista que contiene las predicciones
	 */
	public abstract List<String> getLabelNames();

	/**
	 * Devuelve la precisi—n de la regla.
	 * 
	 * @param rule
	 * @return
	 */
	public abstract Double getRuleConfidence(Rule rule);

	/**
	 * Devuelve el soporte de la regla
	 * 
	 * @param rule
	 * @return
	 */
	public abstract Double getRuleSupport(Rule rule);

}
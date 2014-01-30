package com.roisin.core.results;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.rapidminer.operator.learner.subgroups.hypothesis.Rule;
import com.rapidminer.operator.learner.subgroups.utility.UtilityFunction;

/**
 * Implementación de todos los métodos necesarios para la obtención de
 * resultados a partir del algoritmo subgroup discovery.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * @param <T>
 * 
 */
public class SubgroupResults extends AbstractResults<Rule> {

	/**
	 * Rule set que contiene el conjunto de reglas calculadas por un proceso.
	 */
	private RuleSet ruleSet;

	/**
	 * Constructor público.
	 * 
	 * @param ruleSet
	 * @param numCasos
	 */
	public SubgroupResults(RuleSet ruleSet, int numCasos) {
		this.ruleSet = ruleSet;
		this.numCasos = numCasos;
		this.precisiones = populateConfidenceMap();
		this.soportes = populateSupportMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.utils.Results#getLabelNames()
	 */
	@Override
	public List<String> getLabelNames() {
		return ruleSet.getLabel().getMapping().getValues();
	}

	/**
	 * Devuelve un mapa que contiene las reglas asociadas a su precisión.
	 * 
	 * @return Map<Rule, Double> mapa
	 */
	private Map<Rule, Double> populateConfidenceMap() {
		Map<Rule, Double> map = new HashMap<Rule, Double>();
		for (int i = 0; i < ruleSet.getNumberOfRules(); i++) {
			map.put(ruleSet.getRule(i),
					(Double) ruleSet.getRule(i).getUtility(
							UtilityFunction.getUtilityFunctionClass(UtilityFunction.PRECISION)));
		}
		return map;
	}

	/**
	 * Devuelve un mapa que contiene las reglas asociadas a su soporte.
	 * 
	 * @return Map<Rule, Double> mapa
	 */
	private Map<Rule, Double> populateSupportMap() {
		Map<Rule, Double> map = new HashMap<Rule, Double>();
		for (Rule rule : ruleSet.getPositiveRules()) {
			map.put(rule, new Double(rule.getPositiveWeight() / getNumCasos()));
		}
		for (Rule rule : ruleSet.getNegativeRules()) {
			map.put(rule, new Double(rule.getNegativeWeight() / getNumCasos()));
		}
		return map;
	}

}

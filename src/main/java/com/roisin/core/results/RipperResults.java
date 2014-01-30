package com.roisin.core.results;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rapidminer.operator.learner.rules.Rule;
import com.rapidminer.operator.learner.rules.RuleModel;

/**
 * Implementación de todos los métodos necesarios para la obtención de
 * resultados a partir del algoritmo Ripper.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * 
 */
public class RipperResults extends AbstractResults<Rule> {

	/**
	 * Rule model a partir del cual se hallarán los resultados.
	 */
	private RuleModel ruleModel;

	/**
	 * Constructor público.
	 * 
	 * @param ruleModel
	 */
	public RipperResults(RuleModel ruleModel) {
		this.ruleModel = ruleModel;
		this.numCasos = calculateNumCasos();
		this.soportes = populateSupportMap();
		this.precisiones = populateConfidenceMap();
	}

	/**
	 * Calcula el número total de casos contenidos en los datos de ejemplo. El
	 * cálculo se realiza a partir de la frecuencia.
	 * 
	 * @return numCasos número total de casos
	 */
	private int calculateNumCasos() {
		int numCasos = 0;
		for (Rule rule : ruleModel.getRules()) {
			int[] ruleFrequencies = rule.getFrequencies();
			numCasos += ruleFrequencies[0] + ruleFrequencies[1];
		}
		return numCasos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.utils.RipperResults#getNumCasos()
	 */
	@Override
	public int getNumCasos() {
		return this.numCasos;
	}

	/**
	 * Devuelve un mapa que contiene las reglas asociadas a su precisión.
	 * 
	 * @return Map<Rule, Double> mapa
	 */
	private Map<Rule, Double> populateConfidenceMap() {
		Map<Rule, Double> map = new HashMap<Rule, Double>();
		for (Rule rule : ruleModel.getRules()) {
			int labelIndex = getLabelNames().indexOf(rule.getLabel());
			map.put(rule, rule.getConfidences()[labelIndex]);
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
		for (Rule rule : ruleModel.getRules()) {
			int labelIndex = getLabelNames().indexOf(rule.getLabel());
			map.put(rule, new Double(rule.getFrequencies()[labelIndex] / getNumCasos()));
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.utils.RipperResults#getLabelNames()
	 */
	@Override
	public List<String> getLabelNames() {
		return ruleModel.getLabel().getMapping().getValues();
	}

}

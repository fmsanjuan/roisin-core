package com.roisin.core.results;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rapidminer.example.table.DataRow;
import com.roisin.core.utils.FalsePositiveRateComparator;

/**
 * Clase abstracta con la implementaci�n com�n a la obtenci�n de resultados para
 * Roisin.
 * 
 * @author F�lix Miguel Sanju�n Segovia <fmsanse@gmail.com>
 * 
 */
public abstract class AbstractRoisinResults implements RoisinResults {

	/**
	 * Lista de reglas (resultado).
	 */
	protected List<RoisinRule> rules;

	public AbstractRoisinResults() {
		this.rules = Lists.newArrayList();
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
	 * Este m�todo elimina del listado de reglas aquellas reglas cuyos casos ya
	 * est�n contenidos en otras reglas. Es decir, elimina reglas que se solapen
	 * con otras.
	 */
	protected void applyOverlappingProcedure() {
		Map<RoisinRule, Set<DataRow>> mapa = Maps.newHashMap();
		// Poblamos el mapa
		for (RoisinRule rule : rules) {
			mapa.put(rule, Sets.newHashSet(rule.getCoveredDataRows()));
		}
		// Lista de reglas que deben ser eliminadas.
		Set<RoisinRule> rulesToRemove = Sets.newHashSet();
		Map<Set<DataRow>, Set<RoisinRule>> equalRules = Maps.newHashMap();
		// Recorremos todas las reglas
		for (RoisinRule rule : rules) {
			// Se comprueba si el conjunto de ejemplos de la regla contiene a
			// otro conjunto de ejemplos de otra regla. En caso afirmativo, la
			// regla es candidata para ser borrada.
			for (Entry<RoisinRule, Set<DataRow>> entry : mapa.entrySet()) {
				// Comprobamos una por una y si la regla contiene todos los
				// ejemplos que contiene otra regla, la metemos en la lista de
				// reglas a borrar.
				if (!entry.getKey().equals(rule)
						&& Sets.newHashSet(rule.getCoveredDataRows()).containsAll(entry.getValue())) {
					// Si son iguales, hay que quedarse con una de las reglas.
					if (Sets.newHashSet(rule.getCoveredDataRows()).equals(entry.getValue())) {
						// De momento las almacenamos en un mapa.
						if (!equalRules.containsKey(entry.getValue())) {
							// Si no se hab�a dado el caso antes
							equalRules.put(entry.getValue(), Sets.newHashSet(rule, entry.getKey()));
						} else {
							// Si ya se han encontrado reglas que est�n
							// cubiertas por los mismos ejemplos, se a�aden las
							// dos.
							equalRules.get(entry.getValue()).add(rule);
							equalRules.get(entry.getValue()).add(entry.getKey());
						}
					} else {
						// Se a�ade a la lista de candidatas para borrar
						rulesToRemove.add(entry.getKey());
					}
				}
			}
		}
		// De las reglas que contienen los mismos conjuntos de ejemplos,
		// escogemos una para eliminar de forma aleatoria.
		for (Set<RoisinRule> roisinRuleSet : equalRules.values()) {
			// Se borra la regla que se va a mantener.
			roisinRuleSet.remove(roisinRuleSet.iterator().next());
			// Se a�aden las otras a la lista de borrado.
			rulesToRemove.addAll(roisinRuleSet);
		}
		rules.removeAll(rulesToRemove);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinResults#getRulesAuc()
	 */
	public double getRulesAuc() {
		SortedSet<RoisinRule> sortedRules = Sets.newTreeSet(new FalsePositiveRateComparator());
		sortedRules.addAll(this.rules);
		int ruleCounter = 1;
		double auc = 0.0;
		double prevFpr = 0.0;
		double prevTpr = 0.0;
		for (RoisinRule roisinRule : sortedRules) {
			if (ruleCounter > 1) {
				// Se debe de calcular el tri�ngulo y el rect�ngulo teniendo en
				// cuenta los datos de esta regla y la anterior.
				// Tri�ngulo:
				auc += Math.abs((((roisinRule.getFalsePositiveRate() - prevFpr) * (roisinRule
						.getTruePositiveRate() - prevTpr)) / 2.0));
				// Rect�ngulo
				auc += Math.abs((roisinRule.getFalsePositiveRate() - prevFpr) * prevFpr);
				// Cambiamos el valor de las variables prev
				prevFpr = roisinRule.getFalsePositiveRate();
				prevTpr = roisinRule.getTruePositiveRate();
			} else {
				// S�lo hay que calcular el primer tri�ngulo.
				prevFpr = roisinRule.getFalsePositiveRate();
				prevTpr = roisinRule.getTruePositiveRate();
				auc += Math.abs(((prevFpr * prevTpr) / 2.0));
			}
			ruleCounter++;
		}
		return auc;
	}

	public String toString() {
		String res = new String();
		for (RoisinRule rule : getRoisinRules()) {
			res += "\n\nRegla n�mero " + new Integer(getRoisinRules().indexOf(rule) + 1);
			res += "\n" + rule.toString();
		}
		res += "\n�rea bajo la curva del conjunto de reglas: " + getRulesAuc();
		return res;
	}

}

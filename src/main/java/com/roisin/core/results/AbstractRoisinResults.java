package com.roisin.core.results;

import java.util.List;

public abstract class AbstractRoisinResults implements RoisinResults {

	protected List<RoisinRule> rules;

	public List<RoisinRule> getRoisinRules() {
		return rules;
	}

	public String toString() {
		String res = new String();
		for (RoisinRule rule : getRoisinRules()) {
			res += "\n\nRegla nœmero " + new Integer(getRoisinRules().indexOf(rule) + 1);
			res += "\n" + rule.toString();
		}
		return res;
	}

}

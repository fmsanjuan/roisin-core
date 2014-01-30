package com.roisin.core.results;

import java.util.Map;

public abstract class AbstractResults<T> implements Results<T> {

	/**
	 * Nœmero total de casos de ejemplo.
	 */
	protected int numCasos;

	/**
	 * Mapa que asocia cada regla a su soporte.
	 */
	protected Map<T, Double> soportes;

	/**
	 * Mapa que asocia cada regla a su precisi—n.
	 */
	protected Map<T, Double> precisiones;

	@Override
	public int getNumCasos() {
		return numCasos;
	}

	@Override
	public Double getRuleConfidence(T rule) {
		return precisiones.get(rule);
	}

	@Override
	public Double getRuleSupport(T rule) {
		return this.soportes.get(rule);
	}

}

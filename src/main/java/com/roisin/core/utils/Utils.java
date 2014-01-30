package com.roisin.core.utils;

import com.rapidminer.example.ExampleSet;

public class Utils {

	public static int getNumCasosTotalFromExampleSet(ExampleSet exampleSet) {
		return exampleSet.getExampleTable().size();
	}
}

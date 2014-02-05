package com.roisin.core.utils;

import com.rapidminer.example.ExampleSet;

public class Utils {

	public static int getNumEjemplosTotalExampleSet(ExampleSet exampleSet) {
		return exampleSet.getExampleTable().size();
	}

}

package com.roisin.core.now;

import java.util.logging.Logger;

import org.jfree.util.Log;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.roisin.core.processes.SampleProcesses;

public class App {

	/**
	 * Trial Log
	 */
	public static Logger log = Logger.getAnonymousLogger();

	public static void main(String[] args) {
		// Iniciando rapidminer
		try {
			log.info("Iniciando rapidminer");
			RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
			RapidMiner.init();
		} catch (Exception e) {
			log.info("Er rapidminer dise que no arranca, que no tiene gana.");
		}
		Log.info("Rapidminer iniciado");

		// Obtenci—n de proceso
		Process process = SampleProcesses.getRipper();
		// Process process = SampleProcesses.getRipperGolfFromExcel();
		// Process process = SampleProcesses.getDecisionTreeToRules();
		// Process process = SampleProcesses.getSubgroupDiscretization();
		// Process process = SampleProcesses.getRipperSonnar();
		// Process process = GenericProcesses.getRipper();
		// Process process = GenericProcesses.getDecisionTreeToRules();
		// GenericProcesses.getSubgroupDiscoveryDiscretization();
		// System.out.println(process);

		try {
			IOContainer prueba = process.run();

			// Si el resultado es un conjunto de reglas
			// RuleSet ruleSet = (RuleSet) prueba.asList().get(0);

			// //////////////////////////////////////////////
			// Extracci—n de informaci—n de un rule Model.//
			// //////////////////////////////////////////////
			// RuleModel ruleModel = (RuleModel) prueba.asList().get(0);
			// System.out.println("\nCon bucle\n");
			// // Variable inicial con el nœmero registros de los datos de
			// ejemplo.
			// int numCasos = 0;
			// // Predicciones posibles
			// List<String> labelNames =
			// ruleModel.getLabel().getMapping().getValues();
			// // C‡lculo del nœmero de registros de los datos de ejemplo a
			// partir
			// // de la frecuencia de todas las reglas.
			// for (Rule rule : ruleModel.getRules()) {
			// int[] ruleFrequencies = rule.getFrequencies();
			// numCasos += ruleFrequencies[0] + ruleFrequencies[1];
			// }
			// System.out.println("Existen " + numCasos + " casos");
			//
			// for (Rule rule : ruleModel.getRules()) {
			// int ruleNum = ruleModel.getRules().indexOf(rule);
			// System.out.println("Regla nœmero " + ruleNum);
			// System.out.println(rule);
			// double[] ruleConfidences = rule.getConfidences();
			// int labelIndex = labelNames.indexOf(rule.getLabel());
			// System.out.println("La precisi—n de la regla nœmero " + ruleNum +
			// " es de "
			// + ruleConfidences[labelIndex]);
			// int[] ruleFrequencies = rule.getFrequencies();
			// System.out.println("El soporte de la regla nœmero " + ruleNum +
			// " es de "
			// + new Double(ruleFrequencies[labelIndex]) / numCasos);
			// }
		} catch (OperatorException ex) {
			log.info("Se ha liado el taco al ejecutar el proceso.");
		}
	}
}

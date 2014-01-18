package com.fmsanjuan.rapid;

import java.util.logging.Logger;

import org.jfree.util.Log;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.subgroups.RuleSet;

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
		// Process process = SampleProcesses.getRipper();
		// Process process = SampleProcesses.getRipperGolfFromExcel();
		// Process process = SampleProcesses.getDecisionTreeToRules();
		Process process = SampleProcesses.getSubgroupDiscretization();
		// Process process = GenericProcesses.getRipper();
		// Process process = GenericProcesses.getDecisionTreeToRules();
		// GenericProcesses.getSubgroupDiscoveryDiscretization();
		// System.out.println(process);

		try {
			IOContainer prueba = process.run();

			// Si el resultado es un conjunto de reglas
			// RuleModel ruleModel = (RuleModel) prueba.asList().get(0);
			RuleSet ruleSet = (RuleSet) prueba.asList().get(0);

			System.out.println(ruleSet.toString());

		} catch (OperatorException ex) {
			log.info("Se ha liado el taco al ejecutar el proceso.");
		}
	}
}

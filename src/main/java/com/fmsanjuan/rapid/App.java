package com.fmsanjuan.rapid;

import java.util.logging.Logger;

import org.jfree.util.Log;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.OperatorException;

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
		// Process process = SampleProcesses.getSubgroupDiscretization();
		// Process process = GenericProcesses.getRipper();
		Process process = GenericProcesses.getDecisionTreeToRules();
		// GenericProcesses.getSubgroupDiscoveryDiscretization();
		System.out.println(process);

		try {
			IOContainer prueba = process.run();
			IOObject[] lista = prueba.getIOObjects();
			System.out.println("Tama–o de la lista: " + lista.length);

			for (IOObject ioObject : lista) {
				System.out.println("IOObject: \n");
				System.out.println(ioObject.toString());
				System.out.println("\n");
			}

		} catch (OperatorException ex) {
			log.info("Se ha liado el taco al ejecutar el proceso.");
		}
	}
}

package com.fmsanjuan.rapid;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jfree.util.Log;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.io.RepositorySource;
import com.rapidminer.operator.learner.meta.SDRulesetInduction;
import com.rapidminer.operator.learner.rules.BestRuleInduction;
import com.rapidminer.tools.OperatorService;

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
		Process process = GenericProcesses.getRipper();

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

	public static Process changeData(Process process) {

		List<Operator> operators = new ArrayList<Operator>(
				process.getAllOperators());
		System.out.println("Nœmero de Operators: " + operators.size());

		for (Operator operator : operators) {
			System.out.println(operator.toString());
		}

		try {
			Operator data = OperatorService
					.createOperator(RepositorySource.class);
			data.setParameter(RepositorySource.PARAMETER_REPOSITORY_ENTRY,
					"//Samples/data/Iris");

			Operator ruleInduction = OperatorService
					.createOperator(BestRuleInduction.class);

		} catch (OperatorCreationException e) {
			e.printStackTrace();
		}

		return process;
	}

	public static Process createRuleInductionProcess() {

		Process process = null;
		try {
			process = new Process();
			// Datos
			Operator data = OperatorService
					.createOperator(RepositorySource.class);
			data.setParameter(RepositorySource.PARAMETER_REPOSITORY_ENTRY,
					"//Samples/data/Golf");
			// Operador de rule induction
			Operator ruleInduction = OperatorService
					.createOperator(SDRulesetInduction.class);
			// A–adiendo operadores al proceso
			process.getRootOperator().getSubprocess(0).addOperator(data);
			process.getRootOperator().getSubprocess(0)
					.addOperator(ruleInduction);
			System.out.println(process.toString());
		} catch (OperatorCreationException e) {
			log.info("Error creando el proceso de Rule Induction");
		}
		return process;
	}

}

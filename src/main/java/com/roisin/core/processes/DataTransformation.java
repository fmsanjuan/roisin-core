package com.roisin.core.processes;

import org.apache.log4j.Logger;

import com.rapidminer.Process;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.io.ArffExampleSetWriter;
import com.rapidminer.operator.nio.ExcelExampleSource;
import com.rapidminer.operator.ports.metadata.CompatibilityLevel;
import com.rapidminer.tools.OperatorService;

public class DataTransformation {

	public static Logger log = Logger.getLogger(DataTransformation.class);

	/**
	 * Este método devuelve el proceso que convierte un fichero excel en arff.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertExcelToArff(String input, String output) {
		Process process = new Process();
		// Getting excel file
		try {
			ExcelExampleSource excelFile = OperatorService.createOperator(ExcelExampleSource.class);
			excelFile.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, input);
			// Arff writer operator
			ArffExampleSetWriter arffWriter = OperatorService
					.createOperator(ArffExampleSetWriter.class);
			arffWriter.setParameter(ArffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(excelFile);
			process.getRootOperator().getSubprocess(0).addOperator(arffWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero excel en arff" + e);
		}
		System.out.println(process);
		return process;
	}

	public static Process convertArffToExcel(String input, String output) {
		return null;
	}

	public static Process convertXrffToExcel(String input, String output) {
		return null;

	}

	public static Process convertExcelToXrff(String input, String output) {
		return null;

	}

}

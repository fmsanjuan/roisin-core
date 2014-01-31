package com.roisin.core.processes;

import org.apache.log4j.Logger;

import com.rapidminer.Process;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.io.ArffExampleSetWriter;
import com.rapidminer.operator.io.ArffExampleSource;
import com.rapidminer.operator.io.ExcelExampleSetWriter;
import com.rapidminer.operator.io.XrffExampleSetWriter;
import com.rapidminer.operator.io.XrffExampleSource;
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
		try {
			// Getting excel file
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
		return process;
	}

	/**
	 * Este método devuelve el proceso que convierte un fichero arff en excel.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertArffToExcel(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero arff
			ArffExampleSource arffExample = OperatorService.createOperator(ArffExampleSource.class);
			arffExample.setParameter(ArffExampleSource.PARAMETER_DATA_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(arffExample);
			process.getRootOperator().getSubprocess(0).addOperator(excelWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero arff en excel");
		}
		return process;
	}

	/**
	 * Este método devuelve el proceso que convierte un fichero xrff en excel.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertXrffToExcel(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero xrff
			XrffExampleSource xrffExample = OperatorService.createOperator(XrffExampleSource.class);
			xrffExample.setParameter(XrffExampleSource.PARAMETER_DATA_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(xrffExample);
			process.getRootOperator().getSubprocess(0).addOperator(excelWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero xrff en excel");
		}
		return process;
	}

	/**
	 * Este método devuelve el proceso que convierte un fichero excel en xrff.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertExcelToXrff(String input, String output) {
		Process process = new Process();
		try {
			// Getting excel file
			ExcelExampleSource excelFile = OperatorService.createOperator(ExcelExampleSource.class);
			excelFile.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, input);
			// Xrff writer operator
			XrffExampleSetWriter xrffWriter = OperatorService
					.createOperator(XrffExampleSetWriter.class);
			xrffWriter.setParameter(XrffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(excelFile);
			process.getRootOperator().getSubprocess(0).addOperator(xrffWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero excel en xrff" + e);
		}
		return process;
	}

}

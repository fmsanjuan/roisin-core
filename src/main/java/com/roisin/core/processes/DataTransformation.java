package com.roisin.core.processes;

import org.apache.log4j.Logger;

import com.rapidminer.Process;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.io.ArffExampleSetWriter;
import com.rapidminer.operator.io.ArffExampleSource;
import com.rapidminer.operator.io.CSVExampleSetWriter;
import com.rapidminer.operator.io.ExcelExampleSetWriter;
import com.rapidminer.operator.io.XrffExampleSetWriter;
import com.rapidminer.operator.io.XrffExampleSource;
import com.rapidminer.operator.nio.CSVExampleSource;
import com.rapidminer.operator.nio.ExcelExampleSource;
import com.rapidminer.operator.ports.metadata.CompatibilityLevel;
import com.rapidminer.tools.OperatorService;

/**
 * Implementaci�n de m�todos para la conversi�n de ficheros que contienen datos
 * de ejemplo que ser�n procesados en Roisin.
 * 
 * @author F�lix Miguel Sanju�n Segovia <fmsanse@gmail.com>
 * 
 */
public class DataTransformation {

	/**
	 * Log
	 */
	public static Logger log = Logger.getLogger(DataTransformation.class);

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero excel en arff.
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
	 * Este método devuelve el proceso que convierte un fichero arff en xls.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertArffToXls(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero arff
			ArffExampleSource arffExample = OperatorService.createOperator(ArffExampleSource.class);
			arffExample.setParameter(ArffExampleSource.PARAMETER_DATA_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT,
					ExcelExampleSetWriter.FILE_FORMAT_XLS);
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
	 * Este método devuelve el proceso que convierte un fichero arff en excel.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertArffToXlsx(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero arff
			ArffExampleSource arffExample = OperatorService.createOperator(ArffExampleSource.class);
			arffExample.setParameter(ArffExampleSource.PARAMETER_DATA_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT,
					ExcelExampleSetWriter.FILE_FORMAT_XLSX);
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
	 * Este método devuelve el proceso que convierte un fichero xrff en xls.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertXrffToXls(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero xrff
			XrffExampleSource xrffExample = OperatorService.createOperator(XrffExampleSource.class);
			xrffExample.setParameter(XrffExampleSource.PARAMETER_DATA_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT,
					ExcelExampleSetWriter.FILE_FORMAT_XLS);
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
	 * Este método devuelve el proceso que convierte un fichero xrff en xlsx.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertXrffToXlsx(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero xrff
			XrffExampleSource xrffExample = OperatorService.createOperator(XrffExampleSource.class);
			xrffExample.setParameter(XrffExampleSource.PARAMETER_DATA_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT,
					ExcelExampleSetWriter.FILE_FORMAT_XLSX);
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
	 * Este m�todo devuelve el proceso que convierte un fichero excel en xrff.
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

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero excel en csv.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertExcelToCsv(String input, String output) {
		Process process = new Process();
		try {
			// Getting excel file
			ExcelExampleSource excelFile = OperatorService.createOperator(ExcelExampleSource.class);
			excelFile.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, input);
			// CSV writer operator
			CSVExampleSetWriter csvWriter = OperatorService
					.createOperator(CSVExampleSetWriter.class);
			csvWriter.setParameter(CSVExampleSetWriter.PARAMETER_CSV_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(excelFile);
			process.getRootOperator().getSubprocess(0).addOperator(csvWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero excel en csv" + e);
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero csv en xls.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertCsvToXls(String input, String output) {
		Process process = new Process();
		try {
			// Getting CSV file
			CSVExampleSource csvFile = OperatorService.createOperator(CSVExampleSource.class);
			csvFile.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT,
					ExcelExampleSetWriter.FILE_FORMAT_XLS);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(csvFile);
			process.getRootOperator().getSubprocess(0).addOperator(excelWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero csv en excel" + e);
		}
		return process;
	}

	/**
	 * Este método devuelve el proceso que convierte un fichero csv en xlsx.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertCsvToXlsx(String input, String output) {
		Process process = new Process();
		try {
			// Getting CSV file
			CSVExampleSource csvFile = OperatorService.createOperator(CSVExampleSource.class);
			csvFile.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, input);
			// Operador para la escritura del fichero excel
			ExcelExampleSetWriter excelWriter = OperatorService
					.createOperator(ExcelExampleSetWriter.class);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, output);
			excelWriter.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT,
					ExcelExampleSetWriter.FILE_FORMAT_XLSX);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(csvFile);
			process.getRootOperator().getSubprocess(0).addOperator(excelWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero csv en excel" + e);
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero csv en arff.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertCsvToArff(String input, String output) {
		Process process = new Process();
		try {
			// Getting CSV file
			CSVExampleSource csvFile = OperatorService.createOperator(CSVExampleSource.class);
			csvFile.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, input);
			// Arff writer operator
			ArffExampleSetWriter arffWriter = OperatorService
					.createOperator(ArffExampleSetWriter.class);
			arffWriter.setParameter(ArffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(csvFile);
			process.getRootOperator().getSubprocess(0).addOperator(arffWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero csv en excel" + e);
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero csv en xrff.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertCsvToXrff(String input, String output) {
		Process process = new Process();
		try {
			// Getting CSV file
			CSVExampleSource csvFile = OperatorService.createOperator(CSVExampleSource.class);
			csvFile.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, input);
			// Xrff writer operator
			XrffExampleSetWriter xrffWriter = OperatorService
					.createOperator(XrffExampleSetWriter.class);
			xrffWriter.setParameter(XrffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(csvFile);
			process.getRootOperator().getSubprocess(0).addOperator(xrffWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero csv en excel" + e);
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero arff en csv.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertArffToCsv(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero arff
			ArffExampleSource arffExample = OperatorService.createOperator(ArffExampleSource.class);
			arffExample.setParameter(ArffExampleSource.PARAMETER_DATA_FILE, input);
			// CSV writer operator
			CSVExampleSetWriter csvWriter = OperatorService
					.createOperator(CSVExampleSetWriter.class);
			csvWriter.setParameter(CSVExampleSetWriter.PARAMETER_CSV_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(arffExample);
			process.getRootOperator().getSubprocess(0).addOperator(csvWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero arff en excel");
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero arff en xrff.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertArffToXrff(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero arff
			ArffExampleSource arffExample = OperatorService.createOperator(ArffExampleSource.class);
			arffExample.setParameter(ArffExampleSource.PARAMETER_DATA_FILE, input);
			// Xrff writer operator
			XrffExampleSetWriter xrffWriter = OperatorService
					.createOperator(XrffExampleSetWriter.class);
			xrffWriter.setParameter(XrffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(arffExample);
			process.getRootOperator().getSubprocess(0).addOperator(xrffWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero arff en excel");
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero xrff en csv.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertXrffToCsv(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero xrff
			XrffExampleSource xrffExample = OperatorService.createOperator(XrffExampleSource.class);
			xrffExample.setParameter(XrffExampleSource.PARAMETER_DATA_FILE, input);
			// CSV writer operator
			CSVExampleSetWriter csvWriter = OperatorService
					.createOperator(CSVExampleSetWriter.class);
			csvWriter.setParameter(CSVExampleSetWriter.PARAMETER_CSV_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(xrffExample);
			process.getRootOperator().getSubprocess(0).addOperator(csvWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero xrff en excel");
		}
		return process;
	}

	/**
	 * Este m�todo devuelve el proceso que convierte un fichero xrff en arff.
	 * 
	 * @param input
	 *            path del archivo origen
	 * @param output
	 *            path del archivo destino
	 * @return process proceso
	 */
	public static Process convertXrffToArff(String input, String output) {
		Process process = new Process();
		try {
			// Operador para la lectura del fichero xrff
			XrffExampleSource xrffExample = OperatorService.createOperator(XrffExampleSource.class);
			xrffExample.setParameter(XrffExampleSource.PARAMETER_DATA_FILE, input);
			// Arff writer operator
			ArffExampleSetWriter arffWriter = OperatorService
					.createOperator(ArffExampleSetWriter.class);
			arffWriter.setParameter(ArffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, output);
			// Adding operators to process
			process.getRootOperator().getSubprocess(0).addOperator(xrffExample);
			process.getRootOperator().getSubprocess(0).addOperator(arffWriter);
			// Operator connection
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible transformar el fichero xrff en excel");
		}
		return process;
	}

}

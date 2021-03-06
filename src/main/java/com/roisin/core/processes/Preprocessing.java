package com.roisin.core.processes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.rapidminer.Process;
import com.rapidminer.operator.Operator;
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
import com.rapidminer.operator.preprocessing.filter.ExampleFilter;
import com.rapidminer.operator.preprocessing.filter.ExampleRangeFilter;
import com.rapidminer.operator.preprocessing.filter.attributes.AttributeFilter;
import com.rapidminer.tools.OperatorService;
import com.roisin.core.utils.Constants;

import exception.RoisinException;

/**
 * Clase que contiene los métodos necesarios para llevar a cabo las tareas de
 * preprocesamiento de datos.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * 
 */
public class Preprocessing {

	/**
	 * Log
	 */
	private static Logger log = Logger.getLogger(Preprocessing.class);

	/**
	 * Este método devuelve el operador correspondiente a la lectura de datos
	 * según el formato indicado.
	 * 
	 * @param format
	 *            formato del fichero
	 * @param path
	 *            ruta en la que se encuentra el fichero
	 * @return operador
	 */
	public static Operator getReader(String path) {
		Operator reader = null;
		try {
			String format = StringUtils.substringAfterLast(path, Constants.DOT_SYMBOL);
			if (format.equals(Constants.EXCEL_FORMAT) || format.equals(Constants.EXCEL_FORMAT_2007)) {
				reader = OperatorService.createOperator(ExcelExampleSource.class);
				reader.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, path);
			} else if (format.equals(Constants.ARFF_FORMAT)) {
				reader = OperatorService.createOperator(ArffExampleSource.class);
				reader.setParameter(ArffExampleSource.PARAMETER_DATA_FILE, path);
			} else if (format.equals(Constants.XRFF_FORMAT)) {
				reader = OperatorService.createOperator(XrffExampleSource.class);
				reader.setParameter(XrffExampleSource.PARAMETER_DATA_FILE, path);
			} else if (format.equals(Constants.CSV_FORMAT)) {
				reader = OperatorService.createOperator(CSVExampleSource.class);
				reader.setParameter(CSVExampleSource.PARAMETER_CSV_FILE, path);
			} else {
				throw new IllegalArgumentException("Formato de entrada no válido");
			}
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible obtener información del archivo indicado");
		}
		return reader;
	}

	/**
	 * Este método devuelve el operador correspondiente a la escritura de datos
	 * (ExampleSet) a partir del formato indicado.
	 * 
	 * @param format
	 *            formato del fichero
	 * @param path
	 *            ruta donde se guardará el fichero
	 * @return
	 */
	public static Operator getWriter(String path) {
		Operator writer = null;
		try {
			String format = StringUtils.substringAfterLast(path, Constants.DOT_SYMBOL);
			if (format.equals(Constants.EXCEL_FORMAT) || format.equals(Constants.EXCEL_FORMAT_2007)) {
				writer = OperatorService.createOperator(ExcelExampleSetWriter.class);
				writer.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, path);
				writer.setParameter(ExcelExampleSetWriter.PARAMETER_FILE_FORMAT, format);
			} else if (format.equals(Constants.ARFF_FORMAT)) {
				writer = OperatorService.createOperator(ArffExampleSetWriter.class);
				writer.setParameter(ArffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, path);
			} else if (format.equals(Constants.XRFF_FORMAT)) {
				writer = OperatorService.createOperator(XrffExampleSetWriter.class);
				writer.setParameter(XrffExampleSetWriter.PARAMETER_EXAMPLE_SET_FILE, path);
			} else if (format.equals(Constants.CSV_FORMAT)) {
				writer = OperatorService.createOperator(CSVExampleSetWriter.class);
				writer.setParameter(CSVExampleSetWriter.PARAMETER_CSV_FILE, path);
			} else {
				throw new IllegalArgumentException("Formato de entrada no válido");
			}
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible obtener información del archivo indicado");
		}
		return writer;
	}

	/**
	 * Este método devuelve un operador que realiza un filtrado de registros del
	 * conjunto de datos devolviendo aquellos ejemplos que no cumplen con la
	 * condición que se pasa como parámetro.
	 * 
	 * @param filterCondition
	 *            condición para el filtrado
	 * @return
	 */
	public static Operator getExampleFilter(String filterCondition) {
		ExampleFilter exampleFilter = null;
		try {
			exampleFilter = OperatorService.createOperator(ExampleFilter.class);
			exampleFilter.setParameter("condition_class", "attribute_value_filter");
			exampleFilter.setParameter("parameter_string", filterCondition);
			exampleFilter.setParameter("invert_filter", "true");
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible obtener el operador para filtrar ejemplos del conjunto de datos original");
		}
		return exampleFilter;
	}

	/**
	 * Este método devuelve un operador que realiza un filtrado de registros del
	 * conjunto de datos devolviendo aquellos recursos que cumplen la condición
	 * que se pasa como parámetro.
	 * 
	 * @param filterCondition
	 *            condición para el filtrado
	 * @return
	 */
	public static Operator getExampleFilterNoInverter(String filterCondition) {
		ExampleFilter exampleFilter = null;
		try {
			exampleFilter = OperatorService.createOperator(ExampleFilter.class);
			exampleFilter.setParameter("condition_class", "attribute_value_filter");
			exampleFilter.setParameter("parameter_string", filterCondition);
			exampleFilter.setParameter("invert_filter", "false");
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible obtener el operador para filtrar ejemplos del conjunto de datos original");
		}
		return exampleFilter;
	}

	/**
	 * Este método devuelve un operador que elimina atributos (columnas) del
	 * conjunto de datos que va a ser procesado.
	 * 
	 * @param seleccion
	 *            lista con los attributos del conjunto que deben mantenerse
	 * @return attributeFilter operador para el filtrado de atributos
	 */
	public static Operator getAttributeSelection(List<String> seleccion) {
		if (seleccion.isEmpty()) {
			throw new IllegalArgumentException("El filtro no contiene attributos");
		}
		String filtro = new String();
		for (String attributo : seleccion) {
			filtro = filtro.concat(attributo + "|");
		}
		AttributeFilter attributeFilter = null;
		try {
			attributeFilter = OperatorService.createOperator(AttributeFilter.class);
			attributeFilter.setParameter("attribute_filter_type", "subset");
			attributeFilter.setParameter("attributes", filtro);

		} catch (OperatorCreationException e) {
			log.error("No ha sido posible obtener el operador para seleccionar attributos del conjunto de datos original");
		}
		return attributeFilter;
	}

	/**
	 * Devuelve un operador que realiza un filtrado de conjunto de datos de
	 * ejemplo a partir del rango pasado como parámetro.
	 * 
	 * @param inicio
	 *            primera fila del rango
	 * @param fin
	 *            última fila del rango
	 * @return
	 */
	public static ExampleRangeFilter getExampleRangeFilter(int inicio, int fin) {
		ExampleRangeFilter rangeFilter = null;
		try {
			rangeFilter = OperatorService.createOperator(ExampleRangeFilter.class);
			rangeFilter.setParameter(ExampleRangeFilter.PARAMETER_FIRST_EXAMPLE,
					Integer.toString(inicio));
			rangeFilter.setParameter(ExampleRangeFilter.PARAMETER_LAST_EXAMPLE,
					Integer.toString(fin));
			rangeFilter.setParameter(ExampleRangeFilter.PARAMETER_INVERT_FILTER, Constants.TRUE);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el filtro por filas => " + e);
		}
		return rangeFilter;
	}

	/**
	 * Este método devuelve una lista con todos los filtros de ejemplos por
	 * rango necesarios para eliminar las filas que se indican en el conjunto de
	 * enteros ordenado que se pasa como parámetro.
	 * 
	 * @param deletedRows
	 *            filas que se desean eliminar
	 * @return filters operadores encargados de realizar el filtrado
	 */
	public static List<ExampleRangeFilter> getExampleFilterByRow(SortedSet<Integer> deletedRows) {
		List<ExampleRangeFilter> filters = new ArrayList<ExampleRangeFilter>();
		// El siguiente algoritmo calcula aquellas filas que pueden ser
		// evaluadas como un rango de filas.
		Iterator<Integer> iterator = deletedRows.iterator();
		if (iterator.hasNext()) {
			// Marca el inicio del rango en caso de que a y b sean consecutivos.
			int inicio = -1;
			// Esta variable es un contador que marca el número de ejemplos
			// borrados.
			int ejemplosBorrados = 0;
			int a = iterator.next();
			while (iterator.hasNext()) {
				int b = iterator.next();
				inicio = inicio == -1 ? a : inicio;
				if (b - a > 1) {
					// Si no son consecutivos, se crea el filtro con el último
					// rango hasta a.
					ExampleRangeFilter rangeFilter = getExampleRangeFilter(inicio
							- ejemplosBorrados, a - ejemplosBorrados);
					filters.add(rangeFilter);
					// Se incrementa el contador con el número de ejemplos que
					// han sido filtrados.
					ejemplosBorrados += a - inicio + 1;
					inicio = -1;
				}
				a = b;
			}
			inicio = inicio == -1 ? a : inicio;
			ExampleRangeFilter rangeFilter = getExampleRangeFilter(inicio - ejemplosBorrados, a
					- ejemplosBorrados);
			filters.add(rangeFilter);
		}
		return filters;
	}

	/**
	 * Devuelve el proceso con todos los operadores relacionados con la fase de
	 * preprocesamiento.
	 * 
	 * @param format
	 * @param path
	 * @param filterCondition
	 * @param attributeSelection
	 * @return
	 * @throws RoisinException
	 */
	public static Process getPreprocessedData(String path, SortedSet<Integer> rowFilter,
			String filterCondition, List<String> attributeSelection) throws RoisinException {
		Process process = new Process();
		Operator reader = Preprocessing.getReader(path);
		process.getRootOperator().getSubprocess(0).addOperator(reader);
		// Filtrado de filas
		if (rowFilter != null && !rowFilter.isEmpty()) {
			List<ExampleRangeFilter> filters = getExampleFilterByRow(rowFilter);
			for (ExampleRangeFilter exampleRangeFilter : filters) {
				process.getRootOperator().getSubprocess(0).addOperator(exampleRangeFilter);
			}
		}
		// Filtrado de filas mediante condición
		if (!StringUtils.isBlank(filterCondition)) {
			process.getRootOperator().getSubprocess(0)
					.addOperator(Preprocessing.getExampleFilter(filterCondition));
		}
		// Filtrado de columnas
		if (attributeSelection != null) {
			// Eliminada validación de clase contenida en atributos
			// seleccionados para la exportación de datos en un fichero. La
			// validación se realiza ahora en spring.
			process.getRootOperator().getSubprocess(0)
					.addOperator(Preprocessing.getAttributeSelection(attributeSelection));
		}
		return process;
	}

	/**
	 * Devuelve un proceso que al ejecutarse preprocesa toda la información
	 * según los parámetros datos en un fichero cuyo formato se pasa como
	 * parámetro.
	 * 
	 * @param inputFormat
	 * @param inputPath
	 * @param rowFilter
	 * @param filterCondition
	 * @param attributeSelection
	 * @param outputFormat
	 * @param outputPath
	 * @return
	 * @throws RoisinException
	 */
	public static Process getPreprocessedDataFile(String inputPath, SortedSet<Integer> rowFilter,
			String filterCondition, List<String> attributeSelection, String outputPath)
			throws RoisinException {
		Process process = getPreprocessedData(inputPath, rowFilter, filterCondition,
				attributeSelection);
		process.getRootOperator().getSubprocess(0).addOperator(getWriter(outputPath));
		// Auto wire connects the last operator to result 1 automatically.
		process.getRootOperator().getSubprocess(0)
				.autoWire(CompatibilityLevel.VERSION_5, true, true);
		return process;
	}

	/**
	 * Este operador devuelve un proceso que lee de un fichero y preprocesa
	 * (filtra, elimina filas, etc.) la información del mismo y la devuelve en
	 * un objeto ExampleSet.
	 * 
	 * @param inputPath
	 * @param rowFilter
	 * @param filterCondition
	 * @param attributeSelection
	 * @return
	 * @throws RoisinException
	 */
	public static Process getPreprocessedExampleSet(String inputPath, SortedSet<Integer> rowFilter,
			String filterCondition, List<String> attributeSelection) throws RoisinException {
		Process process = getPreprocessedData(inputPath, rowFilter, filterCondition,
				attributeSelection);
		// Auto wire connects the last operator to result 1 automatically.
		process.getRootOperator().getSubprocess(0)
				.autoWire(CompatibilityLevel.VERSION_5, true, true);
		return process;
	}

	/**
	 * Este método devuelve un proceso que devuelve un ExampleSet a partir de el
	 * path de un fichero y su correspondiente formato.
	 * 
	 * @param format
	 * @param path
	 * @return
	 */
	public static Process getExampleSetFromFileProcess(String format, String path) {
		Process process = new Process();
		Operator reader = getReader(path);
		process.getRootOperator().getSubprocess(0).addOperator(reader);
		process.getRootOperator().getSubprocess(0)
				.autoWire(CompatibilityLevel.VERSION_5, true, true);
		return process;
	}

	/**
	 * Este método devuelve un proceso que devuelve un conjunto de datos de
	 * ejemplo que cumple una condición concreta.
	 * 
	 * @param path
	 * @param rowFilter
	 * @param filterCondition
	 * @param attributeSelection
	 * @return
	 * @throws RoisinException
	 */
	public static Process getConditionFilteredData(String path, String filterCondition) {
		Process process = new Process();
		Operator reader = Preprocessing.getReader(path);
		process.getRootOperator().getSubprocess(0).addOperator(reader);
		// Filtrado de filas mediante condición
		if (!StringUtils.isBlank(filterCondition)) {
			process.getRootOperator().getSubprocess(0)
					.addOperator(Preprocessing.getExampleFilterNoInverter(filterCondition));
		}
		process.getRootOperator().getSubprocess(0)
				.autoWire(CompatibilityLevel.VERSION_5, true, true);
		return process;
	}
}

package com.roisin.core.processes;

import java.util.List;

import org.apache.log4j.Logger;

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.io.ArffExampleSource;
import com.rapidminer.operator.io.XrffExampleSource;
import com.rapidminer.operator.nio.CSVExampleSource;
import com.rapidminer.operator.nio.ExcelExampleSource;
import com.rapidminer.operator.preprocessing.filter.ExampleFilter;
import com.rapidminer.operator.preprocessing.filter.attributes.AttributeFilter;
import com.rapidminer.tools.OperatorService;
import com.roisin.core.utils.Constants;

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
	public static Operator getReader(String format, String path) {
		Operator reader = null;
		try {
			if (format.equals(Constants.EXCEL_FORMAT)) {
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
	 * Este método devuelve un operador que realiza un filtrado de registros del
	 * conjunto de datos.
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
}

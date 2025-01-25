package service;

import java.util.logging.Logger;

import org.mariuszgromada.math.mxparser.Expression;

public class CalculatorService {
	
	private static final Logger logger = Logger.getLogger(CalculatorService.class.getName());

	public double process(String action, String currentResult) throws Exception {
		logger.info("Processing action: " + action + ", with currentResult: " + currentResult);
        double result = 0;

        if (action != null) {
            switch (action) {
                case "C":
                    result = 0;
                    logger.info("Action C: Result reset to 0");
                    break;
                case "=":
                    result = eval(currentResult);
                    logger.info("Action =: Evaluated result: " + result);
                    break;
                case "x^2":
                    result = Math.pow(Double.parseDouble(currentResult), 2);
                    logger.info("Action x^2: Result is " + result);
                    break;
                case "√x":
                    result = Math.sqrt(Double.parseDouble(currentResult));
                    logger.info("Action √x: Result is " + result);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid action: " + action);
            }
        }

        return result;
    }

    private double eval(String expression) throws Exception {
    	logger.info("Evaluating expression: " + expression);
        expression = expression.trim().replaceAll("\\s+", "");
        Expression exp = new Expression(expression);
        double result = exp.calculate();
        if (Double.isNaN(result)) {
        	logger.warning("Invalid expression: " + expression);
            throw new IllegalArgumentException("Expresión inválida: " + expression);
        }
        return result;
    }
}

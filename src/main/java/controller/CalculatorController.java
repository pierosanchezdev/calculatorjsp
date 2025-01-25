package controller;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CalculatorService;

@WebServlet("/calculator")
public class CalculatorController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CalculatorController.class.getName());
    private CalculatorService calculatorService = new CalculatorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String currentResult = request.getParameter("result");
        Boolean isOperationExecuted = (Boolean) request.getSession().getAttribute("isOperationExecuted");
        Boolean isChainingOperation = (Boolean) request.getSession().getAttribute("isChainingOperation");

        if (isOperationExecuted == null) {
            isOperationExecuted = false;
        }
        if (isChainingOperation == null) {
            isChainingOperation = false;
        }

        logger.info("Action received: " + action);
        logger.info("Current result: " + currentResult);

        try {
            if (currentResult == null || currentResult.equals("0.0")) {
                currentResult = "";
            }
            if (action.matches("[0-9]") || action.equals(".")) {
                if (isOperationExecuted && !isChainingOperation) {
                    currentResult = "";
                    isOperationExecuted = false;
                }

                String[] parts = currentResult.trim().split(" ");
                String currentEntry = parts[parts.length - 1];
                if (!(action.equals(".") && currentEntry.contains("."))) {
                    currentResult += action;
                    logger.info("Appended number or decimal: " + currentResult);
                } else {
                    logger.info("Decimal already exists, skipping action.");
                }
            }
            else if (action.equals("C")) {
                currentResult = "0.0";
                isOperationExecuted = false;
                isChainingOperation = false;
                logger.info("Reset result to: " + currentResult);
            }
            else if (action.equals("=")) {
                logger.info("Evaluating expression: " + currentResult);
                double result = calculatorService.process(action, currentResult);
                currentResult = String.valueOf(result);
                logger.info("Evaluation result: " + currentResult);
                isOperationExecuted = true;
                isChainingOperation = false;
            }
            else if (action.equals("x^2") || action.equals("√x") || action.equals("1/x")) {
                currentResult = handleSpecificOperation(action, currentResult);
                isOperationExecuted = true;
                isChainingOperation = false;
            }
            else if (action.equals("%")) {
                try {
                    String[] parts = currentResult.trim().split(" ");
                    if (parts.length == 1) {
                        double number = Double.parseDouble(parts[0]);
                        currentResult = String.valueOf(number / 100);
                        logger.info("Converted to percentage: " + currentResult);
                        isOperationExecuted = true;
                    } else if (parts.length == 3) {
                        double base = Double.parseDouble(parts[0]);
                        double percentage = Double.parseDouble(parts[2]) / 100;
                        double result = base * percentage;
                        currentResult = parts[0] + " " + parts[1] + " " + result;
                        logger.info("Calculated percentage in operation: " + currentResult);
                        isOperationExecuted = true;
                    }
                } catch (NumberFormatException e) {
                    logger.severe("Invalid number format for % operation: " + currentResult);
                    currentResult = "Error";
                    isOperationExecuted = true;
                }
            }
            else if (action.equals("±")) {
                currentResult = toggleSign(currentResult);
            }
            else if (action.equals("DEL")) {
                currentResult = deleteLastCharacter(currentResult);
            }
            else if (action.equals("CE")) {
                if (currentResult.matches(".*[+\\-*/].*")) {
                    String[] parts = currentResult.trim().split(" ");
                    if (parts.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < parts.length - 1; i++) {
                            sb.append(parts[i]).append(" ");
                        }
                        currentResult = sb.toString().trim();
                    } else {
                        currentResult = "0.0";
                    }
                } else {
                    currentResult = "0.0";
                }
                logger.info("Cleared entry, current result: " + currentResult);
            }
            else if (action.matches("[+\\-*/]")) {
                if (currentResult.equals("Error")) {
                    logger.warning("Cannot append operator because current result is an error.");
                    return;
                }

                if (isOperationExecuted) {
                    isOperationExecuted = false;
                    isChainingOperation = true;
                }

                currentResult = currentResult.trim();

                if (currentResult.matches(".*[+\\-*/]$")) {
                    currentResult = currentResult.substring(0, currentResult.length() - 1) + action;
                    logger.info("Replaced operator with: " + action);
                } else {
                    currentResult += " " + action + " ";
                    logger.info("Appended operator: " + currentResult);
                }
            }

            request.setAttribute("result", currentResult);
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage());
            request.setAttribute("result", "Error");
        }

        request.getSession().setAttribute("isOperationExecuted", isOperationExecuted);
        request.getSession().setAttribute("isChainingOperation", isChainingOperation);
        request.getRequestDispatcher("calculator.jsp").forward(request, response);
    }


    private String handleSpecificOperation(String action, String currentResult) {
        try {
            double number = Double.parseDouble(currentResult);

            switch (action) {
                case "x^2":
                    return String.valueOf(Math.pow(number, 2));
                case "√x":
                    if (number < 0) {
                        logger.severe("Cannot calculate square root of a negative number: " + number);
                        return "Error";
                    }
                    return String.valueOf(Math.sqrt(number));
                case "1/x":
                    if (number == 0) {
                        logger.severe("Cannot calculate 1/x for zero.");
                        return "Error";
                    }
                    return String.valueOf(1 / number);
                default:
                    return "Error";
            }
        } catch (NumberFormatException e) {
            logger.severe("Invalid number format for operation: " + action);
            return "Error";
        }
    }

    private String toggleSign(String currentResult) {
        try {
            double number = Double.parseDouble(currentResult);
            number = -number;
            logger.info("Changed sign of current result: " + number);
            return String.valueOf(number);
        } catch (NumberFormatException e) {
            logger.severe("Invalid number format for ± operation: " + currentResult);
            return "Error";
        }
    }

    private String deleteLastCharacter(String currentResult) {
        if (!currentResult.isEmpty()) {
            currentResult = currentResult.substring(0, currentResult.length() - 1);
            logger.info("Deleted last character: " + currentResult);

            if (currentResult.isEmpty() || currentResult.equals("-")) {
                currentResult = "0.0";
                logger.info("Result reset to 0.0 after deleting the last character");
            }
        } else {
            currentResult = "0.0";
            logger.info("Result was already empty, reset to 0.0");
        }
        return currentResult;
    }
}

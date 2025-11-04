package com.test.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.test.exception.InvalidDataException;
import com.test.model.Employee;
import com.test.model.ImportSummary;
import com.test.model.Position;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class ImportService {
    private final EmployeeService employeeService;
    int lineCount = 0;
    public ImportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ImportSummary importFromCsv(String filePath) throws IOException, CsvException {
        ImportSummary importSummary = new ImportSummary();
        Path path = Path.of(filePath);

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();

            try {
                for (int i = 1; i < allRows.size(); i++) {
                    String[] row = allRows.get(i);
                    String firstName = row[0];
                    String lastName = row[1];
                    String email = row[2];
                    String companyName = row[3];
                    String positionStr = row[4];
                    Double salary = Double.parseDouble(row[5]);

                    Position p;
                    try {
                        p = Position.getPosition(positionStr);
                    } catch (IllegalArgumentException e) {
                        throw new InvalidDataException("Invalid position: " + positionStr, e);
                    }

                    try {
                        if (salary <= 0) {
                            throw new InvalidDataException("Salary cannot be less than 0: " + salary);
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidDataException("Invalid salary format: " + salary, e);
                    }
                    Employee employee = new Employee(firstName, lastName, email, companyName, p);
                    employee.setSalary(salary);

                    if (employeeService.SearchEmployee(email) == null) {
                        employeeService.addEmployee(employee);
                        importSummary.incrementImportedCount();
                    } else {
                        throw new InvalidDataException("Email already taken: " + email);
                    }
                }
            } catch (InvalidDataException | IllegalArgumentException e) {
                importSummary.addError(lineCount, e.getMessage());
            }
        } catch (IOException e) {
            importSummary.addError(0, "Error: cannot read CSV file" + e.getMessage());
        }
        return importSummary;
    }

}

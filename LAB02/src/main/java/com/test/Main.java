package com.test;

import com.opencsv.exceptions.CsvException;
import com.test.exception.ApiException;
import com.test.model.CompanyStatistics;
import com.test.model.Employee;
import com.test.model.ImportSummary;
import com.test.service.ApiService;
import com.test.service.EmployeeService;
import com.test.service.ImportService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String CSV_FILE_PATH = "employees.csv";

    public static void main(String[] args) throws IOException, CsvException {

        EmployeeService employeeService = new EmployeeService();
        ImportService importService = new ImportService(employeeService);
        ApiService apiService = new ApiService();

        System.out.println("\nCSV FILE IMPORT: (" + CSV_FILE_PATH + ") ---");
        ImportSummary summary = importService.importFromCsv(CSV_FILE_PATH);
        System.out.println(summary);

        if (summary.getImportedCount() > 0) {
            System.out.println("\nImported employees:");
            employeeService.getEmployees().forEach(e ->
                    System.out.println("- " + e.getName() + " " + e.getSurname() + " (" + e.getJobTitle().getName() + ")")
            );
        }

        System.out.println("\n REST API SYNC");
        try {
            List<Employee> apiEmployees = apiService.fetchEmployeesFromApi();

            int newEmployees = 0;
            for (Employee employee : apiEmployees) {
                if (employeeService.SearchEmployee(employee.getEmail()) == null) {
                    employeeService.addEmployee(employee);
                    newEmployees++;
                } else {
                    continue;
                }
            }

            System.out.println("Downloaded and added " + newEmployees + " new employees from API.");
            System.out.println("Total employees count: " + employeeService.getEmployees().size());

        } catch (ApiException e) {
            System.err.println("Error API integration: " + e.getMessage());
        }

        List<Employee> inconsistentSalaryEmployees = employeeService.validateSalaryConsistency();
        System.out.println("\n[1] Employees with salary lower than the base (" + inconsistentSalaryEmployees.size() + " employees):");
        if (inconsistentSalaryEmployees.isEmpty()) {
            System.out.println("No employees with salary lower than the base");
        } else {
            for (Employee e : inconsistentSalaryEmployees) {
                System.out.println("   - " + e.getName() + " " + e.getSurname() +
                        " (" + e.getJobTitle().getName() +
                        "): " + String.format("%.2f", e.getSalary()) +
                        " PLN (Baza: " + String.format("%.2f", e.getJobTitle().getSalary()) + " PLN)");
            }
        }

        Map<String, CompanyStatistics> companyStatistics = employeeService.getCompanyStatistics();
        System.out.println("\n[B] Statistics based on company:");
        for (Map.Entry<String, CompanyStatistics> entry : companyStatistics.entrySet()) {
            System.out.println("\nCompany: " + entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}
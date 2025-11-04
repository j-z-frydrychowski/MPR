package com.test.service;

import com.test.model.CompanyStatistics;
import com.test.model.Employee;
import com.test.model.Employee;
import com.test.model.Position;

import javax.naming.directory.AttributeInUseException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final ArrayList<Employee> employees = new ArrayList<>();

    public void AddEmployee() throws AttributeInUseException {
        Employee employee = new Employee();
        Scanner scanner = new Scanner(System.in);
        String emailInput;

        System.out.println("Enter the email: ");
        emailInput = scanner.nextLine();
        if (SearchEmployee(emailInput) != null){
            throw new AttributeInUseException("This email belongs to other employee");
        }
        else {
            employee.setEmail(emailInput);

            System.out.println("Enter the name: ");
            employee.setName(scanner.nextLine());

            System.out.println("Enter the surname: ");
            employee.setSurname(scanner.nextLine());

            System.out.println("Enter the position (PREZES, WICEPREZES, MANAGER, PROGRAMISTA, STAZYSTA): ");
            Position position = Position.getPosition(scanner.nextLine());
            employee.setJobTitle(position);

            employee.setSalary(position.getSalary());
        }
        employees.add(employee);
        System.out.println("Employee added successfully!\n");
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void ShowEmployees(){
        System.out.println("Employees: ");
        for (Employee employee : employees) {
            System.out.println("---------------------");
            System.out.println(employee.toString());
            System.out.println("---------------------");
        }
    }

    public Employee SearchEmployee(String email){
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> SearchEmployeeByCompany(String companyName){
        return employees.stream()
                .filter(e -> e.getCompanyName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    public List<Employee> validateSalaryConsistency() {
        List<Employee> inconsistentSalaryEmployees = new ArrayList<>();

        for (Employee employee : employees) {
            double baseSalary = employee.getJobTitle().getSalary();

            if (employee.getSalary() < baseSalary) {
                inconsistentSalaryEmployees.add(employee);
            }
        }

        return inconsistentSalaryEmployees;
    }

    public Map<String, CompanyStatistics> getCompanyStatistics() {
        Map<String, List<Employee>> employeesByCompany = new HashMap<>();
        for (Employee employee : employees) {
            String companyName = employee.getCompanyName();
            if (!employeesByCompany.containsKey(companyName)) {
                employeesByCompany.put(companyName, new ArrayList<>());
            }
            employeesByCompany.get(companyName).add(employee);
        }

        Map<String, CompanyStatistics> statisticsMap = new HashMap<>();
        for (Map.Entry<String, List<Employee>> entry : employeesByCompany.entrySet()) {
            List<Employee> companyEmployees = entry.getValue();

            int employeeCount = companyEmployees.size();
            double totalSalary = 0;
            Employee highestPaidEmployee = null;

            for (Employee employee : companyEmployees) {
                totalSalary += employee.getSalary();

                if (highestPaidEmployee == null || employee.getSalary() > highestPaidEmployee.getSalary()) {
                    highestPaidEmployee = employee;
                }
            }

            double averageSalary = employeeCount > 0 ? totalSalary / employeeCount : 0;
            String highestPaidName = highestPaidEmployee != null ?
                    highestPaidEmployee.getName() + " " + highestPaidEmployee.getSurname() : "N/A";

            CompanyStatistics stats = new CompanyStatistics(employeeCount, averageSalary, highestPaidName);
            statisticsMap.put(entry.getKey(), stats);
        }

        return statisticsMap;
    }
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}

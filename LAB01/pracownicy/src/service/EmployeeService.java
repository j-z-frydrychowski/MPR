package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.Employee;
import model.Position;

import javax.naming.directory.AttributeInUseException;

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

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}

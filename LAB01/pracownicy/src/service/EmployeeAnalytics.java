package service;

import model.Employee;
import model.Position;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeAnalytics {
    private final List<Employee> employees;

    public EmployeeAnalytics(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> SortEmployeesBySurname() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSurname))
                .collect(Collectors.toList());
    }

    public Map<Position, List<Employee>> GroupEmployeesByPosition(){
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getJobTitle));
    }

    public Map<Position, Long> CountEmployeesByPosition(){
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getJobTitle, Collectors.counting()));
    }

    public double CalculateAverageSalary() {
        return employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    public Optional<Employee> GetHighestPaidEmployee() {
        return employees.stream()
                .max(Comparator.comparing(Employee::getSalary));
    }
}

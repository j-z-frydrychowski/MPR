import model.Employee;
import model.Position;
import service.EmployeeAnalytics;
import service.EmployeeService;

import javax.naming.directory.AttributeInUseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws AttributeInUseException {
        EmployeeService service = new EmployeeService();

        try{
            Employee e1 = new Employee("Jan", "Kowalski", "jan.kowalski@techcorp.pl", "TechCorp", Position.PROGRAMISTA);
            Employee e2 = new Employee("Anna", "Nowak", "anna.nowak@techcorp.pl", "TechCorp", Position.MANAGER);
            Employee e3 = new Employee("Piotr", "Zaręba", "piotr.zareba@techcorp.pl", "TechCorp", Position.PREZES);
            Employee e4 = new Employee("Marta", "Wójcik", "marta.wojcik@innycorp.pl", "InnyCorp", Position.PROGRAMISTA);
            Employee e5 = new Employee("Krzysztof", "Kozak", "krzysztof.kozak@techcorp.pl", "TechCorp", Position.PROGRAMISTA);

            service.getEmployees().add(e1);
            service.getEmployees().add(e2);
            service.getEmployees().add(e3);
            service.getEmployees().add(e4);
            service.getEmployees().add(e5);
        } catch (IllegalArgumentException e) {
            System.err.println("Błąd podczas tworzenia pracownika: " + e.getMessage());
        }

        System.out.println("--- Lista wszystkich pracowników ---");
        service.ShowEmployees();

        EmployeeAnalytics analytics = new EmployeeAnalytics(service.getEmployees());

        System.out.println("--- Pracownicy w firmie 'InnyCorp' ---");
        List<Employee> innycorpEmployees = service.SearchEmployeeByCompany("InnyCorp");
        innycorpEmployees.forEach(System.out::println);

        System.out.println("--- Pracownicy posortowani wg nazwiska ---");
        analytics.SortEmployeesBySurname().forEach(e -> System.out.println(e.getName() + " " + e.getSurname()));

        System.out.println("\n--- Grupowanie pracowników wg stanowiska ---");
        Map<Position, List<Employee>> groupedByPosition = analytics.GroupEmployeesByPosition();
        groupedByPosition.forEach((position, list) ->
                System.out.println(position.getName() + ": " + list.size() + " pracowników")
        );

        System.out.println("\n--- Liczba pracowników na stanowiskach ---");
        Map<Position, Long> countsByPosition = analytics.CountEmployeesByPosition();
        countsByPosition.forEach((position, count) ->
                System.out.println(position.getName() + ": " + count)
        );

        System.out.println("\n--- Statystyki finansowe ---");
        System.out.printf("Średnie wynagrodzenie: %.2f PLN\n", analytics.CalculateAverageSalary());

        System.out.println("Pracownik z najwyższym wynagrodzeniem:");
        analytics.GetHighestPaidEmployee().ifPresentOrElse(
                e -> System.out.println(e.getName() + " " + e.getSurname() + " (" + e.getSalary() + " PLN)"),
                () -> System.out.println("Brak pracowników w systemie.")
        );
    }
}
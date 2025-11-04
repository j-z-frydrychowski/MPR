package com.test.model;

public class CompanyStatistics {
    private final int employeeCount;
    private final double averageSalary;
    private final String highestPaidEmployeeName;

    public CompanyStatistics(int employeeCount, double averageSalary, String highestPaidEmployeeName) {
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.highestPaidEmployeeName = highestPaidEmployeeName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public String getHighestPaidEmployeeName() {
        return highestPaidEmployeeName;
    }

    @Override
    public String toString() {
        return "\n  - Liczba pracowników: " + employeeCount +
                "\n  - Średnie wynagrodzenie: " + String.format("%.2f", averageSalary) +
                "\n  - Najlepiej opłacany pracownik: " + highestPaidEmployeeName;
    }
}

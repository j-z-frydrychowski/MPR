package com.test.model;

public class Employee {
    private String name;
    private String surname;
    private String email;
    private String companyName;
    private Position position;
    private double salary;

    public Employee(){
        this.companyName = "TechCorp";
    }

    public Employee(String name, String surname, String email, String companyName, Position position) {
        this.name = validate(name);
        this.surname = validate(surname);
        this.email = validate(email);
        this.companyName = validate(companyName);
        this.position = validate(position);
        this.salary = position.getSalary();
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getCompanyName() {
        return companyName;
    }
    public Position getJobTitle() {
        return position;
    }
    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = validate(name);
    }
    public void setSurname(String surname) {
        this.surname = validate(surname);
    }
    public void setEmail(String email) {
        this.email = validate(email);
    }
    public void setCompanyName(String companyName) {
        this.companyName = validate(companyName);
    }
    public void setJobTitle(Position position) {
        this.position = validate(position);
    }
    public void setSalary(double salary) {
        if(salary <= 0){
            throw new IllegalArgumentException("Salary cannot be negative or zero");
        }
        else this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Employee e = (Employee) o;
        return email.equals(e.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "Imie: " + name +
                "\nSurname: " + surname +
                "\nEmail: " + email +
                "\nCompany: " + companyName +
                "\nJob title: " + position.getName() +
                "\nSalary: " + salary + "\n";
    }

    private static <T> T validate(T o){
        if(o == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        else return o;
    }
}
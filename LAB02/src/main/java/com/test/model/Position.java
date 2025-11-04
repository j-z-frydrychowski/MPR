package com.test.model;

public enum Position {
    PREZES("Prezes",25000),
    WICEPREZES("Wiceprezes",18000),
    MANAGER("Manager",12000),
    PROGRAMISTA("Programista",8000),
    STAZYSTA("Stażysta", 3000);

    public final String name;
    public final double salary;

    private Position(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public double getSalary() {
        return this.salary;
    }

    public String getName() {
        return this.name;
    }

    public static Position getPosition(String name){
        return Position.valueOf(name.toUpperCase());
    }
}

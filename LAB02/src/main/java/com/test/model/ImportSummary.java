package com.test.model;

import java.util.ArrayList;
import java.util.List;

public class ImportSummary {
    private int importedCount = 0;
    private List<String> errors = new ArrayList<>();

    public void incrementImportedCount() {
        this.importedCount++;
    }

    public void addError(int lineNumber, String errorDescription) {
        this.errors.add("Linia " + lineNumber + ": " + errorDescription);
    }

    public int getImportedCount() {
        return importedCount;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Podsumowanie Importu ---\n");
        sb.append("Zaimportowano pracowników: ").append(importedCount).append("\n");
        sb.append("Liczba błędów: ").append(errors.size()).append("\n");
        if (!errors.isEmpty()) {
            sb.append("Lista błędów:\n");
            for (String error : errors) {
                sb.append("  - ").append(error).append("\n");
            }
        }
        return sb.toString();
    }
}
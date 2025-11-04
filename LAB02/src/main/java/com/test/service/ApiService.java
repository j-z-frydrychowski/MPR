package com.test.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.exception.ApiException;
import com.test.model.ApiUser;
import com.test.model.Employee;
import com.test.model.Position;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ApiService {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private final Gson gson = new Gson();
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public List<Employee> fetchEmployeesFromApi() throws ApiException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ApiException("API Error: " + response.statusCode());
            }

            Type userListType = new TypeToken<List<ApiUser>>() {
            }.getType();
            List<ApiUser> apiUsers = gson.fromJson(response.body(), userListType);

            return apiUsers.stream()
                    .map(this::mapApiUserToEmployee)
                    .collect(Collectors.toList());

        } catch (IOException | InterruptedException e) {
            throw new ApiException("API communication error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ApiException("JSON parsing error: " + e.getMessage(), e);
        }
    }

    private Employee mapApiUserToEmployee(ApiUser apiUser) {
        String[] parts = apiUser.name.split("\\s+", 2);
        String firstName = parts.length > 0 ? parts[0] : "BrakImie";
        String lastName = parts.length > 1 ? parts[1] : "BrakNazwisko";

        Position defaultPosition = Position.PROGRAMISTA;

        Employee employee = new Employee(
                firstName,
                lastName,
                apiUser.email,
                apiUser.company.name,
                defaultPosition
        );

        employee.setSalary(defaultPosition.getSalary());

        return employee;
    }
}
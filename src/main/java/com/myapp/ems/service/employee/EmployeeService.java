package com.myapp.ems.service.employee;

import com.myapp.ems.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployee();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    void deleteEmployee(Long id);
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField,String sortDirection);
    boolean checkDuplicateEmail(String email,String orgEmail);
}

package com.myapp.ems.service.employee;

import com.myapp.ems.model.Employee;
import com.myapp.ems.repository.EmployeeRepository;
import com.myapp.ems.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {
    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if(optional.isPresent()){
            employee = optional.get();
        }else{
            throw new RuntimeException("Employee not found for id::"+id);
        }
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if(optional.isPresent()){
            employeeRepository.deleteById(id);
        }else{
            throw new RuntimeException("Employee not found for id::"+id);
        }
    }

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize,String sortField,String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1,pageSize,sort);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public boolean checkDuplicateEmail(String email, String orgEmail) {
        if(email.equals(orgEmail)) return false;
        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);
        return employee.isPresent();
    }


}

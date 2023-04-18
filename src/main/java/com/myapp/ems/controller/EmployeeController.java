package com.myapp.ems.controller;

import com.myapp.ems.model.Employee;
import com.myapp.ems.repository.EmployeeRepository;
import com.myapp.ems.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;
    @GetMapping("/")
    public String viewHomePage(Model model){
        return findPaginated(1,"firstName","asc",model);
    }

    @GetMapping("/saveEmployee")
    public String newEmployeeForm(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "newEmployeeForm";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,Model model){
        if(employeeService.checkDuplicateEmail(employee.getEmail(),null)){
            bindingResult.rejectValue("email", null, "Email already taken");
        }
        if(bindingResult.hasErrors()){
            return "newEmployeeForm";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,Model model){
        Optional<Employee> originalUser = employeeRepository.findById(employee.getId());
        Employee checkOriginalUser = originalUser.get();
        if(employeeService.checkDuplicateEmail(employee.getEmail(),checkOriginalUser.getEmail())){
            bindingResult.rejectValue("email", null, "Email already taken");
        }
        if(bindingResult.hasErrors()){
            return "updateEmployeeForm";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable Long id,Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee",employee);
        return "updateEmployeeForm";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 5;
        Page<Employee> page = employeeService.findPaginated(pageNo,pageSize,sortField,sortDir);
        List<Employee> listEmployee = page.getContent();
        model.addAttribute("listEmployee",listEmployee);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseDir",sortDir.equals("asc") ? "desc" : "asc");

        return "index";
    }
}

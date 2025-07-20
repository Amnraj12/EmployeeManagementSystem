 
package com.company.employeemanagement.service;

import com.company.employeemanagement.model.Employee;
import com.company.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public List<Employee> getPendingEmployees(){
        return employeeRepository.findByApprovedFalse();
    }

    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void approveEmployee(Long id){
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            employee.setApproved(true);
            employeeRepository.save(employee);
        }
    }

    public void rejectEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
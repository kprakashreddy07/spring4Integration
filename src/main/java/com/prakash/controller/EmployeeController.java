package com.prakash.controller;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.prakash.entity.Employee;
import com.prakash.service.EmployeeService;

 /**
 * @author prakashreddyreddy
 *
 */
@RestController
public class EmployeeController {
	
	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	public EmployeeController() {
		System.out.println("EmployeeController()");
	}

    @Autowired
    private EmployeeService employeeService;

  @RequestMapping(value="/createEmployee",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
 
     public ModelAndView createEmployee(@ModelAttribute Employee employee) {
    	logger.info("Creating Employee. Data: "+employee);
        return new ModelAndView("employeeForm");
    }
    
    @RequestMapping(value="/editEmployee",method=RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView editEmployee(@RequestParam long id, @ModelAttribute Employee employee) {
    	logger.info("Updating the Employee for the Id "+id);
        employee = employeeService.getEmployee(id);
        return new ModelAndView("employeeForm", "employeeObject", employee);
    }
    
    @RequestMapping("saveEmployee")
    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
    	logger.info("Saving the Employee. Data : "+employee);
        if(employee.getId() == 0){ // if employee id is 0 then creating the employee other updating the employee
            employeeService.createEmployee(employee);
        } else {
            employeeService.updateEmployee(employee);
        }
        return new ModelAndView("redirect:getAllEmployees");
    }
    
    @RequestMapping(value="/deleteEmployee",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteEmployee(@RequestParam long id) {
    	logger.info("Deleting the Employee. Id : "+id);
        employeeService.deleteEmployee(id);
        return new ModelAndView("redirect:getAllEmployees");
    }
    
   // @RequestMapping(value = {"getAllEmployees", "/"})
    @RequestMapping(value="/",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAllEmployees() {
    	logger.info("Getting the all Employees.");
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ModelAndView("employeeList", "employeeList", employeeList);
    }
    
    @RequestMapping("searchEmployee")
    public ModelAndView searchEmployee(@RequestParam("searchName") String searchName,@RequestParam("searchAddress") String searchAddress) {  
    	logger.info("Searching the Employee. Employee Names: "+searchName+""+searchAddress);
    	List<Employee> employeeList = employeeService.getAllEmployees(searchName,searchAddress);
        return new ModelAndView("employeeList", "employeeList", employeeList);    	
    }
}
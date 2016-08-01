package com.common.rest.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.common.rest.message.ApplicationMessages;
import com.common.rest.model.Customer;
import com.common.rest.model.Employee;
import com.common.rest.result.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(value = "/user/")
public class CommonController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableCustomer")
	public ResponseEntity<Result> addCustomer(@RequestBody Customer customer) throws Exception {

		Result result = new Result();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<Customer> request = new HttpEntity<Customer>(customer);
			logger.info("customer" + customer);
			URI uri = URI.create("http://localhost:8080/customer/v1/");
			result = restTemplate.postForObject(uri, request, Result.class);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/findAll/customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableCustomer")
	public ResponseEntity<Result> findAllCustomers() throws Exception {
		Result result = new Result();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			URI uri = URI.create("http://localhost:8080/customer/v1/customers");
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, Result.class);
			return loginResponse;
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/findOne/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableCustomer")
	public ResponseEntity<Result> findOneCustomer(@PathVariable("customerId") String customerId) throws Exception {
		Result result = new Result();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<String> entity = new HttpEntity<String>(customerId.toString(), headers);
			logger.info("customerId.toString() " + customerId.toString());
			URI uri = URI.create("http://localhost:8080/customer/v1/" + customerId);
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, Result.class);
			return loginResponse;
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/customer", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableCustomer")
	public ResponseEntity<Result> updateCustomer(@RequestBody Customer customer) throws Exception {
		Result result = new Result();

		try {
			HttpEntity<Customer> request = new HttpEntity<Customer>(customer);
			logger.info("customer" + customer);
			URI uri = URI.create("http://localhost:8080/customer/v1/");
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.PUT, request, Result.class);
			return loginResponse;
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableCustomer")
	public ResponseEntity<Result> deleteCustomer(@PathVariable("customerId") String customerId) throws Exception {
		Result result = new Result();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(customerId.toString(), headers);
		try {
			logger.info("customerId.toString() " + customerId.toString());
			URI uri = URI.create("http://localhost:8080/customer/v1/" + customerId);
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Result.class);
			return loginResponse;
		} catch (

		Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableEmployee")
	public ResponseEntity<Result> addEmployee(@RequestBody Employee employee) throws Exception {
		Result result = new Result();
		try {
			HttpEntity<Employee> request = new HttpEntity<Employee>(employee);
			logger.info("employee " + employee);
			URI uri = URI.create("http://localhost:8090/employee/v1/");
			result = restTemplate.postForObject(uri, request, Result.class);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/findAll/employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableEmployee")
	public ResponseEntity<Result> findAllEmployees() throws Exception {
		Result result = new Result();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			URI uri = URI.create("http://localhost:8090/employee/v1/employees");
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, Result.class);
			return loginResponse;

		} catch (

		Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/findOne/employee/{empId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableEmployee")
	public ResponseEntity<Result> findOneEmployee(@PathVariable("empId") String empId) throws Exception {
		Result result = new Result();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<String> entity = new HttpEntity<String>(empId.toString(), headers);
			logger.info("empId.toString() " + empId.toString());
			URI uri = URI.create("http://localhost:8090/employee/v1/" + empId);
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, Result.class);
			return loginResponse;

		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/employee", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableEmployee")
	public ResponseEntity<Result> updateEmployee(@RequestBody Employee employee) throws Exception {
		Result result = new Result();

		try {
			HttpEntity<Employee> request = new HttpEntity<Employee>(employee);
			logger.info("employee " + employee);
			URI uri = URI.create("http://localhost:8090/employee/v1/");
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.PUT, request, Result.class);
			return loginResponse;
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/employee/{empId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "reliableEmployee")
	public ResponseEntity<Result> deleteEmployee(@PathVariable("empId") String empId) throws Exception {
		Result result = new Result();
		try {
			HttpEntity<String> request = new HttpEntity<String>(empId);
			logger.info("empId.toString() " + empId.toString());
			URI uri = URI.create("http://localhost:8090/employee/v1/" + empId);
			ResponseEntity<Result> loginResponse = restTemplate.exchange(uri, HttpMethod.DELETE, request, Result.class);

			return loginResponse;
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// public static void main(String[] args) {
	// SpringApplication.run(CommonController.class, args);
	// }

	public ResponseEntity<Result> reliableCustomer(Customer customer) {
		Result result = new Result();
		result.setMessage(ApplicationMessages.NO_CUSTOMER_SERVICE);
		return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Result> reliableCustomer(String customerId) {
		Result result = new Result();
		result.setMessage(ApplicationMessages.NO_CUSTOMER_SERVICE);
		return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Result> reliableCustomer() {
		Result result = new Result();
		result.setMessage(ApplicationMessages.NO_CUSTOMER_SERVICE);
		return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<Result> reliableEmployee(Employee employee) {
		Result result = new Result();
		result.setMessage(ApplicationMessages.NO_EMPLOYEE_SERVICE);
		return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Result> reliableEmployee(String employeeId) {
		Result result = new Result();
		result.setMessage(ApplicationMessages.NO_EMPLOYEE_SERVICE);
		return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<Result> reliableEmployee() {
		Result result = new Result();
		result.setMessage(ApplicationMessages.NO_EMPLOYEE_SERVICE);
		return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

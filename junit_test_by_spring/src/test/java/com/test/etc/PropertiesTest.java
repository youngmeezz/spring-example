package com.test.etc;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * ref 
 * 	: http://docs.spring.io/spring/docs/4.0.0.RELEASE/spring-framework-reference/htmlsingle/#beans-beans-conventions
 * @author zaccoding
 * @date 2017. 7. 23.
 */
public class PropertiesTest {
	@Test
	public void wrapper() {
		BeanWrapper company = new BeanWrapperImpl(new Company());
		company.setPropertyValue("name","Zaccoding Inc.");
		
		BeanWrapper employee = new BeanWrapperImpl(new Employee());
		employee.setPropertyValue(new PropertyValue("name","zaccoding"));
		employee.setPropertyValue(new PropertyValue("salary","100"));
		company.setPropertyValue("managingDirector",employee.getWrappedInstance());
		
		System.out.println(company.getWrappedInstance());		
	}
}

//////////////////////////////
// Test domain Classes
//////////////////////////////

class Company {
    private String name;
    private Employee managingDirector;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Employee getManagingDirector() {
        return this.managingDirector;
    }
    public void setManagingDirector(Employee managingDirector) {
        this.managingDirector = managingDirector;
    }
	@Override
	public String toString() {
		return "Company [name=" + name + ", managingDirector=" + managingDirector + "]";
	}
}

class Employee {
    private String name;
    private float salary;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }
	@Override
	public String toString() {
		return "Employee [name=" + name + ", salary=" + salary + "]";
	}
}








package com.njkol.atomic.doublecas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicMarkableReference;

import org.junit.jupiter.api.Test;

class Employee {

	private final int id;
	private final String name;

	public Employee(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}

public class TestAtomicMarkedReference {

	@Test
	public void demo() {
		Employee employee = new Employee(123, "Mike");
		AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<>(employee, true);
		assertEquals(employee, employeeNode.getReference());
		assertTrue(employeeNode.isMarked());
	}

	// @Test
	public void testGet() {

		Employee employee = new Employee(123, "Mike");
		AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<>(employee, true);

		boolean[] markHolder = new boolean[1];
		Employee currentEmployee = employeeNode.get(markHolder);

		assertEquals(employee, currentEmployee);
		assertTrue(markHolder[0]);
		System.out.println(markHolder[0]);
	}

	// @Test
	public void testSet() {

		Employee employee = new Employee(123, "Mike");
		AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<>(employee, true);

		Employee newEmployee = new Employee(124, "John");
		employeeNode.set(newEmployee, false);

		assertEquals(newEmployee, employeeNode.getReference());
		assertFalse(employeeNode.isMarked());
		System.out.println(employeeNode.isMarked());
	}

	// @Test
	public void testCAS() {

		Employee employee = new Employee(123, "Mike");
		AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<>(employee, true);
		Employee newEmployee = new Employee(124, "John");

		assertTrue(employeeNode.compareAndSet(employee, newEmployee, true, false));
		assertEquals(newEmployee, employeeNode.getReference());
		assertFalse(employeeNode.isMarked());
		System.out.println(employeeNode.isMarked());
	}

	@Test
	public void testAttemptMark() {
		Employee employee = new Employee(123, "Mike");
		AtomicMarkableReference<Employee> employeeNode = new AtomicMarkableReference<>(employee, true);

		assertTrue(employeeNode.attemptMark(employee, false));
		assertFalse(employeeNode.isMarked());
		System.out.println(employeeNode.isMarked());
	}
}

package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest {
private List<Integer> numbers;
private List<String> strings;
Integer initialNumbers[] = {10, 20, 40};
String initialStrings[] = {"name1", "name2"};

Predicate<Integer> greater100Predicate = new GreaterNumberPredicate(100);
Predicate<Integer> greater25Predicate = new GreaterNumberPredicate(25);
Predicate<Integer> multipleOf10Predicate = new NumberMultipleOfPredicate(10);
Predicate<String> predicateName = new StartWithPredicate("name");
Predicate<String> predicateMain = new StartWithPredicate("main");

	@BeforeEach
	void setUp() throws Exception {
		numbers = getInitialNumbers();
		strings = getInitialStrings();
	}

	private List<String> getInitialStrings() {
		List<String> res = new ArrayList<>();
		for (int i = 0; i < initialStrings.length; i++) {
			res.add(initialStrings[i]);
		}
		return res;
	}

	private List<Integer> getInitialNumbers() {
		
		List<Integer> res = new ArrayList<>(1);
		for (int i = 0; i < initialNumbers.length; i++) {
			res.add(initialNumbers[i]);
		}
		return res;
	}

	@Test
	void testGet() {
		assertEquals(10, numbers.get(0));
		assertEquals("name1", strings.get(0));
		assertNull(numbers.get(-1));
		assertNull(numbers.get(3));
		
	}
	@Test
	void testAddAtIndex() {
		int inserted0 = 100;
		int inserted2 = -8;
		int inserted4 = 1000;
		Integer[] expected = {inserted0, 10, inserted2, 20, 40, inserted4};
		assertTrue(numbers.add(0, inserted0));
		assertTrue( numbers.add(2, inserted2));
		assertTrue( numbers.add(5, inserted4));
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.add(7, 1000));
		assertFalse( numbers.add(-1, 1000));
	}
	@Test
	void testRemove() {
		Integer expected0[] = {20, 40};
		Integer expected1[] = {20};
		assertNull(numbers.remove(3));
		assertNull(numbers.remove(-1));
		assertEquals(10, numbers.remove(0));
		assertArrayEquals(expected0, getArrayFromList(numbers));
		assertEquals(40, numbers.remove(1));
		assertArrayEquals(expected1, getArrayFromList(numbers));
		
	}
	@Test 
	void testSize() {
		assertEquals(initialNumbers.length, numbers.size());
		numbers.add(100);
		assertEquals(initialNumbers.length + 1, numbers.size());
		numbers.remove(0);
		assertEquals(initialNumbers.length, numbers.size());
	}
	
	@Test
	void testContainsNumbers() {
		assertTrue(numbers.contains(initialNumbers[0]));
		assertFalse(numbers.contains(1000));
		numbers.add(1000);
		assertTrue(numbers.contains(1000));
		
		
	}
	@Test
	void testContainsStrings() {
		strings.add("Hello");
		String pattern = new String("Hello");
		assertTrue(strings.contains(pattern));
		assertTrue(strings.contains("Hello"));
	}
	@Test
	void testContainsPersons() {
		Person prs = new Person(123, "Moshe");
		Person prs2 = new Person(124, "Vasya");
		List<Person> persons = new ArrayList<>();
		persons.add(prs);
		persons.add(prs2);
		assertTrue(persons.contains(new Person(124, "Vasya")));
		assertTrue(persons.contains(prs));
		assertFalse(persons.contains(new Person(125, "Olya")));
	}
	@Test
	void containsPredicateNumbersTest() {
		assertFalse(numbers.contains(greater100Predicate));
		assertTrue(numbers.contains(greater25Predicate));
		
	}
	@Test
	void containsPredicateStringsTest() {
		assertFalse(strings.contains(predicateMain));
		assertTrue(strings.contains(predicateName));
			
	}
	
	@Test
	void indexOf() {
		assertEquals(0, numbers.indexOf(10));
		assertEquals(1, numbers.indexOf(20));
		assertEquals(2, numbers.indexOf(40));
		numbers.add(10);
		assertEquals(0, numbers.indexOf(10));
		numbers.add(2, 20);
		assertEquals(1, numbers.indexOf(20));
		assertEquals(-1, numbers.indexOf(25));
	}
	
	@Test
	void lastIndexOf() {
		numbers.add(10);
		assertEquals(3, numbers.lastIndexOf(10));
		numbers.add(0, 20);
		assertEquals(2, numbers.lastIndexOf(20));
		assertEquals(3, numbers.lastIndexOf(40));
		assertEquals(-1, numbers.lastIndexOf(25));
	}
	
	@Test
	void indexOfPredicate() {
		assertEquals(0, numbers.indexOf(multipleOf10Predicate));
		numbers.add(0, 25);
		assertEquals(1, numbers.indexOf(multipleOf10Predicate));
		assertEquals(-1, numbers.indexOf(greater100Predicate));
	}
	
	@Test
	void lastIndexOfPredicate(){
		assertEquals(2, numbers.lastIndexOf(multipleOf10Predicate));
		numbers.add(50);
		assertEquals(3, numbers.lastIndexOf(greater25Predicate));
		assertEquals(-1, numbers.lastIndexOf(greater100Predicate));
	}
	
	@Test
	void removePredicate() {
		assertFalse(numbers.removeIf(greater100Predicate));
		numbers.add(25);
		assertTrue(numbers.removeIf(multipleOf10Predicate));
		assertEquals(1, numbers.size());
		numbers.add(30);
		assertTrue(numbers.removeIf(greater25Predicate));
		assertEquals(25, numbers.get(0));
		
	}
	
	@Test
	void removePredicateString() {
		assertTrue(strings.removeIf(predicateName));
		assertEquals(0, strings.size());
		strings.add("hello");
		assertFalse(strings.removeIf(predicateMain));
		
		
	}

	@SuppressWarnings("unchecked")
	private <T> T[] getArrayFromList(List<T> list) {
		int size = list.size();
		T[] res = (T[]) new Object[size];
		
		for (int i = 0; i < size; i++) {
			res[i] = list.get(i);
		}
		return res;
	}

}

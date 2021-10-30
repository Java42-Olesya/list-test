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
	void testAdd() {
	assertEquals(initialNumbers.length, numbers.size());
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
	
	@Test
	void indexOfTest() {
		assertEquals(0, numbers.indexOf(10));
		assertEquals(2, numbers.indexOf(40));
		assertEquals(-1, numbers.indexOf(100));
	}
	@Test
	void lastIndexOfTest() {
		assertEquals(0, numbers.lastIndexOf(10));
		assertEquals(2, numbers.lastIndexOf(40));
		assertEquals(-1, numbers.lastIndexOf(100));
		numbers.add(10);
		assertEquals(3, numbers.lastIndexOf(10));
		
	}
	@Test
	void indexOfPredicate() {
		assertEquals(2, numbers.indexOf(new GreaterNumberPredicate(25)));
		assertEquals(0, numbers.indexOf(new GreaterNumberPredicate(5)));
		assertEquals(-1, numbers.indexOf(new GreaterNumberPredicate(45)));
	}
	@Test
	void lastIndexOfPredicate() {
		assertEquals(2, numbers.lastIndexOf(new GreaterNumberPredicate(25)));
		assertEquals(2, numbers.lastIndexOf(new GreaterNumberPredicate(5)));
		assertEquals(-1, numbers.lastIndexOf(new GreaterNumberPredicate(45)));
	}

	@Test
	void removeIfTest() {
		Integer expected[] = {10, 20};
		Integer expectedEmpty[] = {};
		Predicate<Integer> greater25 = new GreaterNumberPredicate(25);
		assertTrue(numbers.removeIf(greater25));
		assertFalse(numbers.removeIf(greater25));
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertTrue(numbers.removeIf(new GreaterNumberPredicate(0)));
		assertArrayEquals(expectedEmpty, getArrayFromList(numbers));
			
		
	}
	@Test
	void removeAllTest() {
		numbers.add(20);
		List<Integer> otherNumbers = new ArrayList<>();
		otherNumbers.add(20);
		otherNumbers.add(40);
		assertTrue(numbers.removeAll(otherNumbers));
		Integer expected[] = {10};
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.removeAll(otherNumbers));
	}
	@Test
	void removeAllSame() {
		assertTrue(numbers.removeAll(numbers));
		assertArrayEquals(new Integer[0], getArrayFromList(numbers));
	}
	
	@Test
	void removePatternTest() {
		numbers.add(10);
		Integer expected10[] = {20, 40, 10};
		Integer expected40[] = {20, 10};
		assertNull(numbers.remove(25));
		Integer pattern10 = 10;
		Integer pattern40 = 40;
		assertEquals(10, numbers.remove(pattern10));
		assertArrayEquals(expected10, getArrayFromList(numbers));
		assertEquals(40, numbers.remove(pattern40));
		assertArrayEquals(expected40, getArrayFromList(numbers));
	}
	
	@Test
	void retainAllTest() {
		assertFalse(numbers.retainAll(numbers));
		numbers.add(40);
		numbers.add(15);
		numbers.add(25);
		List<Integer> otherNumbers = new ArrayList<>();
		otherNumbers.add(15);
		otherNumbers.add(25);
		Integer expected[] = {15, 25};
		assertTrue(numbers.retainAll(otherNumbers));
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.retainAll(otherNumbers));
	}
	
	@Test
	void sortNaturalOrderTest() {
		Integer expected[] = {5, 10, 20, 25, 40};
		numbers.add(5);
		numbers.add(25);
		numbers.sort();
		assertArrayEquals(expected, getArrayFromList(numbers));
	}
	
	@Test
	void sortComparatorTest() {
		Integer expected[] = {40, 25, 20, 10, 5};
		numbers.add(5);
		numbers.add(25);
		numbers.sort(new NumberDescComparator());
		assertArrayEquals(expected, getArrayFromList(numbers));
		
	}
	
	@Test
	void sortStringNaturalOrder() {
		strings.add("yes");
		strings.add("javascript");
		String[]expected = {"javascript", "name1", "name2", "yes"};
		strings.sort();
		assertArrayEquals(expected, getArrayFromList(strings));
		
	}
	
	@Test
	void sortStringComparator() {
		strings.add("Yes");
		strings.add("JavaScript");
		String[]expected = {"Yes", "name1", "name2", "JavaScript"};
		strings.sort(new StringComparator());
		assertArrayEquals(expected, getArrayFromList(strings));
		
	}


}


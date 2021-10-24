package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest {
	
private List<Integer> numbers;
private List<String> strings;

Integer initialNumbers[] = {10, 20, 40};
String initialStrings[] = {"name1", "name2"};

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
		assertEquals(null, numbers.get(-1));
		assertEquals(null, numbers.get(3));

	}
	
	@Test
	void testAddByIndex() {
		assertFalse(numbers.add(5, 30));
		assertFalse(numbers.add(-1, 30));
		assertTrue(numbers.add(2, 30));
		assertEquals(4, numbers.size());
		assertEquals(30, numbers.get(2));
//		test allocate
		
	

	}
	
	@Test
	void testRemove() {
		assertEquals(40, numbers.remove(2));
		assertEquals(2, numbers.size());
		assertNull(numbers.remove(10));
	
	}
	@Test
	void testRemove2() {
		numbers.add(100);
		assertEquals(100, numbers.remove(3));
		assertEquals(3, numbers.size());

	}

}


package sg.edu.nus.comp.cs3219.module;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs3219.model.LineStorage;

public class RequiredWordsFilterTest {
	LineStorage inputLineStorage;
	LineStorage afterShiftLineStorage;
	CircularShifter shifter;

	@Before
	public void setUp() {
		inputLineStorage = new LineStorage();
		afterShiftLineStorage = new LineStorage();
		shifter = new CircularShifter(afterShiftLineStorage);
		Set<String> words = new HashSet<>();
		words.add("the");
		words.add("after");
		shifter.setIgnoreWords(words);

		inputLineStorage.addObserver(shifter);
	}

	@Test
	public void withRequiredWordTest() {

		Set<String> required = new HashSet<>();
		required.add("tomorrow");
		required.add("day");

		shifter.setRequiredWords(required);

		inputLineStorage.addLine("The Day after Tomorrow Morning");
		assertEquals(2, afterShiftLineStorage.size());

		assertEquals("Day after Tomorrow Morning the", afterShiftLineStorage.get(0).toString());
		assertEquals("Tomorrow Morning the Day after", afterShiftLineStorage.get(1).toString());
	}

	@Test
	public void withoutRequiredWordTest() {

		Set<String> required = new HashSet<>();
		shifter.setRequiredWords(required);

		inputLineStorage.addLine("The Day after Tomorrow Morning");
		assertEquals(3, afterShiftLineStorage.size());

		assertEquals("Day after Tomorrow Morning the", afterShiftLineStorage.get(0).toString());
		assertEquals("Tomorrow Morning the Day after", afterShiftLineStorage.get(1).toString());
		assertEquals("Morning the Day after Tomorrow", afterShiftLineStorage.get(2).toString());
	}
}

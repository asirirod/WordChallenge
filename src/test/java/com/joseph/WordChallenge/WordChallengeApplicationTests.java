package com.joseph.WordChallenge;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Test to validate functionality in WordChallengeApplication.
 *
 * Test Cases focus on validating largest word selection from
 * a file containing a list of words, as well as the method
 * to transpose the largest word.
 *
 */
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WordChallengeApplicationTests {

	Logger log = LoggerFactory.getLogger(this.getClass());
	WordChallengeApplication wordChallengeApplication;

	@BeforeAll
	public void setup(){
		wordChallengeApplication = new WordChallengeApplication();
	}

	/**
	 * Validate primary use case.
	 */
	@Test
	@Order(1)
	@DisplayName("Primary Use Case")
	public void validatePrimaryUseCase() throws IOException {
		String largest_word = wordChallengeApplication.getLargestWord("test-words.txt");
		log.info("Validating largest word selected: '{}'", largest_word);
		Assertions.assertEquals("abcdef", largest_word, "Failed to validate largest word selection");

		String transposed_word = wordChallengeApplication.transposeLargestWord();
		log.info("Validating transposed largest word: '{}'", transposed_word);
		Assertions.assertEquals("fedcba", transposed_word, "Failed to validate transposed largest word");
	}

	/**
	 * Action: Execute with a data file which contains empty lines
	 * Validation: Empty lines will be ignored and largest word and transposed word will be selected as expected.
	 * @throws IOException
	 */
	@Test
	@Tag("Negative")
	@DisplayName("Data file with empty lines")
	public void validateWithDataFileWithEmptyLines() throws IOException {
		String largest_word = wordChallengeApplication.getLargestWord("test-words-empty-lines.txt");
		log.info("Validating largest word selected: '{}'", largest_word);
		Assertions.assertEquals("abcdef", largest_word, "Failed to validate largest word selection");

		String transposed_word = wordChallengeApplication.transposeLargestWord();
		log.info("Validating transposed largest word: '{}'", transposed_word);
		Assertions.assertEquals("fedcba", transposed_word, "Failed to validate transposed largest word");
	}

	/**
	 * Action: Execute with a data file with words of the same size
	 * Validation: The first word will be selected as the largest word.
	 * @throws IOException
	 */
	@Test
	@Tag("Negative")
	@DisplayName("File with multiple words of the same size")
	public void validateWithDataFileWithSameSizedWords() throws IOException {
		String largest_word = wordChallengeApplication.getLargestWord("test-words-same-size.txt");
		log.info("Validating largest word selected: '{}'", largest_word);
		Assertions.assertEquals("abcdef", largest_word, "Failed to validate largest word selection");

		String transposed_word = wordChallengeApplication.transposeLargestWord();
		log.info("Validating transposed largest word: '{}'", transposed_word);
		Assertions.assertEquals("fedcba", transposed_word, "Failed to validate transposed largest word");
	}

	/**
	 * Action: Execute with empty data file
	 * Validation: Expect empty strings for largest word and transposed word.
	 * @throws IOException
	 */
	@Test
	@Tag("Negative")
	@DisplayName("Empty data file")
	public void validateWithEmptyDataFile() throws IOException {
		Assertions.assertThrows(RuntimeException.class, () -> {
			wordChallengeApplication.getLargestWord("empty.txt");
		});
		log.info("Successfully validated RunTimeException.");
	}

	/**
	 * Action: Execute with a data file which does not exist
	 * Validation: A FileNotFound exception wlil be thrown.
	 */
	@Test
	@Tag("Negative")
	@DisplayName("Non existing file")
	public void validateWithNonExistingFile()  {
		Assertions.assertThrows(FileNotFoundException.class, () -> {
			wordChallengeApplication.getLargestWord("non-existing.txt");
		});
		log.info("Successfully validated FileNotFoundException.");
	}

	/**
	 * Action: Execute with a data file that is not a text file
	 * Validation: file will be read as string and processed.
	 */
	@Test
	@Tag("Negative")
	@DisplayName("Invalid file format")
	public void validateWithInvalidFormatFile() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			wordChallengeApplication.getLargestWord("binary-file.txt");
		});
		log.info("Successfully validated RunTimeException.");
	}
}

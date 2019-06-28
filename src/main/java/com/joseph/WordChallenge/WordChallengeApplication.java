package com.joseph.WordChallenge;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
@SpringBootApplication
@ComponentScan
public class WordChallengeApplication implements ApplicationRunner {

	private String largest_word = null;

	private String readDataFile(String filename) throws IOException {
		try {
			ClassPathResource classPathResource = new ClassPathResource(filename);
			String content =  FileUtils.readFileToString(classPathResource.getFile(), Charset.defaultCharset());
			return content;

		} catch (IOException e){
			log.error("Failed to read file: {}\n{}", filename, e.getMessage());
			throw e;
		}
	}

	public String getLargestWord(String filename) throws IOException {
		// Get words from file as a list
		String file_content = readDataFile(filename);

		if(file_content.length() == 0){
			throw new RuntimeException("No data found in file.");
		}

		if(!file_content.matches("[a-zA-Z0-9\\n\\s]+")){
			throw new RuntimeException("Invalid content in file");
		}

		List<String> words = Arrays.asList(file_content.split("\n"));

		if(words.size() == 0){
			log.info("No words found in the file");
			return null;
		}

		log.info("Getting largest word from list: " + words.toString());
		this.largest_word = Collections.max(words, Comparator.comparing(String::length));
		return this.largest_word;
	}

	public String transposeLargestWord(){
		log.debug("Transposing word: '{}'", this.largest_word);
		return new StringBuilder(this.largest_word).reverse().toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(WordChallengeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws IOException {
		log.debug("Arguments: " + Arrays.toString(args.getSourceArgs()));

		if(!args.containsOption("file")){
			log.info("Required file parameter not specified.");
			System.exit(1);
		}

		WordChallengeApplication app = new WordChallengeApplication();

		// Find largest word
		String largest_word = app.getLargestWord(args.getOptionValues("file").get(0));
		log.info("Largest Word: '{}'", largest_word);

		// Transpose largest word
		String transposed_word = app.transposeLargestWord();
		log.info("Transposed Word: '{}'", transposed_word);
	}
}

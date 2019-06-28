package com.joseph.WordChallenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class WordChallengeApplication {

	private List<String> words;

	public WordChallengeApplication(String filename){

	}

	public String getLargestWord(){
		return null;
	}

	public String transposeWord(String word){
		return null;
	}

	public static void main(String[] args) {
		SpringApplication.run(WordChallengeApplication.class, args);
	}

}

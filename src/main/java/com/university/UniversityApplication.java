package com.university;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.utils.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@SpringBootApplication
@EnableNeo4jRepositories
public class UniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		ObjectMapper mapper = new ObjectMapper();
		InputStream bigDockerLecturesIs = Map.class.getResourceAsStream("/systems-lectures.json");
		InputStream linuxLecturesIs = Map.class.getResourceAsStream("/linux-lectures.json");
		InputStream namesIs = Map.class.getResourceAsStream("/names.json");
		HashMap<String, List<String>> map = null;
		try {
			map = mapper.readValue(bigDockerLecturesIs, HashMap.class);
			Data.BIG_DOCKER_LECTURES.addAll(map.get("values"));
			map = mapper.readValue(linuxLecturesIs, HashMap.class);
			Data.LINUX_LECTURES.addAll(map.get("values"));
			map = mapper.readValue(namesIs, HashMap.class);
			Data.NAMES.addAll(map.get("values"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

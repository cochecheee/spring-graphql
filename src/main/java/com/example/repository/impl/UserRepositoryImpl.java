package com.example.repository.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.model.User;
import com.example.repository.IUserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UserRepositoryImpl implements IUserRepository {
	
	@Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    
	@Override
	public List<User> findAllUsers() {
		String url = "http://localhost:4000/"; // URL của GraphQL server

		// Tạo GraphQL query
		String query = "{ getAllUsers { id username email } }";

		// Định nghĩa request body
		String requestBody = "{ \"query\": \"" + query.replace("\"", "\\\"") + "\" }";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		// Gửi request đến GraphQL server
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		try {
			// Chuyển đổi JSON về danh sách User
			JsonNode root = objectMapper.readTree(response.getBody());
			JsonNode dataNode = root.path("data").path("getAllUsers");

			// Chuyển đổi JsonNode thành danh sách User
			// Sử dụng TypeReference để chuyển đổi trực tiếp thành List<User>
			return objectMapper.readValue(dataNode.toString(), new TypeReference<List<User>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}

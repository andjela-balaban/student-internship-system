package org.unibl.etf.internship.intern_hub.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.*;
import org.springframework.web.client.RestClient;
import org.unibl.etf.internship.intern_hub.model.dto.AiRecommendationResultDTO;
import org.unibl.etf.internship.intern_hub.model.dto.AiRequestDTO;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class AiService {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${groq.api.key}")
	private String apiKey;
	
	private final RestClient restClient = RestClient.builder().baseUrl("https://api.groq.com/openai/v1/chat/completions").build();
	
	private static final String INIT_PROMPT = "You are an expert HR Recruitment AI Assistant specializing in matching IT students with the most suitable internships.\n"
	        + "\n"
	        + "Your task is to analyze the student's profile ('student' field), their CV ('cv' field), and a list of available internships ('internships' field).\n"
	        + "\n"
	        + "MATCHING CRITERIA:\n"
	        + "You must evaluate matches by comparing the student's professional 'interests' and their known 'technologies' (found inside the 'cv' object) against each internship's 'name', 'description', and required 'technologies'. Pay close attention to aligning the student's career desires with the internship goals.\n"
	        + "\n"
	        + "CRITICAL INSTRUCTION FOR CV VALIDATION:\n"
	        + "The 'cv' field can be null. If the 'cv' field is null, IGNORE it completely and perform the matching process using ONLY the available data from the 'student' profile (such as their faculty). Do not fail or complain about a missing CV.\n"
	        + "\n"
	        + "Strict Constraints:\n"
	        + "1. Select a MAXIMUM of 5 best-fitting internships. If fewer than 5 internships are a good match, return only those. If there are no good matches, return an empty array [].\n"
	        + "2. Order the selected internships from the highest match percentage to the lowest. Match percentage is a double value ranging from 0 to 1.\n"
	        + "3. You must respond ONLY with a raw JSON array of objects containing the internship ID, match percentage, and a short reason.\n"
	        + "4. DO NOT include any conversational text, markdown formatting (like ```json), explanations, or notes. Your entire response must be directly parseable as a valid JSON array.\n"
	        + "\n"
	        + "REQUIRED OUTPUT FORMAT TEMPLATE:\n"
	        + "[\n"
	        + "  {\n"
	        + "    \"internshipId\": 1,\n"
	        + "    \"matchPercentage\": 0.95,\n"
	        + "    \"reason\": \"Short explanation in English why this internship name and its technologies match the student's profile.\"\n"
	        + "  }\n"
	        + "]";
	
	private static final String USER_PROMPT = "Please analyze the data and find the best internship matches according to the system rules.\n"
			+ "\n"
			+ "[DATA]\n";
	
	public List<AiRecommendationResultDTO> getInternshipIds(AiRequestDTO request) {
		try {
			String studentDataJson = objectMapper.writeValueAsString(request);			
			String fullUserPrompt = USER_PROMPT + studentDataJson;
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("model", "groq/compound-mini");
			requestBody.put("max_tokens", 800);
			requestBody.put("temperature", 0.2);
			
			List<Map<String, String>> messages = new ArrayList<>();
			messages.add(Map.of("role", "system", "content", INIT_PROMPT));
			messages.add(Map.of("role", "user", "content", fullUserPrompt));
			requestBody.put("messages", messages);
			
			GroqResponse response = restClient.post()
					.header("Authorization", "Bearer " + apiKey)
					.contentType(MediaType.APPLICATION_JSON)
					.body(requestBody)
					.retrieve()
					.body(GroqResponse.class);
			
			if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
				String rawContent = response.getChoices().get(0).getMessage().getContent().trim();
				
				// Safely extract the JSON array substring from [ to ]
				int start = rawContent.indexOf("[");
				int end = rawContent.lastIndexOf("]");
				if (start != -1 && end != -1 && end > start) {
					String jsonArrayStr = rawContent.substring(start, end + 1);
					return objectMapper.readValue(jsonArrayStr, new TypeReference<List<AiRecommendationResultDTO>>() {});
				}
			}
		} catch(Exception e) {
			System.err.println("Groq AI Call Error: " + e.getMessage());
		}
		
		return Collections.emptyList();
	}
	
	public static class GroqResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() { return choices; }
        public void setChoices(List<Choice> choices) { this.choices = choices; }
    }

    public static class Choice {
        private Message message;

        public Message getMessage() { return message; }
        public void setMessage(Message message) { this.message = message; }
    }

    public static class Message {
        private String content;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}

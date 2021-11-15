package com.example.faces;





import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class FacesApplicationTests {
	
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @Autowired
	 ObjectMapper objectmapper;
	 
    @Test
    public void testGetFaces() throws Exception {    	
        
        String response = mockMvc.perform(get("http://localhost:8080/faces?url=https://res.cloudinary.com/demo/image/upload/woman1.jpg,https://res.cloudinary.com/demo/image/upload/face_left.jpg", 1))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.totalFaces", is(2)))
                .andReturn().getResponse().getContentAsString();
 
        System.out.println("response: " + response);
    }

	@Test
	void contextLoads() throws Exception {
		testGetFaces();
		
	}

}

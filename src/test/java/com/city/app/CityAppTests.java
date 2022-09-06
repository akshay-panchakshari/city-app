package com.city.app;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.city.app.controller.CityController;
import com.city.app.model.City;
import com.city.app.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CityController.class)
@WithMockUser
@ContextConfiguration 
class CityAppTests {
	
	
	@Autowired
	private  MockMvc mockMvc;
	
	@MockBean
	CityService service ;
	
	@Autowired
    ObjectMapper mapper;
	
	@Test
	public void getCities() throws Exception {
		List<City> cities = new ArrayList<>();
		cities.add(new City(1L, "Mumbai", "mumbai.png"));
		cities.add(new City(2L, "Tokyo", "tokyo.png"));
		cities.add(new City(3L, "Jakarta", "jakarta.png"));

		Page<City> pagedResponse = new PageImpl<City>(cities);
		Mockito.when(service.getCities(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(pagedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cities").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(3))).andExpect(jsonPath("$.content[0].name", is("Mumbai")))
				.andExpect(jsonPath("$.content[1].name", is("Tokyo")))
				.andExpect(jsonPath("$.content[2].name", is("Jakarta")));
	}
	
	@Test
	public void updateCity_Forbidden() throws Exception {
		City updatedCity=new City(1001L,"abc","abc.png");
		
		Mockito.when(service.updateCity(updatedCity, 1001L)).thenReturn(updatedCity);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/api/cities/1001L", updatedCity)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsBytes(updatedCity));
		
		mockMvc.perform(builder).andExpect(status().isForbidden());
	}
	
}

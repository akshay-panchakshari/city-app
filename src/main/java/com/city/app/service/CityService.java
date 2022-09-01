package com.city.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.city.app.model.City;
import com.city.app.repo.CityRepo;

@Service
public class CityService {
	
	@Autowired
	CityRepo repo;
	
	List<City>  intialData ;
	
	private static final String COMMA = ",";
	 private final static Logger logger = LoggerFactory.getLogger(CityService.class);
	 
	@PostConstruct
	public void loadInitialData() {
	    try{
	      File inputF =new ClassPathResource("cities.csv").getFile();
	      InputStream inputFS = new FileInputStream(inputF);
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
	      // skip the header of the csv
	      intialData = br.lines().skip(1).map(line-> mapToItem.apply(line)).collect(Collectors.toList());
	      br.close();
	      loadData(intialData);
	      logger.info("Intial data load successful!");
	    } catch (IOException e) {
	    	logger.error("Exception occured while loading initial data into database",e);
	    }
	}
	
	private void loadData(List<City>  intialData) {
		if(intialData!=null && intialData.size() > 0) {
			repo.saveAll(intialData);
		}
	}
	
	private Function<String, City> mapToItem = (line) -> {
		String[] p = line.split(COMMA);// a CSV has comma separated lines
		City item = new City();
		item.setName(p[1]);
		item.setPhoto(p[2]);
		return item;
	};
}
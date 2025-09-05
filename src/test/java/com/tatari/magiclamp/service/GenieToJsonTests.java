package com.tatari.magiclamp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(SpringRunner.class)
public class GenieToJsonTests {

	@Autowired protected GenieToJson genieToJson;
	@Test
	public void shouldWriteToJson() {


		genieToJson.readGenieDataFile("/Users/pgg1v/Documents/GENIE_v17.0/data_mutations_extended.txt", "/Users/pgg1v/Documents/mutation.json", "mutations_extended", "/Users/pgg1v/Documents/GENIE_v17_Cancer_Types_JB.csv");
		//genieToJson.readGenieDataFile("/Users/pgg1v/Documents/GENIE_v17.0/data_clinical_sample.txt", "/Users/pgg1v/Documents/clinical.json", "clinical_sample", "/Users/pgg1v/Documents/GENIE_v17_Cancer_Types_JB.csv");
	}
}
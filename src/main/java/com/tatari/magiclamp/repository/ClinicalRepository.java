package com.tatari.magiclamp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.tatari.magiclamp.domain.Clinical;


public interface ClinicalRepository extends MongoRepository<Clinical, String> {

	public Clinical findBySample(String sampleId);

}

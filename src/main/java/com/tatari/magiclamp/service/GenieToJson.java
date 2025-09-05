package com.tatari.magiclamp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.tatari.magiclamp.domain.Clinical;
import com.tatari.magiclamp.repository.ClinicalRepository;


@Service
public class GenieToJson {

	private Map<String, String> sampleCancerTypeMap = new LinkedHashMap<String, String>();
	
	private ClinicalRepository clinicalRepository;
	
	@Autowired
	public GenieToJson(ClinicalRepository clinicalRepository) {
		super();
		this.clinicalRepository = clinicalRepository;
	}

	public void readGenieDataFile(String inputFilename, String outputFilename, String fileType, String sampleCancerTypeFilename) {

		if(sampleCancerTypeFilename != null) {
			populateSampleCancerTypeMap(sampleCancerTypeFilename);
		}
        File file = new File(outputFilename);	
        String[] filenameSplit = outputFilename.split("\\.");
		FileReader fileReader = null;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			fileReader = new FileReader(inputFilename);
			readAndWrite(fileReader, writer, fileType, filenameSplit[0]);
		} catch (IOException e) {

			e.printStackTrace();
		} 
		finally {

		    if (writer != null) {
		        try {
		        	writer.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
	private void readAndWrite(FileReader fileReader, BufferedWriter writer, String fileType, String filename) {

		String line = null;
		BufferedReader reader = new BufferedReader(fileReader);
		String sample = "";
		String superType = null;
		try {
			
			if(fileType.equals("clinical_sample")) {
				System.out.println("Parsing clinical sample file. Please wait until the FINISHED indicator is presented....");
				while ((line = reader.readLine()) != null) {

					if(line.startsWith("GENIE")) {
						
						String lineElements[] = line.split("\\t");
						sample = lineElements[1];
						if(sampleCancerTypeMap.containsKey(sample)) {
							superType = sampleCancerTypeMap.get(sample);
						} else {
							System.out.println("Can't find sample " + sample);
						}						
						JSONObject jo = new JSONObject();
						jo.put("patient", lineElements[0]);
						jo.put("sample", sample);
						jo.put("age", lineElements[2]);
						jo.put("oncotreeCode", lineElements[3]);
						jo.put("sampleType", lineElements[4]);
						jo.put("assayId", lineElements[5]);
						jo.put("cancerType", lineElements[6]);
						jo.put("cancerTypeDetailed", lineElements[7]);
						jo.put("cancerTypeHighLevel", superType);
						writer.write(jo.toString());
					}
				}
			}
			Clinical clinical = null;
			if(fileType.equals("mutations_extended")) {
					
				while ((line = reader.readLine()) != null) {
					
					if(!line.startsWith("Hugo_Symbol")) {
						String lineElements[] = line.split("\\t");

						String gene = lineElements[0];
						String sampleid = lineElements[16];
						String protpos = lineElements[42];
						String exon = "";
						if(lineElements[44].contains("/")) {
							String exonSplit [] = lineElements[44].split("/");
							exon = exonSplit[0];
						}
						String hgvsc = "";
						if(lineElements[37].contains(":")) {
							// e.g. raw is ENST00000256078.4:c.34G>T and we want to remove the transcript
							// String may have more than 1 : so best to just remove the bit up to the first colon and the first colon
							String hgvscSplit [] = lineElements[37].split(":");
							hgvsc = lineElements[37].replace(hgvscSplit[0] + ":", "");
							//hgvsc = hgvscSplit[1];
						}
						clinical = this.clinicalRepository.findBySample(sampleid);

							JSONObject jo = new JSONObject();
							jo.put("genieVersion", filename);
							jo.put("gene", gene);
							jo.put("center", lineElements[2]);
							jo.put("build", lineElements[3]);
							jo.put("chromosome", lineElements[4]);
							jo.put("start", lineElements[5]);
							jo.put("end", lineElements[6]);
							jo.put("strand", lineElements[7]);
							jo.put("consequence", lineElements[8]);
							jo.put("variantClassification", lineElements[9]);
							jo.put("variantType", lineElements[10]);
							jo.put("ref", lineElements[11]);
							jo.put("alt1", lineElements[12]);
							jo.put("alt2", lineElements[13]);
							jo.put("rsid", lineElements[14]);
							jo.put("tumourRefCount", lineElements[33]);
							jo.put("tumourAltCount", lineElements[34]);
							jo.put("tumourTotalCount", lineElements[61]);
							jo.put("exon", exon);
							jo.put("sample", sampleid);
							jo.put("patient", clinical.getPatient());
							jo.put("hgvsc", hgvsc);
							jo.put("hgvsp", lineElements[39]);
							jo.put("ensembleTranscriptId", lineElements[40]);
							jo.put("refseq", lineElements[41]);
							jo.put("protpos", getProtposAsInteger(protpos));
							if(lineElements[9].equals("Nonsense_Mutation")) {
								jo.put("hgvspCodon", getCodonFromNonsenseMutation(lineElements[39],getProtposAsInteger(protpos)));
							} else if(lineElements[9].contains("Frame_Shift")) {
								jo.put("hgvspCodon", getCodonFromFrameshiftMutation(lineElements[39],getProtposAsInteger(protpos)));
							} else {
								jo.put("hgvspCodon", "");
							}
							jo.put("polyphenPrediction", lineElements[55]);
							jo.put("siftPrediction", lineElements[57]);						
							jo.put("age", clinical.getAge());
							jo.put("assayId", clinical.getAssayId());
							jo.put("oncotreeCode", clinical.getOncotreeCode());
							jo.put("cancerType", clinical.getCancerType());
							jo.put("cancerTypeDetailed", clinical.getCancerTypeDetailed());
							jo.put("cancerTypeHighLevel", clinical.getCancerTypeHighLevel());
							jo.put("sampleType", clinical.getSampleType());
							writer.write(jo.toString());
					}
				}
			}
			System.out.println("FINISHED!!!!");

		} catch (IOException e) {

			e.printStackTrace();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Integer getCodonFromNonsenseMutation(String hgvsp, Integer protpos) {
		
		Integer n = protpos;
		if(hgvsp.contains("_")) {
			String [] hgvspSplit = hgvsp.split("_");
			String numbersLower = hgvspSplit[0].replaceAll("[^0-9]", "");
			try {
				n = Integer.parseInt(numbersLower);
				if(n != protpos) {
					//System.out.println("Nonsense HGVSP with different codon in hgvsp " + hgvsp + " to protpos: " + protpos);
				}
			} catch (NumberFormatException nfe) {
				
				System.out.println("Nonsense HGVSP without numbers: " + hgvsp);
			}			
			return n;
		} //else if(hgvsp.contains("*")) {
			String [] hgvspSplit = hgvsp.split("\\*");
			String numbers = hgvspSplit[0].replaceAll("[^0-9]", "");
			try {
				n = Integer.parseInt(numbers);
				if(n != protpos) {
					//System.out.println("Nonsense HGVSP with different codon "+ n + " in hgvsp " + hgvsp + " to protpos: " + protpos);
				}
				if(!hgvsp.contains(String.valueOf(n))) {
					
					System.out.println("Nonsense HGVSP without codon: " + n);
				}
			} catch (NumberFormatException nfe) {
				
				System.out.println("Nonsense HGVSP without numbers: " + hgvsp);
			}	

			return n;
		//} 
		//System.out.println("**** HGVSP: " + hgvsp);
		//return n;

	}
	
	private Integer getCodonFromFrameshiftMutation(String hgvsp, Integer protpos) {
		
		Integer n = protpos;
		if(hgvsp.contains("fs")) {
			String [] hgvspSplit = hgvsp.split("fs");
			String numbersLower = hgvspSplit[0].replaceAll("[^0-9]", "");
			try {
				n = Integer.parseInt(numbersLower);
				if(n != protpos) {
					//System.out.println("Frameshift HGVSP with different codon "+ n + " in hgvsp " + hgvsp + " to protpos: " + protpos);
				}
			} catch (NumberFormatException nfe) {
				
				System.out.println("Frameshift HGVSP without numbers: " + hgvsp);
			}			
			return n;
		} else {
			String numbers = hgvsp.replaceAll("[^0-9]", "");
			try {
				n = Integer.parseInt(numbers);
				if(n != protpos) {
					//System.out.println("Frameshift HGVSP with different codon "+ n + " in hgvsp " + hgvsp + " to protpos: " + protpos);
				}
			} catch (NumberFormatException nfe) {
				
				System.out.println("Frameshift HGVSP without numbers: " + hgvsp);
			}			
			return n;
		}
	}
	
	private Integer getProtposAsInteger(String protpos) {
	    if (protpos == null || protpos.isEmpty()) {
	        return null;
	    }
	    try {
	        Integer.valueOf(protpos);
	    } catch (NumberFormatException nfe) {
	        return null;
	    }
	    return Integer.valueOf(protpos);
	}
	
	private void populateSampleCancerTypeMap(String filename) {

		File lookupsFile = new File(filename);
		String[] csvRecord = null;
		CSVReader reader;
		try {
			reader = new CSVReaderBuilder(new FileReader(lookupsFile)).withSkipLines(1).build();
			while ((csvRecord = reader.readNext()) != null) {
				sampleCancerTypeMap.put(csvRecord[0], csvRecord[1]);
			}
		} catch (CsvValidationException | IOException e) {

			e.printStackTrace();
		}
	}
}

This code is designed to convert GENIE data into JSON format to be uploaded into MongoDB, and requires MongoDB to build the mutation file. 
There are two main steps to the process.

1. Creation and upload of clinical data from data_clinical_sample.txt and optionally from a self-built two column csv file of sample id and cancer super type (with values 'Haematological' or 'Solid Tumour').
   
The first step is to convert the information within data_clinical_sample.txt to JSON format preserving for each row that starts with ‘GENIE’ the following fields, rendering the values exactly as in the original file:

•	patient - line element 0
•	sample - line element 1
•	age - line element 2
•	oncotreeCode - line element 3
•	sampleType - line element 4
•	assayId - line element 5
•	cancerType - line element 6
•	cancerTypeDetailed  - line element 7

Line element refers to the order within data_clinical_sample.txt where line element 0 is the first column. This order was used in both v17 and v18 of GENIE. 
If later versions of GENIE have a different order then the file will have to be rebuilt to align to this order.

If a second file (4th command line argument) is provided, for each row it  will add a further field based on a high-level cancer type file

•	sample - line element 0
•	cancerTypeHighLevel – line element 1

If a second file is not provided, for each row it will add 'NA' for the high-level cancer type.

Running step 1:

java -jar [path]/genie2json.jar clinical_sample [path to output]/clinical_v[version_number].json [path to input]/data_clinical_sample.txt [path to optional supertype file]/GENIE_v18_Cancer_Types.csv

java -jar [path]/genie2json.jar clinical_sample [path to output]/clinical_v[version_number].json [path to input]/data_clinical_sample.txt


**** Upoad these clinical data into a clinical collection in a genie database in MongoDB. They are utilised to supplement the mutation information generated in the second step. ****

Use MongoDB Compass or command line execution:

mongoimport mongodb://127.0.0.1/genie --collection clinical [path]/clinical_v[version_number].json


2. Creation and upload of mutation data

The information within data_mutations_extended.txt is converted to JSON format preserving for each row that does NOT start with ‘Hugo_Symbol’ (i.e.., all but the first row) the following fields, rendering the values exactly as in the original file in all cases apart from where specified:

gene - line element 0
center - line element 2
build - line element 3
chromosome - line element 4
start - line element 5
end - line element 6
strand - line element 7
consequence - line element 8
variantClassification - line element 9
variantType - line element 10
ref - line element 11
alt1 - line element 12
alt2 - line element 13
rsid - line element 14
tumourRefCount - line element 33
tumourAltCount - line element 34
tumourTotalCount - line element 61
exon - line element 44. Trim off the total number of by splitting on / and taking the first element.
Sample line element 16
hgvsc - line element 37. Trim off transcript by splitting on colon and taking the second element.
hgvsp - line element 39
ensemblTranscriptId - line element 40
refseq - line element 41
protpos - line element 42. Convert to Integer data type.
polyphenPrediction - line element 55
siftPrediction - line element 57

Line element refers to the order within data_mutations_extended.txt where line element 0 is the first column. This order was used in both v17 and v18 of GENIE. 
If later versions of GENIE have a different order then either the file will have to be rebuilt to align to this order, or the code will need to be modified to either read the header info.

The system must also create the following fields with values for fields below taken by querying the clinical database derived in step 1.

patient
age
assayId
oncotreeCode
cancerType
cancerTypeDetailed
cancerTypeHighLevel
sampleType

Two derived field must be added:

•	hgvspCodon – for nonsense and frameshift mutations extract the protpos from the hgvsp string, and for all others just populate with an empty string.

•	genieVersion – the mutation data json filename e.g. genie_v18-0

Running step 2:

java -jar [path]/genie2json.jar mutations_extended genie_v[version_number].json [path to input]/data_mutations_extended.txt

N.B. It is recommended to run the code from the directory that you want the output JSON file (second command line argument) to be located AND to call the file genie_v[version_number].json. 
This will ensure a meaningful genie version without a path is added to the genieVersion field.

**** Upoad these mutation data into a mutation collection in a genie database in MongoDB. ****

Use MongoDB Compass or command line execution:

mongoimport mongodb://127.0.0.1/genie --collection mutation [path]/genie_v[version_number].json


To validate the process it is recommeded to check that the number of entries in the clinical collection matches the number of data rows (i.e not counting header and comment rows) 
and the number of entries in the mutation collection matches the number of data rows (typical the total line count minus one header row) and that for a number of random mutations
that the documents match the orgiginal GENIE data.






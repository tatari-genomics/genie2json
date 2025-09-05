This code is designed to convert GENIE data into JSON format to be uploaded into MongoDB, and requires MongoDB to build the mutation file.
There are two main steps to the process.

1. Creation of clinical data from data_clinical_sample.txt and optionally from a self-built two column csv file of sample id and cancer super type (with values 'Haematological' or 'Solid Tumour').
   
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

N.B. These clinical data are loaded into MongoDB and utilised to supplement the mutation information generated in the second step.


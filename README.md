## Magneto needs mutants
Magneto wants to recruit as many mutants as possible, so he can fight the X-Men.
For this reason I have developed a Rest API that detects if a
human is mutant based on its DNA sequence
---
Next project was developed with [Micronaut framework](https://micronaut.io) using `aws-lambda` features.

## Test application
Check this project out, cd into the directory and run:

    ./gradlew test

This will test application.
## Build application
Check this project out, cd into the directory and run:

    ./gradlew shadowJar

Next cd into build/libs. In there, you find the jar `magneto-need-mutants-0.1-all.jar`.

---
## Check it out yourself
For test REST API you need consume the API with next json
```json
{
  "dna":[
    "ATGCTGCTAC",
    "TGTTCTGTTT",
    "GAAGCCCTAT",
    "CGGGTTGCCG",
    "ATGCTGGAAC",
    "GGTTAAGGCG",
    "TTGGAACTCG",
    "CCGCGGCCTC",
    "GGTGTCTGCG",
    "GTTTTCTTGC"
  ]
}
```
Send a POST request to this endpoint https://da1ipalpf7.execute-api.us-east-2.amazonaws.com/prod/mutant

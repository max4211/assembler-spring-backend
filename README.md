# ECE350 Assembler
[Checkpoint Link](https://docs.google.com/document/d/1lWidKfZjJPaiOQSDBa6b3rpWHXM1RloXSUBQB6QC6M8/edit)

## Project Goals
The goal of this application is to streamline the development cycle of ECE350 students in their final project by automating the assembly process. At a high level, the application exposes a REST API where a student can send in a .s file along with target file type (e.g. Logism) and base (e.g. Hex) and the API returns an assembled file according to the ISA.

Design goals of the project were to write code that is closed to modification but open to extension. I leveraged a variety of object oriented design patterns/principles - including SOLID, factories, design to contract via interfaces, one way data flow, etc. - to achieve this. Additionally, I hoped to abstract away any dependency on a particular ISA (ideallly a RISC architecture) and leave the assembler open to future modifications at runtime by swapping out a data file (unless Professor Board decides to curse the class with x86, if so good luck to you future 350 students).

## Test Driven Development
There is an accompanying test suite which achieved 90% coverage of the core assembler functionality. Additional tests should be written to verify the behavior of Spring Boot compnents.

## Adding New Features
I hoped to make it as easy as possible to extend the code base to new file types, ISAs, and target base. Below is a guide to future developers that want to extend the functionality.

### ISA and Registers
The original design goal of the project was to be completely data driven, particularly with regards to a new ISA. Ideally, the ISA can be pulled from a secure location and is interpreted during each assemble API request. In this way, the ISA can be modified at runtime, and so long as new instructions can be molded to fit one of the existing formats in MIPS it can trivially be added to the architecture.

Below is a sample from the current [ISA configuration file](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/resources/config/ece350ISA.xml). I selected XML for ease of readability. The `Name` field corresponds to what the assembler expects in raw text form on the input file. The `Type` field facilitates reflection in the assembler in order to generate `Instruction` objects which then execute into raw binary translation. The `Code` field are the binary values that the instruction translates to upon instruciton execute. 

```htmlembedded=
<?xml version="1.0" encoding="UTF-8" ?>
<ISA>
    <Instruction>
        <Name>add</Name>
        <Type>R</Type>
        <Code>00000</Code>
    </Instruction>
    <Instruction>
        <Name>addi</Name>
        <Type>I</Type>
        <Code>00101</Code>
    </Instruction>
</ISA>
```

Below is a sample from the `register.properties` file which drives register translatoin logic. These properties are used to translate the raw MIPS input into appropriate register numbers during the filtering process.

```css
$zero=$0
$at=$1
$v0=$2
$v1=$3
$max=$4
```

### Target File Types
To add a new file type, all that one has to do is is extend the `OutputFile` class and implement the constructor. Likely changes between files are the header formatting, line output, and the file extension. These subclasses are generated in an `OutputFile` factory and polymorphic calls to execute facilitate translation. As an example, below is how support was added for `.mem` files:
```java=8
public class MemFile extends OutputFile {

    public MemFile(String path, String outputBase, String digits, Output data) {
        super(path, data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
    }
    
}
```

### Input Validation
Input MIPS files are now [validated](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/java/com/ece350/assembler/model/filter/Validator.java) before the assembly process occurs. Instructions are confirmed to exist, the correct operands must be present, all labels must also exist, as do registers. Assembly will only continue if all of these checks are complete. If one or more instructions are invalid, the Validator will return errors to the calling function and the file will not be assembled. A detailed error output will be returned to the user with file numbers and descriptive error messages. Note that some edge case instructions may slip through to be assembled. In the future, carrying through the original instruction (along with the filtered instruction) will be useful for complete error handling. 

### Custom Instructions
The assembler also supports the addition of custom instructions. In order to use these, they must be of type R, I, JI, or JII and uploaded in parallel with the associated MIPS file in XML format. The schema of said file will be validated and coarse error messages may be thrown if the XML is incorrectly formatted. In the future, improved schema validation will help improve the overall function of the assembler.

## Logging and Metrics
[SL4FJ](http://www.slf4j.org/manual.html) was used to perform logging. From these logs, a [log parsing script](https://github.com/max4211/assembler-spring-backend/blob/master/src/logs/count.py) exists to count usgae.

### General Instruction Path
The general flow of instruction assembly is as follows (see MainAssembler for pipeline):
1. Instructions are accepted by the [Spring Boot Controller](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/java/com/ece350/assembler/spring/Controller.java) and then passed through to the [Spring Boot Service](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/java/com/ece350/assembler/spring/Controller.java), along with desired file type (e.g. MIF) and base (e.g. binary).
2. Any custom instructions are validated and appended to the default ISA.
3. Whitespace, tabs, and comments are [Filtered](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/java/com/ece350/assembler/model/filter/Filter.java) 
4. The MIPS file is [Validated](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/java/com/ece350/assembler/model/filter/Validator.java)
5. Assuming no validation errors, the instructions are then [Replaced](https://github.com/max4211/assembler-spring-backend/blob/master/src/main/java/com/ece350/assembler/model/filter/Replacer.java) into an easy to assemble format. For example, all register values are replaced with integers, commas are removed, and labels are replaced with the appropriate numeric value indicating the offset from the jumping instruction.
6. Instructions are assembled one at a time into 32 digit binary and collected into a single list.
7. The instructions are optionally converted into a different base and then packaged into the indicated file type
8. Assembled file is returned to the frontend.

## Deployment
All components of the application are deployed on AWS. I used AWS Elastic Beanstalk to help streamline the deployment of the .jar file compiled via Maven of the Spring Boot source code. The front end also lives on AWS and CICD is accomplished via AWS Amplify.

#### Elastic Beanstalk
Elastic Beanstalk abstracts away server provisioning and allows you to easily upload and deploy your application. In order to deploy in this way, build the maven project with `mvn clean package`. Then, copy the resulting `*.tar` file into the ELB interface. Once the file has been uploaded, modify the ELB environment properties (Environment --> Configuration --> Software) and set `PORT: 8080` (this is the default port SpringBoot serves requests on, however ELB by default routes them to port 5000). Next, update the health check in the appropriate Target Group to ping the endpoint `/actuator/health`. 

Finally, as is you will get a CORS error when sending requests to this server. To update the ELB instance to serve https requests on the same domain, follow these instructions from [AWS](https://aws.amazon.com/premiumsupport/knowledge-center/elastic-beanstalk-https-configuration/). Another [YouTube](https://www.youtube.com/watch?v=BeOKTpFsuvk&ab_channel=WornOffKeys) channel had an excellent tutorial on how to configure this. Good luck! (devops is hard)

## Future Work
1. Pull in configuration files from Spring properties instead of GitHub Http request.
2. Add support for translating multiple files at once.
3. Improve robustness of validation.
4. Improve ELB deployment through application.yaml and CLI
5. More robust schema validation and error messages on custom instruction templates. 
6. Fix assembly of different base instructions (overflow occurs during some hex translations when using built in Java Integer/String manipulation, a custom converter is recommended).

## Author
Max Smith

max.smith@duke.edu
smithmax4211@gmail.com

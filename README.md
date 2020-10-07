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

## Deployment
All components of the application are deployed on AWS. I used AWS Elastic Beanstalk to help streamline the deployment of the .jar file compiled via Maven of the Spring Boot source code. The front end also lives on AWS and CICD is accomplished via AWS Amplify.

## Future Work
1. Move the configuration files to a GitHub repository to pull in at runtime.
2. Test SpringBoot components to validate expected behavior
3. Add support for more file types as inputs (beyond .s)
4. Add support for translating multiple files at once
5. Add validation  on translation request to verify immediate overflow and input arg count per instruction
6. Instruction translation through stacks/tree DFS to improve accuracy
8. Improve filtering robustness

## Author
Max Smith

max.smith@duke.edu

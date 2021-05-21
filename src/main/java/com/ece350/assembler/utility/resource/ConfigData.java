package com.ece350.assembler.utility.resource;

import com.amazonaws.services.sagemaker.model.GitConfig;
import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.spring.Service;
import com.ece350.assembler.utility.tuple.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConfigData {

    static final Logger LOGGER = LoggerFactory.getLogger(ConfigData.class);

    // TODO: Import from properties file
    static Map<String, String> getRegisterMap() {
        Map<String, String> registerMap = new HashMap<>();

        registerMap.put("$zero", "$0");
        registerMap.put("$at", "$1");
        registerMap.put("$v0", "$2");
        registerMap.put("$v1", "$3");

        registerMap.put("$a0", "$4");
        registerMap.put("$a1", "$5");
        registerMap.put("$a2", "$6");
        registerMap.put("$a3", "$7");

        registerMap.put("$t0", "$8");
        registerMap.put("$t1", "$9");
        registerMap.put("$t2", "$10");
        registerMap.put("$t3", "$11");
        registerMap.put("$t4", "$12");
        registerMap.put("$t5", "$13");
        registerMap.put("$t6", "$14");
        registerMap.put("$t7", "$15");

        registerMap.put("$s0", "$16");
        registerMap.put("$s1", "$17");
        registerMap.put("$s2", "$18");
        registerMap.put("$s3", "$19");
        registerMap.put("$s4", "$20");
        registerMap.put("$s5", "$21");
        registerMap.put("$s6", "$22");
        registerMap.put("$s7", "$23");

        registerMap.put("$t8", "$24");
        registerMap.put("$t9", "$25");

        registerMap.put("$k0", "$26");
        registerMap.put("$k1", "$27");

        registerMap.put("$gp", "$28");
        registerMap.put("$sp", "$29");
        registerMap.put("$fp", "$30");
        registerMap.put("$ra", "$31");

        registerMap.put("$r0", "$0");
        registerMap.put("$r1", "$1");
        registerMap.put("$r2", "$2");
        registerMap.put("$r3", "$3");

        registerMap.put("$r4", "$4");
        registerMap.put("$r5", "$5");
        registerMap.put("$r6", "$6");
        registerMap.put("$r7", "$7");

        registerMap.put("$r8", "$8");
        registerMap.put("$r9", "$9");
        registerMap.put("$r10", "$10");
        registerMap.put("$r11", "$11");
        registerMap.put("$r12", "$12");
        registerMap.put("$r13", "$13");
        registerMap.put("$r14", "$14");
        registerMap.put("$r15", "$15");

        registerMap.put("$r16", "$16");
        registerMap.put("$r17", "$17");
        registerMap.put("$r18", "$18");
        registerMap.put("$r19", "$19");
        registerMap.put("$r20", "$20");
        registerMap.put("$r21", "$21");
        registerMap.put("$r22", "$22");
        registerMap.put("$r23", "$23");

        registerMap.put("$r24", "$24");
        registerMap.put("$r25", "$25");

        registerMap.put("$r26", "$26");
        registerMap.put("$r27", "$27");

        registerMap.put("$r28", "$28");
        registerMap.put("$r29", "$29");
        registerMap.put("$r30", "$30");
        registerMap.put("$r31", "$31");

        registerMap.put("$0", "$0");
        registerMap.put("$1", "$1");
        registerMap.put("$2", "$2");
        registerMap.put("$3", "$3");

        registerMap.put("$4", "$4");
        registerMap.put("$5", "$5");
        registerMap.put("$6", "$6");
        registerMap.put("$7", "$7");

        registerMap.put("$8", "$8");
        registerMap.put("$9", "$9");
        registerMap.put("$10", "$10");
        registerMap.put("$11", "$11");
        registerMap.put("$12", "$12");
        registerMap.put("$13", "$13");
        registerMap.put("$14", "$14");
        registerMap.put("$15", "$15");

        registerMap.put("$16", "$16");
        registerMap.put("$17", "$17");
        registerMap.put("$18", "$18");
        registerMap.put("$19", "$19");
        registerMap.put("$20", "$20");
        registerMap.put("$21", "$21");
        registerMap.put("$22", "$22");
        registerMap.put("$23", "$23");

        registerMap.put("$24", "$24");
        registerMap.put("$25", "$25");
        registerMap.put("$26", "$26");
        registerMap.put("$27", "$27");

        registerMap.put("$28", "$28");
        registerMap.put("$29", "$29");
        registerMap.put("$30", "$30");
        registerMap.put("$31", "$31");

        return registerMap;
    }

    static ISA getISAData() {
        try {
            ISA myISA = GitConfigLoader.getISA();
            LOGGER.info("Successfully fetched ISA from GitHub :)");
            return myISA;
        } catch (Throwable e) {
            LOGGER.debug("Throwable in GitHub raw config pull, defaulting to code");
            List<Triplet> myList = new ArrayList<>();
            myList.add(new Triplet("add","R","00000"));
            myList.add(new Triplet("addi","I","00101"));
            myList.add(new Triplet("sub","R","00001"));
            myList.add(new Triplet("and","R","00010"));
            myList.add(new Triplet("or","R","00011"));
            myList.add(new Triplet("sll","R","00100"));
            myList.add(new Triplet("sra","R","00101"));
            myList.add(new Triplet("mul","R","00110"));
            myList.add(new Triplet("div","R","00111"));
            myList.add(new Triplet("sw","I","00111"));
            myList.add(new Triplet("lw","I","01000"));
            myList.add(new Triplet("j","JI","00001"));
            myList.add(new Triplet("bne","I","00010"));
            myList.add(new Triplet("jal","JI","00011"));
            myList.add(new Triplet("jr","JII","00100"));
            myList.add(new Triplet("blt","I","00110"));
            myList.add(new Triplet("bex","JI","10110"));
            myList.add(new Triplet("setx","JI","10101"));
            myList.add(new Triplet("nop","NOP","00000"));
            myList.add(new Triplet("custom_r","R","01000"));
            return new ISA(myList);
        }
    }

}

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

        registerMap.put("$board", "1");
        registerMap.put("$max", "2");
        registerMap.put("$nathan", "3");

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

package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

public interface ReplaceInterface {

    /**
     * Called on Replacer to replace values in preparation for assembly
     * Process occurs after initial error checking
     * @return instructions replaced with purely numeric values
     */
    Input replace();

}

package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

public interface FilterInterface {

    /**
     * Called on filter to filter the user input, write a new one back
     * @return filtered user input
     */
    Input filter();
}

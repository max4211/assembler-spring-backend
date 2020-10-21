## MIPS Practice Code
# Testing basic ALU operations with write to registers

# Test add immediates (store reg value in register for 1-4)

    blt     $4, $2, final

loop:
    addi	$1, $0, 1		
    addi	$2, $0, 2	
    addi	$3, $0, 3	
    addi	$4, $0, 4	
    bne		$0, $1, loop	    
    
else:   
    addi	$5, $0, 5	
    add	    $6, $3, $3	
    add	    $7, $31, $1	
    add	    $8, $5, $3
    blt		$0, $1, else	
    
final:
    j		0				# jump to 0
    jr		$0			# jump to $4
    jal		$1				# jump to $4 and save position to $ra

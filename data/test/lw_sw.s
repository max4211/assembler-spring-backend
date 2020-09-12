## MIPS Practice Code
# Testing basic ALU operations with write to registers

# Test add immediates (store reg value in register for 1-4)
addi	$1, $0, 1			# $0 = $1 + 4	
addi	$2, $0, 2	
addi	$3, $0, 3	
addi	$4, $0, 4	
addi	$5, $0, 5
addi	$6, $0, 6
addi	$7, $0, 7
addi	$8, $0, 8	

# Test load word
lw		$3, 0($0)		# assign $3 = MEM[$0 + 0]
sw		$2, 0($0)		# assign MEM[$0 + 0] = $2


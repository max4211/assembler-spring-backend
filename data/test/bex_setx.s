addi	$1, $0, 1
addi	$2, $0, 2	
addi	$3, $0, 3	
addi	$4, $0, 4	
addi	$5, $0, 5
addi	$6, $0, 4	
addi	$7, $0, 4	
addi	$8, $0, 4	
addi	$9, $0, 4
bne		$1, $2, 2
blt		$1, $2, 0
add	    $6, $6, $2
j		0

bex     -42         # Hex catch, stays binary
setx    -87         # Hex catch, stays binary
jal		-12         # Full translate to hex
j       44         # Full translate to hex

add		$t0, $t1, $t2		# $t0 = $t1 + $t2
sub		$t2, $t3, $t4		# $t0 = $t1 - $t2
sll     $t4, $t2, $t5
sra     $t5, $t4, $t3


add     $0 $0 $0 	# simple jal test case
add     $0 $0 $0 
add     $0 $0 $0 
add     $0 $0 $0
add     $0 $0 $0
add     $0 $0 $0
addi    $1 $0 4     # $r1 = 4
addi    $2 $0 5     # $r2 = 5
sub     $3 $0 $1   # $r3 = -4
sub     $4 $0 $2   # $r4 = -5
add     $0 $0 $0
add     $0 $0 $0
add     $0 $0 $0 	
addi	$31 $0 100	# $r31 = 100
jal 	j2		# jump to j2, $r31 = PC + 1 = 15
addi 	$20 $20 1	# r20 += 1 (Incorrect)
addi 	$20 $20 1	# r20 += 1 (Incorrect)
addi 	$20 $20 1	# r20 += 1 (Incorrect)
j2:
addi	$10 $10 1	# r10 += 1 (Correct)
add     $0 $0 $0
add	$10 $10 $31 # r10 = r10 + 15
add     $0 $0 $0
add     $0 $0 $0
add     $0 $0 $0

# Final: $r10 should be 16, $r20 should be 0

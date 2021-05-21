loop:
add $r1, $r2    # should throw invalid arguments
su $r4, $r5, $r6    # should throw invalid instruction
j label             # should throw missing label
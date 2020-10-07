bex     $v0         # Hex catch, stays binary
setx    $v1         # Hex catch, stays binary
jal		$at         # Full translate to hex
j       $v1         # Full translate to hex
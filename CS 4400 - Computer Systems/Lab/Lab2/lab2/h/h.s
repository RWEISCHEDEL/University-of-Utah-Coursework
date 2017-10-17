	.file	"h.c"
	.text
	.p2align 4,,15
	.globl	h
	.type	h, @function
h:
.LFB0:
	.cfi_startproc
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	movl	%edi, %ebx
	.p2align 4,,10
	.p2align 3
.L2:
	movl	%ebx, %edi
	call	print_number
	testl	%ebx, %ebx
	jle	.L1
.L8:
	testb	$1, %bl
	jne	.L4
	sarl	%ebx
	movl	%ebx, %edi
	call	print_number
	testl	%ebx, %ebx
	jg	.L8
.L1:
	popq	%rbx
	.cfi_remember_state
	.cfi_def_cfa_offset 8
	.p2align 4,,1
	ret
	.p2align 4,,10
	.p2align 3
.L4:
	.cfi_restore_state
	leal	1(%rbx,%rbx,2), %ebx
	jmp	.L2
	.cfi_endproc
.LFE0:
	.size	h, .-h
	.ident	"GCC: (GNU) 4.8.5 20150623 (Red Hat 4.8.5-4)"
	.section	.note.GNU-stack,"",@progbits

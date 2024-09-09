bits 32

global start

extern printf
import printf msvcrt.dll
extern scanf
import scanf msvcrt.dll
extern exit
import exit msvcrt.dll

segment data use32 class=data
        format db "%d", 0
        endl db `\n`, 0
        d dd 0
        p dd 0
        b dd 0
        a dd 0

segment code use32 class=code
        start:
                push dword a
                push dword format
                call [scanf]
                add esp, 4 * 2

                mov eax, 1
                mov edx, [a]
                add eax, edx
                mov [b], eax

                mov eax, [b]
                mov ebx, [b]
                mul ebx
                mov [p], eax

                push dword [p]
                push dword format
                call [printf]
                add esp, 4 * 2

                push dword endl
                call [printf]
                add esp, 4

                mov eax, [p]
                mov ebx, [b]
                idiv ebx
                mov [d], eax

                push dword [d]
                push dword format
                call [printf]
                add esp, 4 * 2

                push dword endl
                call [printf]
                add esp, 4

                mov eax, 18
                mov edx, [d]
                sub eax, ebx
                mov [d], eax

                push dword [d]
                push dword format
                call [printf]
                add esp, 4 * 2

                push dword endl
                call [printf]
                add esp, 4
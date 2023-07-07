;grupo2.asm
;Funciones usadas por el Grupo 2


;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

;Funciones externas
extrn atoi:proc, itoa:proc, atof:proc, ftoa:proc


;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

GetInteger              macro    number
;gets an integer and stores it in a double word sized variable
                lea    bx, number
                call    atoi

endm

;++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

getString                    macro  string        ;read string from keyboard
local  label1, label2, label3, label4, label5, label6, label7, label8

                        pushad
                        push    di
                        push    si


                        lea    si, string
                        mov    bx, si

label1:                mov    ah, 1
                        int    21h
                        cmp    al, 0Dh
                        je      label2

                        cmp    al, 8
                        je      label8
                        jmp    label7

label8:                dec    si
                        cmp    si, bx
                        jl      label6
                      jmp    label1

label6:                mov    si, bx
                        jmp    label1


label7:                mov    [si], al
                        inc    si
                        jmp    label1
label2:                mov    byte ptr [si], '$'

                        pop    si
                        pop    di
                        popad

endm

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

DisplayString                  macro  string          ;write string on screen

                        push    dx
                        push    ax

                        lea    dx, string
                        mov    ah, 9
                        int    21h

                        pop    ax
                        pop    dx

endm

;+++++++++++++++++++++++++++++++++++++++++++++++++++d++++++++++++++++++++++

DisplayInteger            macro  number ;displays an double word sized variable on the screen

                lea    bx, number
                call    itoa

endm

;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
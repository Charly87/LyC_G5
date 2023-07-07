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
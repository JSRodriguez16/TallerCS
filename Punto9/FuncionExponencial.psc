Proceso FuncionExponencial
    Definir x Como Real
    Definir n, i Como Entero
    Definir suma, termino, factorial Como Real
    
    Escribir "Ingrese el valor de x:"
    Leer x
    
    Escribir "Ingrese la cantidad de terminos a usar:"
    Leer n
    
    suma <- 1
    termino <- 1
    factorial <- 1
    
    Para i <- 1 Hasta n Con Paso 1 Hacer
        factorial <- factorial * i
        termino <- termino * x
        suma <- suma + (termino / factorial)
    FinPara
    
    Escribir "La aproximacion de e^", x, " es: ", suma
FinProceso
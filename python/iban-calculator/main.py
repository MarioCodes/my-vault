#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Ejercicio 2 Python

CALCULAR EL IBAN DE UNA CUENTA BANCARIA:

http://www.tecnoxplora.com/ciencia/divulgacion/iban-asi-calculan-numeros-cuenta-bancaria_2014020957fca03d0cf2fd8cc6b0e1a2.html para consultar el algoritmo de c�lculo

    Se solicita la introducci�n del CCC por teclado (el formato correcto es una sucesi�n de 20 n�meros enteros NNNNNNNNNNNNNNNNNNNN)
    Si el formato del CCC es correcto, se calculan los dos d�gitos del IBAN (las letras suponemos que son ES, de CCC en Espa�a) y se escribe por pantalla "El c�digo IBAN para la CCC introducida es� ESXXNNNNNNNNNNNNNNNNNNNN
    Si el formato del CCC es incorrecto se escribe por pantalla "Formato de CCC incorrecto� y se vuelve a solicitar la introducci�n del CCC.
"""    

def getCCC(output = 'Introduce el CCC: '):
    numero = raw_input(output)
    
    if(len(str(numero)) != 20):
        return 0
    
    try:
        long(numero)
    except:
        return 0
        
    return numero

def calculoIBAN(codigo, ccc): 
    valor1 = 14
    valor2 = 28 
    suma = valor1 + valor2
    
    resto = suma % 97
    calculo = str(98 - resto)
    if(len(calculo) == 1):
        calculo[-1] = '0'
        
    return calculo

codigo = 'ES'
cuenta = getCCC()

while(cuenta == 0):
    cuenta = getCCC('Formato de CCC incorrecto. Introducelo de nuevo: ')


control = calculoIBAN(codigo, cuenta)
final = codigo +str(control) +str(cuenta)
print 'El codigo IBAN para el CCC introducido es: ' +final


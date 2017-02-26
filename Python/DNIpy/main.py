#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Ejercicio 1 Python

CALCULAR LA LETRA QUE CORRESPONDE A UN DNI:

http://www.interior.gob.es/web/servicios-al-ciudadano/dni/calculo-del-digito-de-control-del-nif-nie para consultar el algoritmo de c�lculo
    Se solicita la introducci�n del DNI por teclado (el formato correcto es una sucesi�n de 8 n�meros enteros NNNNNNNN)
    Si el formato del DNI es correcto se calcula la letra que corresponde y se escribe por pantalla "La letra que corresponde al DNI introducido es� X y el NIF completo es NNNNNNNNX
    Si el formato del DNI es incorrecto se escribe por pantalla "Formato de DNI incorrecto� y se vuelve a solicitar la introducci�n del DNI.
"""
def letrasPosibles(numero):
    arrayLetras = 'TRWAGMYFPDXBNJZSQVHLCKE'
    letra = arrayLetras[numero%23]
    return letra

def inputNumber():
    numero = input('Introduce el numero: ')
    
    while(len(str(numero)) != 8):
        numero = input('Formato de DNI incorrecto. Introduce un DNI valido.')
        
    print 'La letra que corresponde al DNI introducido es ' +letrasPosibles(numero) +' y el NIF completo es ' +str(numero) +letrasPosibles(numero)
    
inputNumber()
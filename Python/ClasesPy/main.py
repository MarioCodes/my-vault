#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
CREACIÓN DE UNA CLASE Y PRUEBA DE SUS MÉTODOS:

Cread una clase llamada Empleado que modele la información que una empresa mantiene sobre cada empleado de la misma: nombre, apellidos, NIF, sueldo base, pago por hora extra, horas extra realizadas en el mes, tipo (porcentaje) de IRPF, casado o no y número de hijos (*).

Al crear un objeto de la clase Empleado se podrá proporcionar, si se desea, el número de NIF.

Los objetos de la clase Empleado deben disponer de las siguientes operaciones:

    un método que devuelva el complemento correspondiente a las horas extra realizadas por un empleado
    un método que devuelva el sueldo bruto que gana un empleado
    un método que devuelva la retención (IRPF) a partir del tipo, teniendo en cuenta que el porcentaje de retención que hay que aplicar es el tipo menos 2 puntos si el empleado está casado y menos 1 punto por cada hijo que tenga; el porcentaje se aplica sobre todo el sueldo bruto (sueldo base + sueldo complemento por horas extra)
    un método que visualice por pantalla la siguiente información asociada un empleado: nombre, apellidos, NIF, sueldo base, sueldo complemento por horas extra, sueldo bruto, retención de IRPF y sueldo neto
    un método que clone un objeto empleado


Una vez creada la clase Empleado y todas sus operaciones:

    Se solicita por pantalla la introducción de la información de un empleado (*)
    Si el formato de la información introducida es correcto se escribe por pantalla:

"El complemento correspondiente a las horas extra para el empleado introducido es" ...
"El sueldo bruto que gana el empleado introducido es" ...
"La rentención IRPF aplicada al empleado introducido es" ...
"La información asociada al empleado introducido es" ...

    Si el formato de la información introducida es incorrecto se escribe por pantalla "Formato incorrecto de información de empleado" y se vuelve a solicitar la introducción de la información.
"""
import copy

class Empleado:
    def __init__(self, atributo, bool_nif):
        self.nombre = atributo['nombre']
        self.apellidos = atributo['apellidos']
        if(bool_nif):
            self.nif = atributo['nif']
        self.sueldo_base = atributo['sueldo_base']
        self.horas_extra_precio = atributo['horas_extra_precio']
        self.horas_extra_mes = atributo['horas_extra_mes']
        self.porcentaje_irpf = atributo['porcentaje_irpf']
        self.casado = atributo['casado']
        self.numero_hijos = atributo['numero_hijos']
           
    def get_pago_horas_extra(self):
        return self.horas_extra_mes*self.horas_extra_precio
    
    def get_sueldo_bruto(self):
        return self.sueldo_base+self.get_pago_horas_extra()
    
    def get_retencion_irpf(self):
        return self.porcentaje_irpf()*self.get_sueldo_bruto()
    
    def get_sueldo_neto(self):
        return self.get_sueldo_bruto()-self.get_retencion_irpf()
    
    def output_info_empleado(self):
        print('Empleado: ' +self.apellidos +", " +self.nombre)
        try:
            print('NIF: ' +self.nif)
        except AttributeError:
            print(self.nombre +' no ha introducido su DNI.')
        print('Sueldo Base: ' +str(self.sueldo_base))
        print('El complemento correspondiente a las horas extra para el empleado introducido es: ' +str(self.get_pago_horas_extra()))
        print('El sueldo bruto que gana el empleado es: ' +str(self.get_sueldo_bruto()))
        print('La retencion del IRPF aplicada al empleado es: ' +str(self.get_retencion_irpf()))
        print('Sueldo Neto: ' +str(self.get_sueldo_neto()))
           
    def clonar(self, new_empleado):
        new_empleado.copy(self)
           
def get_casado(output):
    answer = raw_input(output)
    
    if(answer in ['True', 'true', 'si', 'Si', 'Casado']):
        return True
    elif(answer in ['False', 'false', 'no', 'No', 'Soltero']):
        return False
    else:
        print('Input no valido')
        return get_casado(output) 
            
def get_boolean(output):
    bool = raw_input(output)
            
    if(bool in ['True', 'true', 'si', 'Si']):
        return True
    elif(bool in ['False', 'false', 'no', 'No']):
        return False
    else:
        print('Input no valido')
        return get_boolean(output)
            
def get_long(output):
    number = raw_input(output)
    
    try:
        return(long(number))
    except ValueError:
        get_long(output)
          
def get_float(output):
    number = raw_input(output)

    try:
        return(float(number))
    except ValueError:
        get_float(output)
            
def get_NIF(output = 'Introduce el NIF: '):
    correcto = True
    nif = raw_input(output)    
    
    if(len(nif) != 9):
        correcto = False
    
    if not (str(nif[:8]).isdigit()):
        correcto = False
        
    if not (str(nif[8:]).isalpha()):
        correcto = False
    
    if not (correcto):
        get_NIF('NIF no valido. Introduce uno valido: ')
        
    return nif
    
def empleado_nuevo():
    atributo = {}
    atributo['nombre'] = raw_input('Introduce el nombre: ')
    atributo['apellidos'] = raw_input('Introduce los apellidos: ')
    
    input_nif = get_boolean('¿Quieres introducir el NIF?: ')
    if(input_nif):
        atributo['nif'] = get_NIF()
        
    atributo['sueldo_base'] = get_float('Introduce el sueldo base: ')
    atributo['horas_extra_precio'] = get_float('Introduce el precio de la hora extra: ')
    atributo['horas_extra_mes'] = get_long('Introduce el numero de horas extra al mes: ')
    atributo['porcentaje_irpf'] = get_float('Introduce el porcentaje de IRPF: ')
    atributo['casado'] = get_casado('¿Empleado casado?: ')
    atributo['numero_hijos'] = get_float('Numero de Hijos: ')
    
    return Empleado(atributo, input_nif)

empleado = empleado_nuevo()
empleado.output_info_empleado()
"""
print "Hola Mundo" #sout
raw_input() #wait a enter
print "Enter"
""" #Comentario multi-linea

#Tipos de datos:

"""
entero = 23 #int
entero = 23L #long
print type(entero)

string = 'hola \nmundo' #String
"""
array = [10, True, 'hola'] #Array

#Sentencias Condicionales:
bool = False #Booleano #IF
if bool: 
	print 'si'
else:
	print 'nope'
	
while bool: #Bucle
	print 'si'
	
def funcion():
	print 'Funcion llamada'
	
def funcion2(var1 = 'valor ', var2 = 'por defecto'): #se les puede asignar valores por defecto.
	print var1 +var2
	
funcion2()
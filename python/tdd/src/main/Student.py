'''
Created on 3 ene. 2018

@author: mcodes
'''

class Student(object):
    
    def __init__(self, name, age, major):
        self.name = name
        self.age = age
        self.major = major

    def presentation(self):
        print ("Hi, I'm " + self.name + " and I'm " + str(self.age)) + " yo. "

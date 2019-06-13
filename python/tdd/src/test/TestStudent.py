'''
Created on 3 ene. 2018

@author: mcodes
'''
import unittest
from src.main.Student import Student


class TestStudent(unittest.TestCase):

    def test_dto(self):
        student = Student("mario", 24, "su")
        self.assertEqual(24, student.age)
        self.assertEqual("mario", student.name)
        
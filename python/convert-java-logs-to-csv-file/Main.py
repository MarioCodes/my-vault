#!/usr/bin/env python
"""
Created on May 08, 2019
@author: msanchez
"""

import sys

from logs.Logs import Logs

if __name__ == '__main__':
    print("Python version:", sys.version)
    print("Started Program")
    logs = Logs()
    logs.convert_into_csv("input.txt", "output.csv")

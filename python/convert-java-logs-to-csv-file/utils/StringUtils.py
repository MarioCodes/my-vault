"""
Created on May 08, 2019
@author: msanchez
"""


class StringUtils(object):

    def __init__(self):
        """
        Constructor
        """

    @staticmethod
    def remove_start_inclusive(string, chars):
        split_string_len = len(chars)
        return string[string.find(chars) + split_string_len:]

    @staticmethod
    def remove_end_inclusive(string, chars):
        return string[:string.find(chars)]

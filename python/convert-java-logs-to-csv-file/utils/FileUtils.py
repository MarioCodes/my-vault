"""
Created on May 08, 2019
@author: msanchez
"""


class FileUtils(object):

    def __init__(self):
        """
        Constructor
        """

    @staticmethod
    def read_file_lines(file_name, path="resources/"):
        with open(path + file_name) as file:
            raw_content = file.readlines()
            return [raw_line.rstrip('\n') for raw_line in raw_content]

    @staticmethod
    def write_file(file_name, content, path="resources/"):
        f = open(path + file_name, "w")
        f.write(content)
        f.close()

    @staticmethod
    def construct_csv(headers, clean_values):
        csv_content = headers
        for values_group in clean_values:
            csv_line = ""
            for value_line in values_group:
                csv_line += value_line + ";"
            csv_line += "\n"
            csv_content += csv_line

        csv_content += "\n"
        return csv_content

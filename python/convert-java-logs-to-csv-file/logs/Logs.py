"""
Created on May 08, 2019
@author: msanchez
"""

from logs.SVWLogs import SVWLogs
from utils.FileUtils import FileUtils


class Logs(object):
    """ Sets the main structure to follow, to modify it in a future with other Log structures.

        Attributes:
            svw_logs    specific, encapsulated solution to clean the logs I needed this time.
    """

    def __init__(self):
        """
        Constructor
        """
        self.svw_logs = SVWLogs()

    def __get_grouped_lines(self, input_file):
        raw_lines = FileUtils.read_file_lines(input_file)
        return self.svw_logs.group_raw_lines(raw_lines)

    def __clean_log_lines(self, grouped_raw_lines):
        sorted_raw_lines = self.svw_logs.sort_logs(grouped_raw_lines)
        return self.svw_logs.clean_lines(sorted_raw_lines)

    def convert_into_csv(self, input_file, output_file):
        """ Reads a Java log file, groups together the data from the logs, cleans it and gives it back as a .csv file.
        This data was obtained from the logs with Linux AWK command and exported into a .txt file so we only have the
        data we want without external noise.

        :param input_file: .txt file with all raw Java logs.
        :param output_file: .csv file with only the data we need to recover.
        """
        grouped_raw_lines = self.__get_grouped_lines(input_file)
        has_valid_size = self.svw_logs.validate_lists_size(grouped_raw_lines)
        if has_valid_size:
            cleaned_lines = self.__clean_log_lines(grouped_raw_lines)
            csv_content = FileUtils.construct_csv(self.svw_logs.headers, cleaned_lines)
            FileUtils.write_file(output_file, csv_content)

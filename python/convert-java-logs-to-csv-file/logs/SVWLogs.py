"""
Created on May 08, 2019
@author: msanchez
"""

from utils.StringUtils import StringUtils


class SVWLogs(object):
    """
    Specific solution for the needs we had at the moment.
    """

    def __init__(self):
        """ Constructor

            Attributes:
                headers     list of headers to set to the .csv file.
        """
        self.headers = "CREATE_DATE;FUER;VON;WERT;ABLAUFDATUM;XXXX;KARTENNUMMER;PERSOENLICHE_NACHRICHT;\n"

    def group_raw_lines(self, raw_lines):
        """ Takes as parameter the raw lines outputted by Java loggers. Groups them by date, minute and Java-Thread

        :param raw_lines: raw lines read from File
        :return: Grouped lines
        :rtype: BiDimensional Array
        """
        print("Separating lines into lists by Thread-ID, time and date")
        all_grouped_lines = []
        single_group_of_lines = []
        previous_date, previous_time, previous_thread = "", "", ""

        for line in raw_lines:
            date = line[:10]
            time = line[10:16]
            thread = line[25:47]

            if previous_thread and (thread != previous_thread or date != previous_date or time != previous_time):
                all_grouped_lines.append(single_group_of_lines)
                single_group_of_lines = []

            single_group_of_lines.append(line)

            previous_date = date
            previous_time = time
            previous_thread = thread

        return all_grouped_lines

    def validate_lists_size(self, all_grouped_lines, size=7):
        """ Checks that for the BiDimensional array given as parameter, all the sublist at the second dimension
                have the correct Size. This size has to be the same for all of them.

        :param all_grouped_lines: BiDimensional Array to check.
        :param size: Size we want every sub-array to have
        :return: true if valid
        """
        print("Validating list")
        for group in all_grouped_lines:
            if len(group) != size:
                print("ATTENTION! There's a group of data which wasn't correctly separated. "
                      "Expected size: " + str(size) + " Found size: " + str(len(group)))
                return False

        return True

    def sort_logs(self, grouped_lines):
        """ Sorts the logs, as Java logger sometimes writes a log before or after. This is needed before converting to CSV.

        :param grouped_lines: all lines grouped together.
        :return: 2 Dimensions Array with all grouped & sorted logs
        :rtype: BiDimensional array.
        """
        sorted_list = []
        sorted_group = ["", "", "", "", "", "", ""]
        for group in grouped_lines:
            for entry in group:
                if "FUER" in entry:
                    sorted_group[0] = entry
                elif "VON" in entry:
                    sorted_group[1] = entry
                elif "WERT" in entry:
                    sorted_group[2] = entry
                elif "ABLAUFDATUM" in entry:
                    sorted_group[3] = entry
                elif "XXXX" in entry:
                    sorted_group[4] = entry
                elif "KARTENNUMMER" in entry:
                    sorted_group[5] = entry
                elif "PERSOENLICHE_NACHRICHT" in entry:
                    sorted_group[6] = entry

            sorted_list.append(sorted_group)
            sorted_group = ["", "", "", "", "", "", ""]

        return sorted_list

    def __obtain_create_date(self, first_entry):
        return first_entry[:23]

    def __clean_group(self, input_group):
        cleaned_group = []
        create_date = self.__obtain_create_date(input_group[0])
        cleaned_group.append(create_date)

        for entry in input_group:
            entry_minus_start = StringUtils.remove_start_inclusive(entry, "fill ")
            cleaned_entry = StringUtils.remove_end_inclusive(entry_minus_start, " into")
            cleaned_group.append(cleaned_entry)

        return cleaned_group

    def clean_lines(self, raw_lines_lists):
        """ Removes all the noise from the strings which we don't need at all.

        :param raw_lines_lists: BiDimensional array with the complete Strings we receive from the loggers.
        :return: list which contains only the data we need from the logs.
        :rtype: BiDimensional Array
        """
        cleaned_full_list = []
        for group in raw_lines_lists:
            cleaned_group = self.__clean_group(group)
            cleaned_full_list.append(cleaned_group)
        return cleaned_full_list

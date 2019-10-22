'''
Created on Apr 17, 2018

@author: msanchez
'''

class FileReader(object):
    ''' File reader utility class. Reads the lines one by one and strips the blank line char from them.
    
        Attributes:
            file_name relative path + name of the file to read data from. (i.e.) config/file.txt 
    '''

    def __init__(self, file_name):
        """
        :param file_name: complete file name with path where the file is located to be able to open it.
        """
        self.file_name = file_name
        
    def read(self):
        """ Opens the file, reads all the contents, strips blank lines and returns the result.
        
        :return: Iterable which contains everything as is in the file with exception of blank lines.
        :rtype: Iterable
        """
        with open(self.file_name, "r") as file:
            content = file.readlines()
            return self.__strip_blank_lines(content)

    def __strip_blank_lines(self, content):
        return [x.strip('\n') for x in content]
            
    def append_keyword(self, line):
        """ Appends a new line into the file, only if it did not exist previously.
        
        :param line: content we want to append.
        :return: operation status. True if appended, false if it did exist.
        :rtype: boolean
        """
        is_new_keyword = not self.__line_exists(line)
        if(is_new_keyword):
            self.__write_new_line(line)
        
        return is_new_keyword
    
    def __line_exists(self, content_to_write):
        file_content = self.read()
        for line in file_content:
            if(line == content_to_write):
                return True
            
        return False
    
    def __write_new_line(self, line):
        with open(self.file_name, "a") as file:
                file.write(line + "\n")
                
    def delete_keyword(self, keyword):
        """ Deletes the given keyword from the file. Does so reading all the file and writting all again 
                but the given keyword. This way I don't need to read the file twice.
                
        :param keyword: str we want to remove from keywords.
        :return: Status of the operation.
        :rtype: Boolean
        """
        found = False
        with open(self.file_name, "r+") as file_content:
            lines = file_content.readlines()
            clean_lines = self.__strip_blank_lines(lines)
            file_content.seek(0)
            for line in clean_lines:
                if(line == keyword):
                    found = True
                else:
                    file_content.write(line + "\n")
                    
            file_content.truncate()
        return found
    
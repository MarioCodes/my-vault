'''
Created on 08.06.2018

@author: Mario
'''
from scraper.utilities.FileReader import FileReader
from scraper.utilities.WebUtilities import WebUtilities

class RestOperations(object):
    '''
    classdocs
    '''

    def get_info(self):
        """ Retrieves status on the current system.
                Reads an info file which contains the parsed webs, does a ping to them and returns
                    a static web with the info.
                    
        :return: Static web to directly show the HTML with the obtained info.
        :rtype: String
        """
        utilities = WebUtilities()
        reader = FileReader("config/webs.txt")
        web_list = reader.read()
        utilities.create_static_web("Webs searched")
        for web in web_list:
            utilities.append_paragraph("Web: " + web + ", actual status: " + utilities.web_status(web))
        return utilities.build()

    def get_keywords(self):
        """ Gets all the current keywords written into the config file.
        
        :return: All current keywords.
        :rtype: Iterable
        """
        reader = FileReader("config/keywords.txt")
        return reader.read()
    
    def post_keyword(self, input_keyword):
        """ Appends a new keyword into the keywords config file.
        
        :param input_keyword: Keyword to append.
        :return: Result of the operation.
        :rtype: boolean
        """
        input_keyword = self.__clean_input(input_keyword)
        reader = FileReader("config/keywords.txt")
        return reader.append_keyword(input_keyword)

    def __clean_input(self, keyword):
        return keyword.strip().lower()
    
    def delete_keyword(self, input_keyword):
        """ Removes the given keyword from the config file.
        
        :param input_keyword: str we want to remove.
        :return: Result of the operation
        :rtype: boolean
        """
        clean_keyword = self.__clean_input(input_keyword)
        reader = FileReader("config/keywords.txt")
        return reader.delete_keyword(clean_keyword)
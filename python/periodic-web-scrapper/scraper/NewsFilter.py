'''
Created on Apr 12, 2018

@author: msanchez
'''
from scraper.utilities.FileReader import FileReader

class NewsFilter(object):
    ''' Takes as input a list which contains another 2 lists, them being the titles (index [0]) and links [1] already parsed and clean from 
            all the available news. Once done so, opens a config file which contains the wanted keywords, compares the titles against them
            and returns only matching ones in the same format as input. 
            
        Attributes:
            FILE_NAME   file to obtain the keywords from.
            input_lists lists which contain raw data to extract matches from.
            keywords    read keywords obtained from config file.
    '''
    
    FILE_NAME = 'config/keywords.txt'

    def __init__(self, input_lists):
        ''' Constructor
        
        :param input_lists: 2D list. Index 0 contains all the titles & index 1 contains all the links. For both lists, index (i.e.) 5 would be the title and link of the same new.
        :type 2D list: 
        '''
        self.input_lists = input_lists
        reader = FileReader(self.FILE_NAME)
        self.keywords = reader.read()
        
    def search(self):
        ''' Searches for the given keywords in the loaded news' titles
        
        :return same as input, 2D list. index 0 titles & idx 1 links:
        :rtype list:
        '''
        for keyword in self.keywords:
            keyword = self.__clean_input(keyword)
            results = self.__compare_against_titles(keyword)

        return results
    
    def __clean_input(self, keyword_input):
        return " " + keyword_input.strip() + " "
    
    def __compare_against_titles(self, keyword):
        results = list()
        matching_titles = list()
        matching_links = list()
        titles = self.input_lists[0]
        links = self.input_lists[1]
        
        for index, title in enumerate(titles):
            if keyword.lower() in title.lower():
                matching_titles.append(title)
                titles_link = links[index]
                matching_links.append(titles_link)

        results.append(matching_titles)
        results.append(matching_links)                    
        return results
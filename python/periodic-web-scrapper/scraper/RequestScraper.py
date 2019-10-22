'''
Created on Mar 22, 2018

@author: msanchez
'''

import re
from bs4 import BeautifulSoup, Tag

class RequestScraper(object):
    ''' Gets as parameter a whole unfiltered request (full HTML page). Gives back a list with raw HTML <a> tags. One tag for every headline. 
    
        Attributes:
            web   Complete request. contains HTTP response code and whole unfiltered content.
            soup  First BeautifulSoup created with the contents of the unfiltered web.
    '''

    def __init__(self, web):
        self.web = web
        self.soup = BeautifulSoup(web.content, "html.parser")
        
    def scrap_news(self):
        ''' Searches for news headers in the whole requests, filters out broken or undesired HTML tags and gives back a list with only wanted ones.
        
        :return Raw <a> HTML elements:
        :rtype list:
        '''
        news_html_elements = self.__get_all_news_headers()
        news_soup = BeautifulSoup(str(news_html_elements), "html.parser")
        wanted_tags = list()

        for new in news_soup:
            if len(new) == 1 and isinstance(new, Tag): # len == 2 are all commas & out none types
                line_soup = BeautifulSoup(str(new), "html.parser")
                a_tag = line_soup.a
                wanted_tags.append(a_tag)

        return wanted_tags
                
    def __get_all_news_headers(self):
        return self.__search_element_by_aprox_attribute("h2", "id", "titulo")
                
    def __search_element_by_aprox_attribute(self, element, attribute, value):
        return self.soup.find_all(element, {attribute : re.compile(value + "*") })
    
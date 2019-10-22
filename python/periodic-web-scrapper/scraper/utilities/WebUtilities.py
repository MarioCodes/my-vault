'''
Created on Mar 22, 2018

@author: msanchez
'''

import requests
from html.parser import HTMLParser
import urllib

class WebUtilities(object):

    def __init__(self):
        ''' Utility class. Has everything that has to do with web download / requests.
                It may also create static HTML web pages.
        
            Attributes:
                content    Part of the page which will form the main content, this is the main info to show.
                title      title which will be shown into HTML <h1> tags as main header.
        '''
        self.content = ""
        
    def download(self, url):
        ''' Obtains complete request from an URL. 
        
        :param url: Complete url to download
        :return: Complete request
        :rtype: requests.Response
        '''
        page = requests.get(url)
        page._content = self.__unescape(page)
        return page
    
    def web_status(self, url):
        ''' Pings a web and shows if it's reachable.
        
        :return: online if status code == 200, else offline
        :rtype: str
        '''
        status_code = urllib.request.urlopen(url).getcode()
        return "online" if status_code == 200 else "offline" # todo: change to bool
        
    def __unescape(self, page):
        ''' Removes HTML Entities. If not done, chars such as 'á' would appear as 'x/0f1' when read.
        '''
        parser = HTMLParser()
        return parser.unescape(page.text)
    
    def create_static_web(self, title):
        ''' Sets the title which will be shown to the given str and creates and empty content
        
        :param title: str to set as the web's title
        '''
        self.content = ""
        self.title = title
        
    def append_paragraph(self, content):
        ''' Appends the given parameter as a part of the main content to be shown.
                it does so appending it into <p> tags.
                
        :param content: str to be added in a new line into <p> tags
        '''
        self.content += "<p>" + content + "</p>"
        
    def build(self):
        ''' Main method to call when the rest of options are set. It will mount the title with the content
                and return the whole web as a str.
                
        :return: whole static web with appended title and content.
        :rtype: str
        '''
        html_page = "<html><h1>" + self.title + "</h1>"
        for entry in self.content:
            html_page += entry
        html_page += "</html>"
        return html_page
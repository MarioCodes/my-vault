'''
Created on Apr 18, 2018

@author: msanchez
'''
from scraper.RequestScraper import RequestScraper
from scraper.HTMLFilter import HTMLFilter
from scraper.NewsFilter import NewsFilter
from scraper.utilities.WebUtilities import WebUtilities

class Scraper(object):
    ''' Full scrap operation. Downloads the request with an URL. Checks the HTTP status code. In case it's correct, proceeds with the scrap & filter operation.
    '''

    def __init__(self):
        '''
        Constructor
        '''
        
    def scrap(self):
        web = self.__download()
        result = list()
        if(200 == web.status_code):
            scraper = RequestScraper(web)
            html_news_tags = scraper.scrap_news()
            cleaned_tags = self.__clean(html_news_tags)
            result = self.__filter(cleaned_tags)
        else:
            print("There was an error on download operation. Status code: ", str(web.status_code))
    
        return result
    
    def __download(self):
        downloader = WebUtilities()
        return downloader.download("https://www.heraldo.es/")
    
    def __clean(self, html_tags):
        tag_filter = HTMLFilter(html_tags)
        return tag_filter.filter()
    
    def __filter(self, unfiltered_tags):
        matcher = NewsFilter(unfiltered_tags)
        return matcher.search()
'''
Created on Apr 11, 2018

@author: msanchez
'''

class HTMLFilter(object):
    ''' Extracts the desired text and attributes from HTML <a></a> tags. It saves it in several lists.
    
        Attributes:
            tags    HTML tags with raw info.
            result  2D list w. filtered results. Contains a list for every wanted item.
    '''

    def __init__(self, tags):
        self.tags = tags
        self.result = list()
    
    def filter(self):
        ''' Filters all the titles and links from desired HTML tags
        
        :return: Index 0 contains list with all titles, idx 1 contains list with all links. For the same index at both lists, it's title and link for the same new.
        :rtype: 2D list
        '''
        
        titles = list()
        links = list()
        
        for tag in self.tags:
            titles.append(tag.text)
            links.append("www.heraldo.es" + tag["href"])
            
        self.result.append(titles)
        self.result.append(links)
        
        return self.result
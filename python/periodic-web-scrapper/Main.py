#!/usr/bin/env python
'''
Created on Mar 22, 2018

@author: msanchez
'''

from flask import Flask, jsonify
from scraper.Scraper import Scraper
from scraper.utilities.WebUtilities import WebUtilities
from scraper.RestOperations import RestOperations

app = Flask("news-scraper")
api_version ="/api/v0.1"

@app.route("/")
def default():
    scraper = Scraper()
    return scraper.scrap()
    
@app.route(api_version + "/keywords", methods = ['GET'])
def get_keywords():
    rest = RestOperations()
    return jsonify(rest.get_keywords())
    
@app.route(api_version + "/keywords/<string:keyword>", methods = ['POST'])
def post_keyword(keyword):
    rest = RestOperations()
    result = rest.post_keyword(keyword)
    return str(result)
    
@app.route(api_version + "/keywords/<string:keyword>", methods = ['DELETE'])
def delete_keyword(keyword):
    rest = RestOperations()
    result = rest.delete_keyword(keyword)
    return str(result)
    
@app.route(api_version + "/status", methods = ['GET'])
def info():
    rest = RestOperations()
    return rest.get_info()
    
@app.route(api_version + "/testing")
def testing():
    web = WebUtilities()
    return web.web_status("http://www.heraldo.es")
    
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=4869)

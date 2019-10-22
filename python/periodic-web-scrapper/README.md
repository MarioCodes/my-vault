# Web-Scrapper API (Python3 + BeautifulSoup4) [WIP]

News web scrapper. It checks news web pages, and compares the headlines against specified keywords, in case there's a match it sends the headline and link of it through e-mail. Working so it's accesible and modifiable as an API.  
Integrated with [docker](https://www.docker.com/what-docker) so it's easily portable and runnable.

## How-to start
```
docker build -t web-scraper:latest -f DockerFile .
docker run -p 4000:4869 web-scraper:latest
```
The first port number it's the one which will be accesible outside of docker's container (the one we must access). The second it's the one specified on _python's_ script.

## Doc

### Accesible Port
Right now it's hardcoded on _Main.py_. To set it in a config file.

### Config
###### keywords.txt
Keywords which will be searched at the scrapped webs.

###### webs.txt
Informative file to show the several webs which are scrapped. They must be added with the following format:  
https://example.com  
This is so, as a ping will be done on /status to see if they're online / reachable.


### (TODO) Endpoints
GET /api/{{version}}/status

GET   /api/{{version}}/keywords  
POST /api/{{version}}/keywords/{{value}}   
DELETE /api/{{version}}/keywords/{{value}}

## Known Troubles / To Fix

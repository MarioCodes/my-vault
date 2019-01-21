# Golang

## Installation
### Check installation
* `go version`

### Environment variables

**GOROOT**  
Folder where go was installated. It must only be set when installing to a custom location.

**GOPATH**  
Place to get, build and install packages outside the standard Go Tree.

[Source](https://stackoverflow.com/questions/7970390/what-should-be-the-values-of-gopath-and-goroot). Example

    # golang
    export GOPATH=/home/msanchez/go/packages
    export GOROOT=/home/msanchez/Programs/go
    export PATH=$PATH:$GOROOT/bin:$GOPATH/bin

## IDE
I use Sublime, to prepare it:

* `ctrl + shift + p`
* `install package`  

Install the packages:
* `GoOracle`
* `All Autocomplete`

_(missing steps)_

* install https://github.com/golang/sublime-build

To compile / build / run
* `ctrl + shift + P`
* `go install`
* `go run`


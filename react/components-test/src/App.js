import React, { Component } from 'react';
import './App.css';
import HelloWorld from './component/HelloWorld'

class App extends Component {
  render() {
    return (
      <div className="App">
          <HelloWorld/>
      </div>
    );
  }
}

export default App;

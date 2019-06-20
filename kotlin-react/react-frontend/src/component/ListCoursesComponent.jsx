import React, { Component } from 'react';

class ListCoursesComponent extends Component {
  render() {
    return (
      <div className="container">
        <h3>All Courses</h3>
        <div className="container">
            <table className="table">
              <thead>
                <tr>
                  <td>Id</td>
                  <td>Description</td>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Learn full stack...</td>
                </tr>
              </tbody>
            </table>
        </div>
      </div>
    )
  }
}

export default ListCoursesComponent

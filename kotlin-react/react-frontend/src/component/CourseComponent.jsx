import React from 'react';
import CourseDataService from "../service/CourseDataService";

class CourseComponent extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            description: ""
        }
    }

    componentDidMount() {
        console.log(this.state.id)

        // eslint-disable-next-line?
        if (this.state.id === -1) {
            return
        }

        CourseDataService.retrieveCourse(this.state.id)
            .then(response => this.setState(
                {description: response.data.description}
            ))
    }

    render() {
        let { description, id } = this.state
        return (
            <div>
              <h3>Course</h3>
              <div>{id}</div>
              <div>{description}</div>
            </div>
        )
    }
}

export default CourseComponent

import ReactDOM from 'react-dom'
import React from 'react';

class Test extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>Hello!</div>
        );
    }
}

ReactDOM.render(
    <Test />,
    document.getElementById('content')
);

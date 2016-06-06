import React from 'react'
import ReactDOM from 'react-dom'
import $ from 'jquery'
import DateHelper from './utils/date-helper'
import RegistrationForm from './registration-form'

class Registration extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userId: document.getElementById("container").getAttribute("userId"),
            load: false
        }
    }

    componentDidMount() {
        self = this;
        Promise.resolve($.ajax('/schedule/api/v1/term')).then(
            function (response) {
                self.setState({
                    termList: response
                });
                return Promise.resolve($.ajax('/schedule/api/v1/state'));
            },
            function (error) {
                throw error;
            }
        ).then(
            function (response) {
                self.setState({
                    stateList: response,
                    load: true
                });
            },
            function (error) {
                throw error;
            }
        );
    }

    render() {
        if (!this.state.load) {
            return null;
        }
        const now = new Date();
        let forms = [];
        for (var i = 1; i <= 7; i++) {
            forms.push(
                <RegistrationForm
                    userId={this.state.userId}
                    date={DateHelper.addDate(now, i)}
                    termList={this.state.termList}
                    stateList={this.state.stateList}
                    key={i}
                    ref={i}
                />
            );
        }
        let buttonStyle = {
            marginLeft: 'auto',
            marginRight: 'auto',
            width: 100
        };
        return (
            <div>
                {forms}
                <div style={buttonStyle}>
                    <button type="button" className="btn btn-primary btn-lg text-right"
                            onClick={this.doPost.bind(this)}>
                        全て保存
                    </button>
                </div>
            </div>
        );
    }

    doPost() {
        Promise.resolve($.ajax({
            url: `/schedule/api/v1/schedule/${this.state.userId}`,
            type: 'POST',
            contentType: 'text/json',
            data: JSON.stringify(this.getPostData())
        })).then(
            function (response) {
                location.href = '/schedule';
            },
            function (error) {
                throw error;
            }
        )
    }

    getPostData() {
        var scheduleList = [];
        for (var i = 1; i <= 7; i++) {
            scheduleList.push(this.refs[i].getPostData());
        }
        return scheduleList;
    }
}

ReactDOM.render(
    <Registration />,
    document.getElementById('container')
);
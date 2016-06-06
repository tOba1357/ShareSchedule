import React from 'react'
import $ from 'jquery'
import DateHelper from './utils/date-helper'
import ScheduleInput from './schedule-input'

export default class RegistrationForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            load: false,
            postData: new Array(this.props.termList.length)
        };
    }

    componentDidMount() {
        let url = `/schedule/api/v1/schedule/${this.props.userId}/${DateHelper.formatForInput(this.props.date)}`;
        Promise.resolve($.ajax(url)).then(
            function (response) {
                this.setState({
                    data: response,
                    load: true
                });
            }.bind(this),
            function (error) {
                throw error;
            }
        );
    }

    render() {
        if (!this.state.load) {
            return null;
        }
        const termInputs = this.props.termList.map(function (term, index) {
            const filteredSchedule = this.state.data.filter(function (schedule) {
                return schedule.term.id == term.id
            });
            const defaultState = filteredSchedule.length > 0 ? this.props.stateList[filteredSchedule[0].state].id : 3;
            const id = filteredSchedule.length > 0 ? filteredSchedule[0].id : null;
            return (
                <ScheduleInput
                    key={index}
                    term={term}
                    scheduleId={id}
                    defaultValue={defaultState}
                    ref={index}
                />
            );
        }.bind(this));
        return (
            <form>
                <div className="panel panel-default">
                    <div className="panel-heading">
                        <h3 className="panel-title">
                            {DateHelper.formatForDisplay(this.props.date)}
                            <button type="button" className="btn btn-primary" onClick={this.doPost.bind(this)}>決定
                            </button>
                        </h3>
                    </div>
                    <div className="panel-body">
                        <input type="hidden" name="date" value={DateHelper.formatForInput(this.props.date)}/>
                        {termInputs}
                    </div>
                </div>
            </form>
        );
    }

    doPost() {
        console.log(this.getPostDate());
        Promise.resolve($.ajax({
            url: `/schedule/api/v1/schedule/${this.props.userId}/${DateHelper.formatForInput(this.props.date)}`,
            type: 'POST',
            contentType: 'text/json',
            data: JSON.stringify(this.getPostDate())
        })).then(
            function (response) {
                location.reload();
            },
            function (error) {
                throw error;
            }
        )
    }

    onChange(term, value) {
        this.state.postData[term.id] = value;
    }

    getPostDate() {
        var scheduleList = [];
        for (var i = 0; i < this.props.termList.length; i++) {
            scheduleList.push(this.refs[i].getDate());
        }
        return {
            date: DateHelper.formatForInput(this.props.date),
            schedule: scheduleList
        };
    }
}

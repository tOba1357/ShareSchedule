import React from 'react'
import $ from 'jquery'
import DateHelper from './utils/date-helper'

export default class ScheduleInput extends React.Component {

    displayState() {
        return [
            'x',
            'o',
            '△',
            '?'
        ];
    }

    constructor(props) {
        super(props);
        this.state = {
            selected: this.props.defaultValue
        }
    }

    render() {
        return (
            <div>
                <h4>{this.props.term.description}</h4>

                {
                    this.props.scheduleId ?
                        <input type="hidden" name="id" value={this.props.scheduleId}/> :
                        null
                }

                <div onClick={this.onClick.bind(this)}>{this.displayState()[this.state.selected]}</div>
            </div>
        )
    }

    onClick() {
        this.setState({
            selected: (this.state.selected + 1) % this.displayState().length
        });
    }

    getDate() {
        if (this.props.scheduleId) {
            return {
                id: this.props.scheduleId,
                state: this.state.selected,
                term: this.props.term.id
            };
        } else {
            return {
                state: this.state.selected,
                term: this.props.term.id
            }
        }
    }
}
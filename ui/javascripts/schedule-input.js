import React from 'react'

export default class ScheduleInput extends React.Component {

    displayStateUrl() {
        return [
            '/schedule/assets/images/batu.png',
            '/schedule/assets/images/maru.png',
            '/schedule/assets/images/sankaku.png',
            '/schedule/assets/images/hatena.png'
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
            <td style={{borderStyle: "none"}} onClick={this.onClick.bind(this)}>
                <img src={this.displayStateUrl()[this.state.selected]} height="50px" width="50px"/>
            </td>
        )
    }

    onClick() {
        this.setState({
            selected: (this.state.selected + 1) % this.displayStateUrl().length
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
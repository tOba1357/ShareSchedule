var React = require('react');
var ReactDOM = require('react-dom');

var SettingTerm = React.createClass({
    getInitialState: function () {
        return {
            rowCount: 1
        };
    },

    render: function () {
        var inputs = [];
        for (var i = 0; i < this.state.rowCount; i++) {
            inputs.push(
                <input className="form-control" type="text" name={i} placeholder="18:00~19:00" key={i}/>
            )
        }
        return (
            <form method="post" action={this.props.postUrl}>
                <h2>
                    Term
                    <button className="btn btn-primary" type="submit">保存</button>
                </h2>
                {inputs}
                <button className="btn" type="button" onClick={this.addInput}>増やす</button>
                <button className="btn" type="button" onClick={this.reduceInput}>減らす</button>
            </form>
        );
    },

    addInput: function () {
        this.setState({
            rowCount: this.state.rowCount + 1
        });
    },

    reduceInput: function () {
        if (this.state.rowCount <= 0) {
            return;
        }
        this.setState({
           rowCount: this.state.rowCount - 1
        });
    }
});

ReactDOM.render(
    <SettingTerm postUrl="/schedule/setting"/>,
    document.getElementById('content')
);
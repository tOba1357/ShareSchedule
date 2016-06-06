export default class DateHelper {
    static dayOfWeekName() {
        return [
            '日',
            '月',
            '火',
            '水',
            '木',
            '金',
            '土'
        ];
    }

    static formatForInput(date) {
        return date.getFullYear() + '-' + (('0' + (date.getMonth() + 1)).slice(-2)) + '-' + (('0' + (date.getDate())).slice(-2));
    }

    static formatForDisplay(date) {
        return `${date.getMonth() + 1}/${date.getDate()} (${this.dayOfWeekName()[date.getDay()]})`;
    }

    static addDate(date, num) {
        const rtnDate = new Date(date);
        rtnDate.setDate(date.getDate() + num);
        return rtnDate;
    }
}
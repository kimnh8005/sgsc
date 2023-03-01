const formatter = {
	date: function (dateString, mask) {
		if (!dateString)
			return '';

		let yyyy = dateString.substr(0, 4);
		let MM = dateString.substr(4, 2);
		let dd = dateString.substr(6, 2);
		let hh = dateString.substr(8, 2);
		let mm = dateString.substr(10, 2);
		let ss = dateString.substr(12, 2);
		let time = (hh === '' || mm === '') ? '' : ' ' + hh + ':' + mm;
		time = (ss === '') ? time : ' ' + time + ':' + ss;

		if (mask === 'MM/dd~MM/dd'){
			let temp = dateString.split('~');
			return formatter.date(temp[0], 'MM/dd') + '~' + formatter.date(temp[1], 'MM/dd');
		} else if (mask === 'MM/dd'){
			return MM + '/' + dd;
		} else if (mask === 'MM/dd hh:mm'){
			if (mm === '')
			{
				mm = '00';
			}
			return MM + '/' + dd + ' ' + hh + ':' + mm;
		} else {
			return yyyy + '-' + MM + '-' + dd + time;
		}
	}
}

export default formatter;
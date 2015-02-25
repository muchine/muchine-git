// 오늘
function getToday(delim) {
	var now = new Date();
	return converDateString(now, delim);
}

// 몇일 전
function getBeforeDate(s, i) {
	var newDt = new Date(s);
	newDt.setDate(newDt.getDate() - i);
	return converDateString(newDt);
}

// 일주일 전
function getBefore1Week(delim) {
	var dt = new Date();
	dt.setDate(dt.getDate() - 6);
	return converDateString(dt, delim);
}

// 이주일 전
function getBefore2Week(delim) {
	var dt = new Date();
	dt.setDate(dt.getDate() - 13);
	return converDateString(dt, delim);
}


// 2013/05/06 형식으로 스트링 포매팅
function converDateString(dt, delim) {
	if (delim) {
		return dt.getFullYear() + delim + addZero(eval(dt.getMonth()+1)) + delim + addZero(dt.getDate());
	} else {
		return dt.getFullYear() + addZero(eval(dt.getMonth()+1)) + addZero(dt.getDate());
	}
	
}

// 5 -> 05 처럼 앞에 0 붙이기
function addZero(i) {
	var rtn = i + 100;
	return rtn.toString().substring(1,3);
}








//기준월 첫날
function getDt1(dt){
	var newDt = new Date(dt);
	newDt.setDate(1);
	return converDateString(newDt);
}
//기준월 말일
function getDt2(dt){
	var newDt = new Date(dt);
	newDt.setMonth(newDt.getMonth() + 1);
	newDt.setDate(0);
	return converDateString(newDt);
}
//이전달 첫날
function getDt3(dt){
	var newDt = new Date(dt);
	newDt.setMonth( newDt.getMonth() - 1 );
	newDt.setDate( 1);
	return converDateString(newDt);
}
//이전달 말일
function getDt4(dt){
	var newDt = new Date(dt);
	newDt.setMonth( newDt.getMonth() );
	newDt.setDate(0);
	return converDateString(newDt);
}
//다음달 첫날
function getDt5(dt){
	var newDt = new Date(dt);
	newDt.setMonth( newDt.getMonth() + 1 );
	newDt.setDate( 1);
	return converDateString(newDt);
}
//다음달 말일
function getDt6(dt){
	var newDt = new Date(dt);
	newDt.setMonth( newDt.getMonth() + 2 );
	newDt.setDate(0);
	return converDateString(newDt);
}
//몇달 후 말일
function getDt7(s, i){
	var newDt = new Date(s);
	newDt.setMonth( newDt.getMonth() + i );
	newDt.setDate(0);
	return converDateString(newDt);
}
//몇달 후 첫날
function getDt8(s, i){
	var newDt = new Date(s);
	newDt.setMonth( newDt.getMonth() + i );
	newDt.setDate(1);
	return converDateString(newDt);
}

//몇일 후
function getDt10(s, i){
	var newDt = new Date(s);
	newDt.setDate( newDt.getDate() + i );
	return converDateString(newDt);
}


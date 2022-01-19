let booking_1 = {};
let booking_2 = {
    toTrainNo: '', 
    toDate: '',
    backTrainNo: '',
    backDate: ''
};

$(()=>{
    function init(){
        //取得上一頁搜尋的結果(時刻表)
        let booking_1_r1 = JSON.parse(window.localStorage.getItem('booking_1_result1')).data;
        let booking_1_r2 = JSON.parse(window.localStorage.getItem('booking_1_result2')).data;
        booking_1 = JSON.parse(window.localStorage.getItem('booking_1'));
        booking_2['toDate'] = booking_1['toDate'];
        booking_2['backDate'] = booking_1['backDate'];

        //去程
        $("#goTrip_name1").text(booking_1_r1[0]['startStation']);
        $("#goTrip_name2").text(booking_1_r1[0]['endStation']);
        $("#goTrip_date").text(booking_2['toDate']);
        $("#goTrip_day").text(`(${getDayStr(booking_2['toDate'])})`);
        
        //建立時刻表
        for(let i=0; i<booking_1_r1.length; i++){
            let row = createRow(booking_1_r1[i].trainNo, 
                booking_1_r1[i].departureTime, 
                booking_1_r1[i].arrivalTime, 0);
            $("#goTrip_table > tbody").append(row);
        }
        $("input[name='TrainGroup0']").on('click', (e)=>{
            booking_2['toTrainNo'] = e.target.value;
            $("#goTripRow").css('display', ''); //顯示ROW
            $("#goTripRow #date").text(booking_2['toDate']);
            $("#goTripRow #trainNo").text(booking_2['toTrainNo']);
            $("#goTripRow #startStaion").text(booking_1_r1[0]['startStation']);
            $("#goTripRow #endStaion").text(booking_1_r1[0]['endStation']);
            $("#goTripRow #departureTime").text('');
            $("#goTripRow #arrivalTime").text('');
        });
        if(booking_1['returnTicket']){
            $("#backTrip").css('display', ''); //顯示回程
            $("#backTrip_name1").text(booking_1_r2[0]['startStation']);
            $("#backTrip_name2").text(booking_1_r2[0]['endStation']);
            $("#backTrip_date").text(booking_2['backDate']);
            $("#backTrip_day").text(`(${getDayStr(booking_2['backDate'])})`);
             //建立時刻表
            for(let i=0; i<booking_1_r2.length; i++){
                let row = createRow(booking_1_r2[i].trainNo, 
                    booking_1_r2[i].departureTime, 
                    booking_1_r2[i].arrivalTime, 1);
                $("#backTrip_table > tbody").append(row);
            }
            $("input[name='TrainGroup1']").on('click', (e)=>{
                booking_2['backTrainNo'] = e.target.value
                $("#backTripRow").css('display', ''); //顯示ROW
                $("#backTripRow #date").text(booking_2['backDate']);
                $("#backTripRow #trainNo").text(booking_2['backTrainNo']);
                $("#backTripRow #startStaion").text(booking_1_r2[0]['startStation']);
                $("#backTripRow #endStaion").text(booking_1_r2[0]['endStation']);
                $("#backTripRow #departureTime").text('');
                $("#backTripRow #arrivalTime").text('');
            });
        }

        //訂單明細

        $("#trainType").text(booking_1['trainType']);
        $("#ticket_adult").text(booking_1['ticket_adult']);
        $("#ticket_university").text(booking_1['ticket_university']);
  
    }
    
    function createRow(trainNo, depTime, arrTime, groupNum){
        let ts = timeSpan(depTime, arrTime);
        let rowStr = `<tr onmouseout="this.bgColor='#FFFFFF'" onmouseover="this.bgColor='#ffcc33'" style="color: rgb(51, 51, 51); font-weight: bold;">
                        <td><input name="TrainGroup${groupNum}" type="radio" value="${trainNo}"></td>
                        <td><span id="QueryCode">${trainNo}</span></td>
                        <td style="border-left: 1px solid #cccccc;"></td>
                        <td style="border-left: 1px solid #cccccc;"><span id="QueryDeparture">${depTime}</span></td>
                        <td><span id="QueryArrival">${arrTime}</span></td>
                        <td><span>${ts}</span></td>
                    </tr>`
        return rowStr;
    }

    init();
})
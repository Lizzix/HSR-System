var BookingS1 = {};

$(()=>{
    function init(){
        const stations = JSON.parse(httpGet('/json/station.json'));
        stations.sort((x, y)=>x.StationID>y.StationID ? 1 : -1); //排序車站 北至南
        let startStation = document.getElementById('startStation');
        let endStation = document.getElementById('endStation');
        for(let i=0; i<stations.length; i++){
            let id = stations[i]["StationID"];
            let name = stations[i]["StationName"]["Zh_tw"];
            startStation.add(createOption(id, name));
            endStation.add(createOption(id, name));
        }
    
        //日期UI
        $("#toDate").datepicker({
            dateFormat: "yy-mm-dd",
            minDate: 0,
            maxDate: 29,
            onSelect: (dateText)=>{
                $("#backDate").datepicker("setDate", dateText);
            }
        });
        $("#backDate").datepicker({
            dateFormat: "yy-mm-dd",
            minDate: 0,
            maxDate: 29,
        });
        //設定 初始時間
        $("#toDate").datepicker("setDate", "0d");
        $("#backDate").datepicker("setDate", "0d");
    
    
        //時間UI
        let toTime = document.getElementById('toTime');
        let backTime = document.getElementById('backTime');
        for(let i=0; i<24; i++){
            for(let j=0; j<2; j++){
                let opt1 = document.createElement("option");
                let opt2 = document.createElement("option");
                let hour = (i + '').padStart(2, '0');
                let minu = (j*30 + '').padStart(2, '0');
                let opt_val = hour + ':' + minu;
                toTime.add(createOption(opt_val, opt_val));
                backTime.add(createOption(opt_val, opt_val));
            }
        }
        
    
        //票數UI
        let ticket_adult = document.getElementById('ticket_adult');
        let ticket_university = document.getElementById('ticket_university');
        for(let i=0; i<11; i++){
            ticket_adult.add(createOption(i , i));
            ticket_university.add(createOption(i , i));
        }
        ticket_adult.selectedIndex = 1;
        ticket_university.selectedIndex = 0;

        //event
        $('#returnCheckBox').change(function(){
            if(this.checked){
                $('#backInfo').removeClass('hide');
            }else{
                $('#backInfo').addClass('hide');
            }
        });
    
        $('#searchBtn').click(()=>{
            let sid = $("#startStation").val();
            let eid = $("#endStation").val();
            let toDate = $("#toDate").val();
            let toTime = $("#toTime").val();
            let backDate = $("#backDate").val();
            let backTime = $("#backTime").val();
            let seat = $("input[name='seatRadioGroup']").val(); //無
            let trainType = $("input[name='trainRadioGroup']").val(); //標準,經濟
            let returnTicket = $('#returnCheckBox').prop('checked'); //要購買來回票
            let ticket_adult = $('#ticket_adult').val(); //全票數量
            let ticket_university = $('#ticket_university').val(); //學生票數量
     
            let booking_1 = {
                toDate: toDate,
                toTime: toTime,
                backDate: backDate,
                backTime: backTime,
                startStation: sid,
                endStation: eid,
                seat: seat,
                trainType: trainType,
                returnTicket: returnTicket,
                ticket_adult: ticket_adult,
                ticket_university: ticket_university
            };

            let res1 = httpPost('/timetable', {
                date: toDate,
                time: toTime,
                startStation: sid,
                endStation: eid
            });
            debugger;
            let res2 = httpPost('/timetable', {
                date: backDate,
                time: backTime,
                startStation: eid,
                endStation: sid
            });
            window.localStorage.setItem("booking_1", JSON.stringify(booking_1));
            window.localStorage.setItem("booking_1_result1", res1);
            window.localStorage.setItem("booking_1_result2", res2);
            window.location.href = '/chooseTrain.html';
        });
    }
   
    init();
})
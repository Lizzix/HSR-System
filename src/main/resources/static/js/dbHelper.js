const db = require('better-sqlite3')('./data.db');
var json_station = require('../json/station.json');
var json_timeTable = require('../json/timeTable.json').timeTable;


module.exports = {
    loadStation: ()=>{
        json_stations.sort((x, y)=>x.StationID>y.StationID ? 1 : -1); //排序車站 北至南
        for(let i=0; i<json_station.length; i++){
            let id = json_station[i]["StationID"];
            let name_zh = json_station[i]["StationName"]["Zh_tw"];
            let name_en = json_station[i]["StationName"]["En"];
            let address = json_station[i]["StationAddress"];
            db.prepare(`INSERT INTO [Station] (id, zh_tw, en, address) VALUES (@id, @zh_tw, @en, @address)`
            ).run({
                id: id,
                zh_tw: name_zh,
                en: name_en,
                address: address
            });
        }
    },
    loadTimeTable: ()=>{
        for(let i=0; i<json_timeTable.length; i++){
            let td = json_timeTable[i]["GeneralTimetable"];
            let TrainNo = td["GeneralTrainInfo"]["TrainNo"];
            let Direction = td["GeneralTrainInfo"]["Direction"];
            let StartingStationID = td["GeneralTrainInfo"]["StartingStationID"];
            let EndingStationID = td["GeneralTrainInfo"]["EndingStationID"];
            let StopTimes = td["StopTimes"];
            let Service_1 = td["ServiceDay"]["Monday"];
            let Service_2 = td["ServiceDay"]["Tuesday"]<<1;
            let Service_3 = td["ServiceDay"]["Wednesday"]<<2;
            let Service_4 = td["ServiceDay"]["Thursday"]<<3;
            let Service_5 = td["ServiceDay"]["Friday"]<<4;
            let Service_6 = td["ServiceDay"]["Saturday"]<<5;
            let Service_7 = td["ServiceDay"]["Sunday"]<<6;
            let ServiceDay = Service_1|Service_2|Service_3|Service_4|Service_5|Service_6|Service_7;

            db.prepare(`INSERT INTO [TimeTableHead] (TrainNo, Direction, StartingStationID, EndingStationID, ServiceDay) VALUES (@TrainNo, @Direction, @StartingStationID, @EndingStationID, @ServiceDay)`
            ).run({
                TrainNo: TrainNo,
                Direction: Direction,
                StartingStationID: StartingStationID,
                EndingStationID: EndingStationID,
                ServiceDay: ServiceDay
            });

            for(let i=0; i<StopTimes.length; i++){
                let StopSequence = StopTimes[i]["StopSequence"];
                let StationID = StopTimes[i]["StationID"];
                let DepartureTime = StopTimes[i]["DepartureTime"];
                db.prepare(`INSERT INTO [TimeTableBody] (TrainNo, StopSequence, StationID, DepartureTime) VALUES (@TrainNo, @StopSequence, @StationID, @DepartureTime)`
                ).run({
                    TrainNo: TrainNo,
                    StopSequence: StopSequence,
                    StationID: StationID,
                    DepartureTime: DepartureTime
                });
            }
        }
    },
    searchTimeTable: (time, dire, day, sid, eid)=>{
        let rows = [];
        let sql = `
        SELECT b.*,s.zh_tw FROM TimeTableHead AS h
        LEFT JOIN TimeTableBody AS b
        ON h.TrainNo = b.TrainNo
        LEFT JOIN Station AS s
        ON s.id=b.StationID
        WHERE 
        h.Direction=@dire AND 
        (h.ServiceDay&@day)>0 AND 
        h.StartingStationID <= @sid AND
        h.EndingStationID >= @eid AND
        ((b.StationID=@sid AND b.DepartureTime>=@time) OR b.StationID = @eid)`;

        if(dire == 1){
            sql = `
        SELECT b.*,s.zh_tw FROM TimeTableHead AS h
        LEFT JOIN TimeTableBody AS b
        ON h.TrainNo = b.TrainNo
        LEFT JOIN Station AS s
        ON s.id=b.StationID
        WHERE 
        h.Direction=@dire AND 
        (h.ServiceDay&@day)>0 AND 
        h.StartingStationID >= @sid AND
        h.EndingStationID <= @eid AND
        ((b.StationID=@sid AND b.DepartureTime>=@time) OR b.StationID = @eid)`;
        }
        rows = db.prepare(sql).all({
            time: time,
            dire: dire,
            day: day,
            sid: sid,
            eid: eid
        });

        //去除沒有到達目的地的車號
        let tmpMap = {};
        let ret = [];
        for(let i=0; i<rows.length; i++){
            let trainNo = rows[i]['TrainNo'];
            if(tmpMap[trainNo]){
                let data = {};
                data['trainNo'] = trainNo;
                data['startStation'] = tmpMap[trainNo]['zh_tw'];
                data['endStation'] = rows[i]['zh_tw'];
                data['departureTime'] = tmpMap[trainNo]['DepartureTime'];
                data['arrivalTime'] = rows[i]['DepartureTime'];
                data['earlyDiscount'] = 0;
                data['universityDiscount'] = 0;
                ret.push(data);
            }else{
                tmpMap[trainNo] = rows[i];
            }
        }
        return ret;
    }
    
}


    
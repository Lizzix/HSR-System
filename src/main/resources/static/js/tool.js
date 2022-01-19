function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;
}
function httpPost(theUrl, data)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader('Content-Type', 'application/json');
    xmlHttp.send(JSON.stringify(data));
    return xmlHttp.responseText;
}
function createOption(value, text){
    var opt = document.createElement("option");
    opt.value = value;
    opt.text = text;
    return opt;
}
function timeSpan(time1, time2){
    var date1 = new Date(`1/Nov/2012 ${time1}:00`);
    var date2 = new Date(`1/Nov/2012 ${time2}:00`);
    var diff = date2.getTime() - date1.getTime();
    var days = Math.floor(diff / (1000 * 60 * 60 * 24));
    diff -=  days * (1000 * 60 * 60 * 24);
    var hours = Math.floor(diff / (1000 * 60 * 60));
    diff -= hours * (1000 * 60 * 60);
    var mins = Math.floor(diff / (1000 * 60));
    diff -= mins * (1000 * 60);
    var seconds = Math.floor(diff / (1000));
    diff -= seconds * (1000);
    return `${hours}:${mins}`;
}
function getDayStr(date){
    let days = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
    let day = (new Date(date)).getDay();
    return days[day];
}
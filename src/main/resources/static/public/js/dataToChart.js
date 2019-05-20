var week = [0];
var client = [12];

var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: week,
        datasets: [
            {
                data: client,
                label: "Klienci",
                borderColor: "#5cb85c",
                fill: false
            }
        ]
    },
    options: {
        title: {
            display: true,
            text: 'Ilość nowych klientów w przeciągu każdego tygodnia.',
            fontSize: 18
        }
    }
});

function load(){
    $.ajax({
        url : '/city',
        dataType: "json"
    }).done(function(res) {
        var weeks = week[week.length-1];
        week.push(weeks+res.weekOfActive);
        client.push(res.amountOfClient);
        myChart.update();
    })
}

$(document).ready(function(){
    setInterval(load,2000);
});
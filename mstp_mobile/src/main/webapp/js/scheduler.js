function inicializa_scheduler_feriado(){
var source =
            {
                dataType: 'json',
                dataFields: [
                    { name: 'id', type: 'string' },
                    { name: 'status', type: 'string' },
                    { name: 'about', type: 'string' },
                    { name: 'address', type: 'string' },
                    { name: 'company', type: 'string'},
                    { name: 'name', type: 'string' },
                    { name: 'style', type: 'string' },
                    { name: 'calendar', type: 'string' },
                    { name: 'start', type: 'date', format: "yyyy-MM-dd HH:mm" },
                    { name: 'end', type: 'date', format: "yyyy-MM-dd HH:mm" }
                ],
                id: 'id',
                url: '../sampledata/appointments.txt'
            };
            var adapter = new $.jqx.dataAdapter(source);
            $("#scheduler").jqxScheduler({
                date: new $.jqx.date(2017, 11, 23),
                width: getWidth("Scheduler"),
                height: 600,
                source: adapter,
                showLegend: true,
                ready: function () {
               
                },
                appointmentDataFields:
                {
                    from: "start",
                    to: "end",
                    id: "id",
                    description: "about",
                    location: "address",
                    subject: "name",
                    style: "style",
                    status: "status"
                },
                view: 'weekView',
                views:
                [
                    'dayView',
                    'weekView',
                    'monthView'
                ]
            });
}
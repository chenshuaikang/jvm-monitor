// Vue实例
let app = new Vue({
    el: '#app',
    data: {
        defaultActive: 'Redis监控',
        data: []
    },
    created() {
        // window.onload = function() {
        //     app.changeDiv();
        // }
        // window.onresize = function() {
        //     app.changeDiv();
        // }
        this.init(); //初始化
    },
    mounted() {
        this.$refs.loader.style.display = 'none';
    },
    methods: {
        _notify(message, type) {
            this.$message({
                message: message,
                type: type
            })
        },
        /**
         * 初始化
         */
        init() {
            this.loading = true;
            this.$http.get(api.redis.redisinfo.get).then(response => {
                this.data = response.body.data
                this.loading = false;
            })

            this.$http.get(api.redis.redismemory.get).then(response => {
                this.memory = response.body.data;
                this.loading = false;
            })
            var $this = this;
            $this.$http.get(api.redis.redismemory.get).then(response => {
                if (response.body.code == 200) {
                    Highcharts.chart('memory', {
                        chart: {
                            type: 'spline',
                            animation: Highcharts.svg,
                            marginRight: 10,
                            events: {
                                load: function () {
                                    var series = this.series[0];
                                    setInterval(function () {
                                        $this.$http.get(api.redis.redismemory.get).then(response => {

                                            if (response.body.code == 200) {
                                                var data = response.body.data;
                                                var x = new Date().getTime(),
                                                    y = data.memory / 1024;
                                                series.addPoint([x, y], true, true);
                                            } else {
                                                this._notify(response.body.msg, 'error');
                                            }
                                        })
                                    }, 3e3);
                                }
                            }
                        },
                        title: {
                            text: "Redis 内存实时占用情况",
                            style: {
                                "font-size": "1.2rem"
                            }
                        },
                        xAxis: {
                            type: 'datetime',
                            tickPixelInterval: 150
                        },
                        yAxis: {
                            title: {
                                text: "kb"
                            },
                            plotLines: [{
                                value: 0,
                                width: 1,
                                color: "#808080"
                            }]
                        },
                        tooltip: {
                            formatter: function () {
                                return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 2) + "Mb";
                            }
                        },
                        series: [{
                            name: "内存占用（kb）",
                            data: function () {
                                var data = [], time = new Date().getTime(), i;
                                for (i = -19; i <= 0; i++) {
                                    data.push({
                                        x: time + i * 1e3,
                                        y: Math.random() * (1e3 - 800) + 800
                                    });
                                }
                                return data;
                            }()
                        }]
                    });
                } else {
                    this._notify(response.body.msg, 'error');
                }
            })

            this.$http.get(api.redis.commandinfo.get).then(response => {
                if (response.body.code == 200) {
                    data = response.body.data.map(val => {
                        var json = {}
                        json.name = val.key
                        json.y = parseInt(val.value)
                        return json
                    })

                    Highcharts.chart('commandinfo', {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            type: 'pie'
                        },
                        title: {
                            text: 'Redis 命令执行情况'
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: true,
                                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                    style: {
                                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                    }
                                }
                            }
                        },

                        series: [{
                            name: 'Brands',
                            colorByPoint: true,

                            data: data
                        }]
                    });
                } else {
                    this._notify(response.body.msg, 'error');
                }
            })
            this.$http.get(api.redis.dbsize.get).then(response => {
                if (response.body.code == 200) {
                    Highcharts.chart('dbsize', {
                        chart: {
                            type: "spline",
                            animation: Highcharts.svg,
                            marginRight: 10,
                            events: {
                                load: function () {
                                    var series = this.series[0];
                                    setInterval(function () {
                                        $this.$http.get(api.redis.dbsize.get).then(response => {
                                            if (response.body.code == 200) {
                                                var data = response.body.data;
                                                var x = new Date().getTime(), y = data.dbsize;
                                                series.addPoint([x, y], true, true);
                                            } else {
                                                this._notify(response.body.msg, 'error')
                                            }
                                        })
                                    }, 3e3);
                                }
                            }
                        },
                        title: {
                            text: "Redis key的实时数量",
                            style: {
                                "font-size": "1.2rem"
                            }
                        },
                        xAxis: {
                            type: "datetime",
                            tickPixelInterval: 150
                        },
                        yAxis: {
                            title: {
                                text: "个"
                            },
                            plotLines: [{
                                value: 0,
                                width: 1,
                                color: "#808080"
                            }]
                        },
                        tooltip: {
                            formatter: function () {
                                return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 2) + "Mb";
                            }
                        },
                        series: [{
                            name: "keys",
                            data: function () {
                                var data = [], time = new Date().getTime(), i;
                                for (i = -19; i <= 0; i++) {
                                    data.push({
                                        x: time + i * 1e3,
                                        y: 0
                                    });
                                }
                                return data;
                            }()
                        }]
                    });
                } else {
                    this._notify(response.body.msg, 'error');
                }
            })
        },
    }
});

Highcharts.setOptions({

    credits: {
        enabled: false
    },

    legend: {
        enabled: false
    },

    exporting: {
        enabled: false
    },

    global: { useUTC: false },

    legend: {
        enabled: false
    },
});



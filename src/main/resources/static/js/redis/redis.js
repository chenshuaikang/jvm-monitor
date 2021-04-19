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
                                return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 2) + "kb";
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
                            text: 'Redis 命令分布情况'
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
                            name: '占比',
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
                                return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 0) + "个";
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

            this.$http.get(api.redis.kbpsinfo.get).then(response => {
                if (response.body.code == 200) {

                    let data = [];
                    response.body.data.forEach(val => {
                        let obj = {
                            name: val.key,
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
                        }
                        data.push(obj)
                    })
                    Highcharts.chart('kbpsinfo', {
                        chart: {
                            type: "spline",
                            animation: Highcharts.svg,
                            marginRight: 10,
                            events: {
                                load: function () {
                                    var series = this.series[0];
                                    var series1 = this.series[1];
                                    setInterval(function () {
                                        $this.$http.get(api.redis.kbpsinfo.get).then(response => {
                                            if (response.body.code == 200) {
                                                var data = response.body.data[0];
                                                var x = new Date().getTime(), y = Number(data.value);
                                                series.addPoint([x, y], true, true);

                                                var data1 = response.body.data[1];
                                                var x1 = new Date().getTime(), y1 = Number(data1.value);
                                                series1.addPoint([x1, y1], true, true);
                                            } else {
                                                this._notify(response.body.msg, 'error')
                                            }
                                        })
                                    }, 3e3);
                                }
                            }
                        },
                        title: {
                            text: "Redis 网络流量情况",
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
                                text: "kbps"
                            },
                            plotLines: [{
                                value: 0,
                                width: 1,
                                color: "#808080"
                            }]
                        },
                        tooltip: {
                            formatter: function () {
                                return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 2) + "kpbs";
                            }
                        },
                        series: data,
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
    plotOptions: {
        line: {
            dataLabels: {
                // 开启数据标签
                enabled: true
            },
            // 关闭鼠标跟踪，对应的提示框、点击事件会失效
            enableMouseTracking: false
        }
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



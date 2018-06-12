// var a = '09:30-11:00,14:00-15:30';
// // var b = '2018-05-29 09:29:58';
// var b = '2018-05-29 10:59:58';

 export function Timer(time_str, now, callback) {
    this._id = time_str + ' ' + now;
    this._seconds = 0;
    this._begin_after_seconds = 0;
    this._interval_fun = null;
    this._timeout_fun = null;
    var the = this;


    var af = function(af1, af2){
        af1 = af1.split(':'), af2 = af2.split(':');
        return (Number(af2[0])-Number(af1[0]))*60*60+(Number(af2[1])-Number(af1[1]))*60-Number(af1[2]);
    }

    var bf = function () {
        // if(window.console)
        //     console.log(the._id);
        if(the._seconds > 0){
            the._seconds--;
            callback(the._seconds, cf(the._seconds));
            if(the._seconds == 0){
                clearInterval(the._interval_fun);
            }
        }else{
            callback(0, cf(0));
        }
    }

    var cf = function (s) {
        return [Math.floor(Math.floor(s/60)/60), Math.floor(s/60)%60, s%60];
    }

    var df = function (str) {
        return Number(str.replace(':', ''));
    }

    var a = time_str.split('-');
    var a1 = a[0], a2 = a[1], b = now.substr(11), b1 = b.substr(0, 5);
    if(df(b1) > df(a2)){
        bf();
    }else if(df(b1) < df(a1)){
        this._seconds = af(a1+':00', a2);
        this._begin_after_seconds = af(b, a1);
        if(window.console)
            console.log(this._id + ' ' + Math.floor(Math.floor(this._begin_after_seconds/60)/60)+'小时'+Math.floor(this._begin_after_seconds/60)%60+'分'+this._begin_after_seconds%60+'秒之后执行');
        this._timeout_fun = setTimeout(function () {
            the._interval_fun = setInterval(bf, 1000);
        }, the._begin_after_seconds*1000);
    }else{
        this._seconds = af(b, a2);
        if(window.console)
            console.log(this._id + ' 剩余' + this._seconds + '秒');
        this._interval_fun = setInterval(bf, 1000);
    }

    this.destroy = function () {
        if(the._timeout_fun != null){
            clearTimeout(the._timeout_fun);
        }
        if(the._interval_fun != null){
            clearInterval(the._interval_fun);
        }
    }

    return {
        destroy: function () {
          if(window.console)
              console.log(the._id + ' destroy');
            if(the._timeout_fun != null){
                clearTimeout(the._timeout_fun);
            }
            if(the._interval_fun != null){
                clearInterval(the._interval_fun);
            }
        }
    }
}


// var aaa = Timer(a.split(',')[0], b, function (a, b) {
//     console.log(a, b)
// });

// aaa.destroy();

// var bbb = new Timer(a.split(',')[1], b, function (a, b) {
//     console.log(a, b)
// });

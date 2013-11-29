function activeMicro () {
  $( "#micro" ).addClass("active");
  $( "#bitcoin" ).removeClass("active");

  $("#bitcoinZone").fadeOut();
  $("#microZone").fadeIn();
}

function activeBitcoin () {
  $( "#micro" ).removeClass("active");
  $( "#bitcoin" ).addClass("active");

  $("#microZone").fadeOut();
  $("#bitcoinZone").fadeIn();
}

$( "#micro" ).click(activeMicro);
$( "#bitcoin" ).click(activeBitcoin);

var isMicro = true;

var minDecibelInput = document.getElementById("minDecibelInput");
var maxDecibelInput = document.getElementById("maxDecibelInput");
var micProgress = document.getElementById("micProgress");
var micProgressBs = document.getElementById("micProgressBs");

var ws = new WebSocket("ws://"+location.host+"/ws/height");

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
var ctx = new (window.AudioContext || window.webkitAudioContext)();
var userAudio = (function (d) {
  navigator.getUserMedia({ audio: true }, d.resolve, d.reject);
  return d.promise;
}(Q.defer()));

var microphone = userAudio.then(function (stream) {
  var mic = ctx.createMediaStreamSource(stream);
  var gain = ctx.createGain();
  gain.gain.value = 2;
  var compr = ctx.createDynamicsCompressor();
  mic.connect(gain);
  gain.connect(compr);
  return compr;
});

var REFRESH_RATE = 50;
var SAMPLES = 16;
var analyser = ctx.createAnalyser();
var array = new Uint8Array(SAMPLES);
analyser.smoothingTimeConstant = 0.9;
analyser.fftSize = SAMPLES * 2;
analyser.minDecibels = minDecibelInput.value;
analyser.maxDecibels = maxDecibelInput.value;

minDecibelInput.addEventListener("change", function() {
  analyser.minDecibels = this.value;
});
maxDecibelInput.addEventListener("change", function() {
  analyser.maxDecibels = this.value;
});

var lastTime = 0;
var sendRate = 300;
function sendHeight (v) {
    var now = Date.now();
    if (now-lastTime > sendRate) {
        lastTime = now;
        /*
        Qajax("/height", {
            logs: false,
            ie: false,
            method: "POST",
            data: { height: v }
        });
        */
        //ws.send(v);
    }
}

microphone.then(function (mic) {
  mic.connect(analyser);
  setInterval(function () {
    analyser.getByteFrequencyData(array);
    var sum = 0, nb = 0;
    var from = 0, to = Math.floor(array.length/2); // only care about 0 to 11kHz
    for (var i=from; i<to; ++i) {
      sum += array[i] / 255;
      nb ++;
    }
    var value = Math.floor(255*sum/nb);
    if (isMicro) { sendHeight(value); }
    micProgress.value = value;
    micProgressBs.style.width = value + '%';
  }, REFRESH_RATE);
});


// ---------------------------------------------
// BITCOINS
// ---------------------------------------------
function closeWS() {
  if(wsSocket) wsSocket.close();
}

var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
console.log(WS);
var wsSocket = new WS('ws://ws.blockchain.info/inv');
console.log(wsSocket);

wsSocket.onopen = function(evt) {
  console.log('onopen'); console.log(evt);
  console.log('sub');
  wsSocket.send('{"op":"set_tx_mini"}{"op":"unconfirmed_sub"}');
};

wsSocket.onclose = function(evt) { console.log('onclose'); console.log(evt); };

var nbTx = 0;
var totalAmount = 0;
wsSocket.onmessage = function(evt) {
  var value = JSON.parse(evt.data).x.value / 100000000;
  nbTx++;
  totalAmount += value;
};

wsSocket.onerror = function(evt) { console.log('onerror'); console.log(evt); };

setInterval(function () {
  var h = totalAmount > 200 ? 200 : totalAmount;
  nbTx = 0;
  totalAmount = 0;
  if (!isMicro) { sendHeight(h); }
}, 1000);

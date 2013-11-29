var minDecibelInput = document.getElementById("minDecibelInput");
var maxDecibelInput = document.getElementById("maxDecibelInput");
var micProgress = document.getElementById("micProgress");

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
var ctx = new (window.AudioContext || window.webkitAudioContext)();
var userAudio = (function (d) {
  navigator.getUserMedia({ audio: true }, d.resolve, d.reject);
  return d.promise;
}(Q.defer()));

var microphone = userAudio.then(function (stream) {
  var mic = ctx.createMediaStreamSource(stream);
  var gain = ctx.createGain();
  gain.gain.value = 10;
  var compr = ctx.createDynamicsCompressor();
  mic.connect(gain);
  gain.connect(compr);
  return compr;
});

var REFRESH_RATE = 50;
var SAMPLES = 16;
var analyser = ctx.createAnalyser();
var array = new Uint8Array(SAMPLES);
analyser.smoothingTimeConstant = 0.5;
analyser.fftSize = SAMPLES * 2;
analyser.minDecibels = -90;
analyser.maxDecibels = -40;

minDecibelInput.addEventListener("change", function() {
  analyser.minDecibels = this.value;
});
maxDecibelInput.addEventListener("change", function() {
  analyser.maxDecibels = this.value;
});

function sendHeight (v) {
    return Qajax("/height", {
        logs: false,
        ie: false,
        method: "POST",
        data: { height: v }
    });
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
    sendHeight(value);
    micProgress.value = value;
  }, REFRESH_RATE);
});

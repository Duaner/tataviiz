// Test tataviz fade Puissance 

int fan = 24;

int power = 200;

void setup() {
  Serial.begin(9600);
  pinMode(fan, OUTPUT);
}

void loop(){
  if(Serial.available()) {
    power = Serial.read();//(Serial.read() - '0') * 20;
    //Serial.print("I power ");
    //Serial.println(power, DEC);
  }
  for(int i = 0; i<360; i++){
    //convert 0-360 angle to radian (needed for sin function)
    float rad = DEG_TO_RAD * i;

    //calculate sin of angle as number between 0 and 255
    int sinOut = constrain((sin(rad) * 64) + power + 64, 0, 255); 

    analogWrite(fan, sinOut);
  }
}

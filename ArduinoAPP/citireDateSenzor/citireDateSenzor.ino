#include <SoftwareSerial.h>


SoftwareSerial esp8266(10, 11);

int sensor = A0 ;

void setup()
{
    pinMode(sensor, INPUT);

    Serial.begin(9600);
    esp8266.begin(115200);
    
    delay(2000);
    
    esp8266.println("AT+CWMODE=1");
    delay(2000);
    
    esp8266.println("AT+CWJAP=\"DIGI_f8e2c0\", \"a7ee071d\"");
    delay(5000);
    
}

void loop() 
{
    int sensorValue = analogRead(sensor);

    esp8266.println("AT+CIPMUX=0");
    delay(2000);
    
    esp8266.println("AT+CIPSTART=\"TCP\",\"api.thingspeak.com\",80");
    delay(2000);
    
    esp8266.println("AT+CIPSEND=51");
    delay(2000);
    
    esp8266.println("GET /update?api_key=DGHTAT9MKCKX4140&field1="+String(sensorValue));
    delay(2000);
    
    esp8266.println("AT+CIPCLOSE");
    delay(2000);

}
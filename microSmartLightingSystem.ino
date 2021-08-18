#include<C:\Users\sonas\AppData\Local\Arduino15\packages\esp32\hardware\esp32\1.0.6\libraries\WiFi\src\WiFi.h>
#include <FirebaseESP32.h>
#define FIREBASE_HOST "https://smartlightingsystem-21067-default-rtdb.firebaseio.com/"
#define FIREBASE_AUTH "2RfTwK0Lf7HDiLDtrjTu0F4nkC7AQVRQwUXSHUtl"
#define WIFI_SSID ""
#define WIFI_PASSWORD ""
#define led 2
#define led2 4
#define led3 18 
#define trigPin1 22
#define echoPin1 23
#define trigPin2 19
#define echoPin2 21
float heightOfRoom=200;
float widthOfRoom=100;
float duration,distance,duration2,distance2;
bool inside=false;

FirebaseData firebaseData;

void setup(){
  
  Initialization();
  WiFiConnection();
  pinMode(led,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(led3,OUTPUT);
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  
  
 }

int counter = 0;
float counter2 = 0.5;

void Initialization(){
  
  Serial.begin(115200); 
}

void WiFiConnection(){
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  
}

void loop(){

  if(Firebase.getInt(firebaseData,"/MyHome/Room1/override"))
  {
    if(firebaseData.intData()==1)
    {
      //if override true
      controlUsingApp();
      
      //digitalWrite(trigPin1,LOW);
    }
    else
    {
      //if override false
      checkForInside();
      
      Serial.println("Not overrided");
      //digitalWrite(trigPin1,HIGH);
      if(inside)
      {
        checkLeftRight();
      }
      else
      {
          digitalWrite(led3,LOW);
          digitalWrite(led,LOW);
      }
    }
  }
    
}
void controlUsingApp()
{
  //if override true

  if(Firebase.getInt(firebaseData,"/MyHome/Room1/Bulb1/status"))
  {
    if(firebaseData.intData()==1)
    {
      digitalWrite(led,HIGH);
    }
    else if(firebaseData.intData()==0)
    {
      digitalWrite(led,LOW);
    }
  }
  if(Firebase.getInt(firebaseData,"/MyHome/Room1/Bulb2/status"))
  {
    if(firebaseData.intData()==1)
    {
      digitalWrite(led2,HIGH);
    }
    else if(firebaseData.intData()==0)
    {
      digitalWrite(led2,LOW);
    }
  }
  if(Firebase.getInt(firebaseData,"/MyHome/Room1/Bulb3/status"))
  {
    if(firebaseData.intData()==1)
    {
      digitalWrite(led3,HIGH);
    }
    else if(firebaseData.intData()==0)
    {
      digitalWrite(led3,LOW);
    }
  }
  
    
  
}

void checkForInside()
{
    // Clears the trigPin
    digitalWrite(trigPin1, LOW);
    delayMicroseconds(2);
    // Sets the trigPin on HIGH state for 10 micro seconds
    digitalWrite(trigPin1, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin1, LOW);
    // Reads the echoPin, returns the sound wave travel time in microseconds
    duration = pulseIn(echoPin1, HIGH);
    // Calculating the distance
    distance = (duration/2)/29.1;

    // Prints the distance on the Serial Monitor
    Serial.print("Distance: ");
    Serial.println(distance);
    if(distance<heightOfRoom)
    {
      //someone is there
      inside=true;
      digitalWrite(led2,HIGH);
        
    
    }
    else
    {
      inside=false;
      digitalWrite(led2,LOW);
    }

}
void checkLeftRight()
{
    // Clears the trigPin
    digitalWrite(trigPin2, LOW);
    delayMicroseconds(2);
    // Sets the trigPin on HIGH state for 10 micro seconds
    digitalWrite(trigPin2, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin2, LOW);
    // Reads the echoPin, returns the sound wave travel time in microseconds
    duration2 = pulseIn(echoPin2, HIGH);
    // Calculating the distance
    distance2 = (duration2/2)/29.1;

    Serial.print("Distance2: ");
    Serial.println(distance2);
    
    if(distance2>widthOfRoom)
    {
      //right side of the room
        digitalWrite(led,LOW);
        digitalWrite(led3,HIGH);
    }
    else
    {
        //left side of the room
        digitalWrite(led,HIGH);
        digitalWrite(led3,LOW);
      
    }
} 

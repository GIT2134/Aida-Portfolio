long lastObstacleTime;
float flag;

void setup() {
  // put your setup code here, to run once:
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(115200); 
  Serial.setTimeout(5);
  lastObstacleTime = -1;
  flag=0;//used to debug and know which condition is being exectued.
}
void serialWait(){
  while(Serial.peek() != 's') {
    char t = Serial.read();
    digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
    delay(1);                      
    digitalWrite(LED_BUILTIN, LOW);    // turn the LED off by making the voltage LOW
    delay(1);
  }
  char t = Serial.read(); //discard the s
}   

void loop() {  
  //Read serial port until sensor data is sent
  serialWait();
  //Interpret the data String
  // Section 1***************************************
 
  float simTime = Serial.parseFloat();
  int   frontResult = Serial.parseInt();
  float frontDistance = Serial.parseFloat();
  int   leftResult = Serial.parseInt();
  float leftDistance = Serial.parseFloat();
  int   rightResult = Serial.parseInt();
  float rightDistance = Serial.parseFloat();

  float servo_position =0;
  int thrust_fan_state= 0 ;
  int lift_fan_state= 1 ;
  int hovercraft_body =0; 
  float thrust_fan_Throttle = 0;
  long timeNow = millis();
  
 
if (frontResult == 0 && leftResult==1 && rightResult==1)
{    
    
   
     thrust_fan_state= 1 ;
     thrust_fan_Throttle= 1;
     lift_fan_state= 1 ;
     servo_position =0;
     flag=1;
   
}

else if (frontResult == 1  && leftResult == 1 && rightResult == 1 && frontDistance > 0.5)
{ 
  //  Action if there is  an obstacle but the hovercraft did not achieve the gap between the two walls.
     thrust_fan_state= 1 ;
     thrust_fan_Throttle= 1;
     servo_position =0;
     lift_fan_state=1 ;
     flag=2;
  
}  
else if (frontResult == 1  && leftResult == 1 && rightResult == 0  && frontDistance<=0.50 )//&& leftDistance<=0.05)//&& frontDistance<=0.5 || frontDistance>=0.05)//
{ 
   
  //  Action if there is an obstacle "a wall" and the hovercraft needs to rotate to the right.  
  // no walls on the right side.
  
    if (frontDistance<=0.50 || frontDistance>0.15)
    {
   
    thrust_fan_state= 1 ;
    thrust_fan_Throttle= 1;//
    lift_fan_state= 1 ;
    servo_position =-3.14159/2;
    flag=3;
    }
    
}  


else if (frontResult == 1  && leftResult == 0 && rightResult == 1  && frontDistance<=50)//&& frontDistance <= 0.5 || frontDistance>0.05)
 {  //if no wall on the left side, it should turn left
  if (frontDistance<= 0.5 || frontDistance>=0.15)

  {
    //lastObstacleTime=timeNow+3000;
    thrust_fan_state= 1 ;
    thrust_fan_Throttle= 1;
    lift_fan_state= 1 ;
    servo_position=3.14159/2;  
    flag=4;
   //servo_position=0;
   }
   
 
      
  }
 
    
   
 else if (frontResult==0 && rightResult==1 && leftResult==0)
 {
         thrust_fan_state= 1 ;
         thrust_fan_Throttle= 1;
         lift_fan_state= 1 ;
         servo_position=0;  
         flag=5;
 }
 else if (frontResult==0 && rightResult==0 && leftResult==1)
 {
  thrust_fan_state=1;
  thrust_fan_Throttle=1;
  lift_fan_state=1;
  servo_position=0;
  flag=6;
 } 
 else if (frontResult==1 && leftResult==1 && rightResult==1 && frontDistance<=0.52 && rightDistance<leftDistance)
    {
      
    thrust_fan_state= 1 ;
    thrust_fan_Throttle= 1;
    lift_fan_state=1;
      //servo_position=-3.14159/4;
    servo_position=3.14159/4;
    flag=8;
    }
else if (frontResult==1 && leftResult==1 && rightResult==1 && frontDistance<0.52 && leftDistance<rightDistance)
{
  thrust_fan_state= 1 ;
  thrust_fan_Throttle= 1;
  lift_fan_state=1;
  servo_position=-3.14159/4;
  flag=9;
}

   else {
      thrust_fan_state= 1 ;
    thrust_fan_Throttle= 1;
     lift_fan_state=1;
    servo_position=0;
     flag=10;
       
    }
    
  


   Serial.print(simTime);
   Serial.write(",");
   Serial.print(timeNow);
   Serial.write(",");
   Serial.print(lastObstacleTime);
   Serial.write(",");
   Serial.print(frontDistance);
   Serial.write(",");
   Serial.print(leftDistance);
   Serial.write(",");
   Serial.print(rightDistance);
   Serial.write(",");
   Serial.print(servo_position);
   Serial.write(",");
   Serial.print(thrust_fan_state);
   Serial.write(",");
   Serial.print(lift_fan_state);
   Serial.write(",");
   Serial.print(hovercraft_body);
   Serial.write(",");
   Serial.print(thrust_fan_Throttle);
   Serial.write(",");
   Serial.print(frontResult);
   Serial.write(",");
   Serial.print(leftResult);
   Serial.write(",");
   Serial.print(rightResult);
   Serial.write(",");
   Serial.print(flag); 
   
   Serial.write("\r\n");
   
 //***************************************

}

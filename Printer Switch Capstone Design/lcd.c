#include "i2c_master.h"
#include "liquid_crystal_i2c.h"
#include "lcd.h"

#include <avr/io.h>
#include <util/delay.h>
#include <util/twi.h>
#include <stdint.h>

#define BUTTON1 6 // button switch connected to port B pin 6
#define BUTTON2 5 // button switch connected to port B pin 5
#define BUTTON3 4 // button switch connected to port B pin 4
#define BUTTON4 6 // button switch connected to port H pin 6

#define LED1 5 // Led1 connected to port H pin 5

#define DEBOUNCE_TIME 25 // time to wait while "de-bouncing" button 
#define LOCK_INPUT_TIME 300 // time to wait after a button press

void init_ports_mcu()
{
	//DDRB=0xFFu; //	Set all pins of the PORTB as output.
	DDRB &= ~(1<<BUTTON1);//Makes first pin of PORTB as Input
	DDRB &= ~(1<<BUTTON2);//Makes first pin of PORTB as Input
	DDRB &= ~(1<<BUTTON3);//Makes first pin of PORTB as Input
	PORTB |= (1<<BUTTON1); 
	PORTB |= (1<<BUTTON2);
	PORTB |= (1<<BUTTON3);
	DDRH=0xFFu; //	Set all pins of the PORTH as output.
	DDRH &= ~(1<<BUTTON4);//Makes first pin of PORTB as Input
	PORTH |= (1<<BUTTON4); 

}

unsigned char button1_state()
{
	/* the button is pressed when BUTTON1 bit is clear */
	if (!(PINB & (1<<BUTTON1)))
	{
		_delay_ms(DEBOUNCE_TIME);
		if (!(PINB & (1<<BUTTON1))) return 1;
	}
	return 0;
}
unsigned char button2_state()
{
	/* the button is pressed when BUTTON1 bit is clear */
	if (!(PINB & (1<<BUTTON2)))
	{
		_delay_ms(DEBOUNCE_TIME);
		if (!(PINB & (1<<BUTTON2))) return 1;
	}
	return 0;
}
unsigned char button3_state()
{
	/* the button is pressed when BUTTON1 bit is clear */
	if (!(PINB & (1<<BUTTON3)))
	{
		_delay_ms(DEBOUNCE_TIME);
		if (!(PINB & (1<<BUTTON3))) return 1;
	}
	return 0;
}
unsigned char button4_state()
{
	/* the button is pressed when BUTTON1 bit is clear */
	if (!(PINH & (1<<BUTTON4)))
	{
		_delay_ms(DEBOUNCE_TIME);
		if (!(PINH & (1<<BUTTON4))) return 1;
	}
	return 0;
}

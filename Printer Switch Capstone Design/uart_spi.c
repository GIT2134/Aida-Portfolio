#define F_CPU 16000000UL
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <avr/sleep.h> 
#include <string.h>
#include <util/twi.h>
#include <stdint.h>

#include "uart.h"
#include "spi.h"
#include "i2c_master.h"
#include "liquid_crystal_i2c.h"
#include "lcd.h"



volatile uint8_t receiving_message = 0;
volatile uint8_t new_rx = 1;
char* SectionSelected = "Section A";
int length =9;
//unsigned char bytes[length];


#define RED_PIN_1 PF0
#define GREEN_PIN_1 PF1

//MEGA SPI pin assignment
#define SPI_DDR DDRB
#define CS      PINB0
#define MOSI    PINB2
#define MISO    PINB3
#define SCK     PINB1


ISR(USART1_RX_vect)
{

    UDR0=UDR1;
	PORTF &= ~(1 << GREEN_PIN_1);

	
	

}
ISR(USART0_TX_vect)
{
    // USART has finished transmitting a byte
    receiving_message = 1;

	//PORTG &= ~(1 << LED_PIN);



	
	length = strlen(SectionSelected);
	unsigned char bytes[length];


	for (int i = 0; i < length; i++) {
        	bytes[i] = SectionSelected[i];
		SPI_masterTransmit(bytes[i]);
				//SPDR = bytes[i];
				
    	}
}



void SPI_init()
{
    // set CS, MOSI and SCK to output
    SPI_DDR |= (1 << CS) | (1 << MOSI) | (1 << SCK);

    // enable SPI, set as master, and clock to fosc/128
    SPCR = (1 << SPE) | (1 << SPIE)| (1 << MSTR) | (1 << SPR0) | (1 << SPR1);


	//PORTB |= (1 << CS); //SET CS HIGH

}

void SPI_masterTransmit(uint8_t data)
{

    // load data into register
    SPDR = data;

    // Wait for transmission complete
    while(!(SPSR & (1 << SPIF)));

	   
}



int main(void)
{
	 // Init I2C Master
    	i2c_master_init(I2C_SCL_FREQUENCY_100);
	uint8_t i2c_status;
	i2c_status=i2c_master_start(0x27,I2C_WRITE);
	_delay_ms(100);
	
	//from liquid_crystal_i2c lib
	LiquidCrystalDevice_t device = lq_init(0x27, 16, 2, LCD_5x8DOTS); 
	lq_turnOnBacklight(&device); // simply turning on the backlight
	
	//init GPIO
	init_ports_mcu();

	//init sections array
	char *sections[] = {"Section A","Section B","Section C","Section D","Section F","Section G","No section"};
	int sectionsSize= sizeof(sections)/sizeof(sections[0]);
	int index=0;
	int cursor=1;
	char *SectionSelected= sections[sectionsSize-1]; //"No section" ALWAYS the last element of the array

	//print home page
	lcd_home(&device, sections, index, cursor);
	int FlagSection=0; //section not selected, enable sections selection
	
	//init for Scope
	DDRF |= (1 << RED_PIN_1) | (1 << GREEN_PIN_1);// set RGB pins as outputs
	PORTF |= (1 << GREEN_PIN_1); //turn OFF LED
	PORTF |= (1 << RED_PIN_1); //turn OFF LED
	UART0_init(); 
	UART1_init();




	SPI_init();



	

	sei();
	
	
		


	while(1){
		if (receiving_message)
        	{
            // Do something when transmission is complete
            receiving_message = 0; // Reset flag
			PORTF |= (1 << GREEN_PIN_1); //turn on LED //turn off LED
				//new_rx=0;
		}
		//Buttons
		//Down button
		if (button1_state()){
			if (cursor==1 && FlagSection==0){
				lq_setCursor(&device, (cursor-1), 0); 
				lq_print(&device, " ");
				cursor=2;
				lq_setCursor(&device, (cursor-1), 0); 
				lq_print(&device, ">");
			
			}
			else if (cursor==2 && FlagSection ==0){
				if (index<(sectionsSize-2)){
					index++;
					lcd_printText(&device,sections[index],sections[index+1],1);
				}
			}
			_delay_ms(LOCK_INPUT_TIME); 
		}
		//Up button
		else if (button2_state()){
			if (cursor==1 && FlagSection ==0){
				if(index!=0){
					index--;
					lcd_printText(&device,sections[index],sections[index+1],1);
				}
			}
			else if (cursor ==2 && FlagSection ==0){
				lq_setCursor(&device, (cursor-1), 0); 
				lq_print(&device, " ");
				cursor=1;
				lq_setCursor(&device, (cursor-1), 0); 
				lq_print(&device, ">");
			}
			_delay_ms(LOCK_INPUT_TIME); 
		}
		//Select button
		else if (button3_state()){
			FlagSection=1;
			int tempIndex=index;
			if (cursor==2){tempIndex= index+1;}
			SectionSelected=sections[tempIndex];
			lq_clear(&device);
			lcd_printText(&device,sections[tempIndex],"Ready",1);
			_delay_ms(LOCK_INPUT_TIME); 
		}
		//Home button
		else if (button4_state()){
			FlagSection=0;
			lcd_home(&device, sections, index, cursor);
			_delay_ms(LOCK_INPUT_TIME); 
		}	


        }
	}


  


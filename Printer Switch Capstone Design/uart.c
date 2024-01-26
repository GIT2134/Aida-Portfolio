
#include <avr/interrupt.h>
#include <avr/io.h>
#include <stdlib.h>


#ifndef F_CPU
	#define F_CPU 16000000UL		// oscillator-frequency in Hz
#endif

#define BAUD 9600 //19200
#define UBRR_VAL ((F_CPU)/((16UL*BAUD))-1)
#define BAUD_RATE0 38400
#define BAUD_RATE1 9600
#define UBRR_VAL0 ((F_CPU / (16UL * BAUD_RATE0)) - 1)
#define UBRR_VAL1 ((F_CPU / (16UL * BAUD_RATE1)) - 1)

volatile uint8_t rx_buff, rx_irq_flag;



void SendChar(uint8_t c) {
	while(!(UCSR0A & (1 << UDRE0))); // wait until UDR ready
	UDR0 = c;    // send character
}

// Send string
void SendStr (char *s, uint8_t lfcr) { //lfcr=255 to add CR/LF at the end of string
	
	while (*s) //  loop until *s = NULL
	{
		SendChar(*s);
		s++;
	} 
	if (lfcr==255) {
		SendChar(0x0D); //CR
		SendChar(0x0A); //LF	
	}
}


// Send Int8
void SendInt8 (uint8_t num) {
char ss[6];
itoa (num, ss, 10); //convert number to string
SendStr (ss, 0);
}


// Send Int32
void SendInt32 (uint32_t num) {
char ss[12];
ltoa (num, ss, 10); //convert number to string
SendStr (ss, 0);
}




void UART0_init(void){




    UCSR0B|=(1<<TXEN0)|(1<<TXCIE0); //enable TX & TX IRQ
    UCSR0C=(1<<UPM01)|(1<<UPM00)|(3<<UCSZ00); //set the frame format 8ODD1, async.
    UBRR0H = (uint8_t)((UBRR_VAL0)>>8); // set the baud rate
    UBRR0L = (uint8_t)UBRR_VAL0;
	
}

void UART1_init(void){

// --------UART1 init---------------------
    //cli(); //this command should be the first one at the init section.
    UCSR1B|=(1<<RXCIE1)|(1<<RXEN1); //enable RX & RX IRQ
//    UCSR1B|=(1<<TXCIE1)|(1<<TXEN1); //enable TX & TX IRQ
    UCSR1C =(1<<UCSZ11)|(1<<UCSZ10); //set the frame format 8N1, async. 
    UBRR1H = (uint8_t)((UBRR_VAL1)>>8); // set the baud rate
    UBRR1L = (uint8_t)(UBRR_VAL1);
	//SendStr("UART1 ready", 255);

	//enable global interrupts
   //sei(); //this command should be the last one at the init section.
	// -------- end UART init --------------

}

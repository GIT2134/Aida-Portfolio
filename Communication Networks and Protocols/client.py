# 0. Import the socket and datetime module
import glob
import socket
import threading

from Client_Requests_Classes import register, unregister, update_contact, retrieve, publish, remove
import os
import pickle
from datetime import datetime
from config import BUFFER_SIZE, SERVER_ADDRESS
import threading



   

   retrieveAll() - retrieve all the information from the server
    def retrieveAll(self):
        self.printwt('Attempting retrieving all information from the server...')

        client_retrieve_all_object = retrieve.Retrieve(self.name)
        print(client_retrieve_all_object.getHeader())

        retrieve_object = pickle.dumps(client_retrieve_all_object)

        self.printwt('Sending retrieving all request to server...')
        self.sendToServer(retrieve_object, 'retrieve-all')

   
    def get_file(file_name, search_path):
        result = []
        # Walking top-down from the root
        for root, dir, files in os.walk(search_path):
            if file_name in files:
                result.append(os.path.join(root, file_name))
            else:
                print("File Not Found")
        return result

    def get_all_file(self, DATA_FOLDER="./Data"):
        """Get all files and make a list to process each files."""
        files = []
        os.chdir(DATA_FOLDER)
        for file in glob.glob("*"):
            files.append(file)
        os.chdir("..")
        return files
    # TODO 4.8 download() -

    # 4.9 updateContact()  - client can update their client information
    def updateContact(self, ip_address, udp_socket, tcp_socket):
        # must update this clients sockets also
        self.host = ip_address
        self.UDP_port = udp_socket
        self.TCP_port = tcp_socket

        # close the old sockets and create and bind the new ones and update the binding
        self.printwt('Closing old sockets and rebinding the new ones...')
        self.UDP_sock.close()
        self.TCP_sock.close()

        self.UDP_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.UDP_sock.bind((self.host, self.UDP_port))
        self.UDP_port = self.UDP_sock.getsockname()[1]
        self.printwt(f'Bound UDP client to {self.host}: {self.UDP_port}')

        self.TCP_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.TCP_sock.bind((self.host, self.TCP_port))
        self.TCP_port = self.TCP_sock.getsockname()[1]
        self.printwt(f'Bound TCP client socket {self.host}: {self.TCP_port}')

        self.printwt('Attempting to update contact with the server...')

        # Create the updateContact object
        client_update_contact_object = update_contact.UpdateContact(self.name, self.host, self.UDP_port, self.TCP_port)
        print(client_update_contact_object.getHeader())

        update_object = pickle.dumps(client_update_contact_object)

        # send the object to the server
        self.printwt('Sending update contact data to server...')
        self.sendToServer(update_object, 'update-contact')

    # 5. sendtoServer() - sends command to server and handles the reply as well, also helps with retransmission
    def sendToServer(self, command_object, requestType):
        flag = True
        trials = 5
        # Create a dedicated UDP port to send data to the server. 1 port that sends, and another that receives.
        while flag:
            # try to send the command and receive a reply from the server
            try:
                self.UDP_sock.sendto(command_object, self.SERVER_ADDRESS)
                self.printwt('Sent ' + requestType + ' request to server')
                # try to receive a reply from the server.
                msg_from_server, server_address = self.UDP_sock.recvfrom(self.BUFFER_SIZE)
                self.printwt(f'Received {requestType} reply from server : {server_address}')
                self.printwt(msg_from_server.decode())
                # if we received a reply, set the flag to false, so we don't try again
                flag = False

                # once we sent the request, remove from the amount of trials if reply not received.
                trials -= 1
                if trials == 0:
                    # if we exceeded the amount of trials we exit
                    flag = False
                    self.printwt('Attempted to send ' + requestType + ' request to server and failed 5 times')
                    break
            except socket.error:
                # if sending failed
                self.printwt('Failed to send ' + requestType + ' request to server')

            # try to receive a reply from the server.
            try:
                msg_from_server, server_address = self.UDP_sock.recvfrom(self.BUFFER_SIZE)
                self.printwt(f'Received {requestType} reply from server : {server_address}')
                self.printwt(msg_from_server.decode())
                # if we received a reply, set the flag to false, so we don't try again
                flag = False
            except socket.timeout:
                self.printwt(
                    'Failed to receive ' + requestType + ' reply from server attempting ' + str(trials) + ' more times')

    def close_sockets(self):
        self.printwt('Closing sockets...')
        self.UDP_sock.close()
        self.TCP_sock.close()
        self.printwt('Sockets closed')

    def handle_commands(self, client, query):
        if query == '?' or query == 'help':
            print('<register> <unregister> <publish> <updateContact>')
        elif query == 'register':
            client.register()
        elif query == 'unregister':
            client.unregister()
        elif query == 'publish':
            client.publish()
        elif query == 'updateContact':
            newip = input('enter new ip address: ')
            pass

def main():
    # TODO Implement dynamic threaded user commands.

    query = input('> Enter Client Name: ')
    client = Client(query, socket.gethostbyname(socket.gethostname()), 0, 0)
    client.configure_client()
    try:
        print('type [help] or [?] for a list of commands at any time.')
        print('type [exit] to exit, or terminate the program')
        while query != 'exit':
            query = input('> ')

            client_thread = threading.Thread(target=client.handle_commands, args=(client, query))
            client_thread.start()
            client_thread.join()

    except KeyboardInterrupt:
        client.close_sockets()
        

if __name__ == '__main__':
    main()

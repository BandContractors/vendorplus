package utilities;

import java.io.*;
import javax.faces.bean.*;
import jssc.*;

@ManagedBean
@SessionScoped
public class ComPortSendReceive implements Serializable, SerialPortEventListener {

    private static final long serialVersionUID = 1L;
    private static SerialPort serialPort;

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        String[] portNames = SerialPortList.getPortNames();
//
//        if (portNames.length == 0) {
//            System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
//            System.out.println("Press Enter to exit...");
//            try {
//                System.in.read();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return;
//        }
//
//        // выбор порта
//        System.out.println("Available com-ports:");
//        for (int i = 0; i < portNames.length; i++) {
//            System.out.println(portNames[i]);
//        }
//        System.out.println("Type port name, which you want to use, and press Enter...");
//        Scanner in = new Scanner(System.in);
//        String portName = in.next();
//
//        // writing to port
//        serialPort = new SerialPort(portName);
//        try {
//            // opening port
//            serialPort.openPort();
//
//            serialPort.setParams(SerialPort.BAUDRATE_9600,
//                    SerialPort.DATABITS_8,
//                    SerialPort.STOPBITS_1,
//                    SerialPort.PARITY_NONE);
//
//            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
//                    | SerialPort.FLOWCONTROL_RTSCTS_OUT);
//
//            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
//            // writing string to port
//            serialPort.writeString("Hurrah!!!");
//
//            System.out.println("String wrote to port, waiting for response..");
//        } catch (SerialPortException ex) {
//            System.out.println("Error in writing data to port: " + ex);
//        }
//    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // receiving response from port
    private static class PortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    // получение ответа от порта
                    String receivedData = serialPort.readString(event.getEventValue());
                    System.out.println("Received response from port: " + receivedData);
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving response from port: " + ex);
                }
            }
        }
    }
}

package utils;

import java.io.OutputStream;
import java.io.IOException;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

import java.util.Enumeration;

public class SerialOverUSB {
	SerialPort serialPort;

	private static final String PORT_NAMES[] = { "/dev/tty.usbmodem1411", "/dev/tty.usbserial-A800eu63", "/dev/tty.usbserial-A700e1l2", "COM7" };

	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			System.out.println("Try " + currPortId.getName());
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find a valid port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			System.out.println("Ty to connect to port " + portId.getName());
			serialPort = (SerialPort) portId.open("Pirates", TIME_OUT);
			
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			System.out.println("ASSIGN");
			output = serialPort.getOutputStream();
		} catch (Exception e) {
			System.out.println("ERROR");
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public void send(byte c) throws IOException {
		if (this.output != null) {
			System.out.println("Send " + c);
			this.output.write(c);
			this.output.flush();
		}
	}
}

package CabinetMock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sumey
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;

public class CabinetMock extends Thread {
	private ServerSocket serverSocket;
	private int port;
	private boolean running = false;
	private static int logCounter = -1;

	public CabinetMock(int port) {
		this.port = port;
	}
      
	public void startServer() {
		try {
			serverSocket = new ServerSocket(port);
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		running = false;
		this.interrupt();
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			try {
				CabinetMock.log("Listening for a connection");

				// Call accept() to receive the next connection
				Socket socket = serverSocket.accept();

				// Pass the socket to the RequestHandler thread for processing
				RequestHandler requestHandler = new RequestHandler(socket);
				requestHandler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static void log(String entry) {
		CabinetMock.logCounter++;
		System.out.println(CabinetMock.logCounter + "\t" + entry + "\t[Size of Message: " + entry.length() + "]");
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			CabinetMock.log("Usage: CabinetMock <port>");
			throw new Exception("Usage: CabinetMock <port>");
		}
		int port = Integer.parseInt(args[0]);
		CabinetMock.log("Start server on port: " + port);

		CabinetMock server = new CabinetMock(port);
		server.startServer();

		try {
			Thread.sleep(60000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		server.stopServer();
		CabinetMock.log("Server stopped");
		System.exit(0);
	}
}

class RequestHandler extends Thread {
	private Socket socket;
	private String station;
	private String user;
	private int failureRate;
	private int failureRatePreTest;
	private String role;
	private String[] slots;
	private MessageType awaited;
	private boolean initPhase;
	private Hashtable<String, Integer> examinees;
	private int preTestCnt;
	private Random random;
	private int maxMinsWait;
	private float actTemp;
	private float targetTemp;
	private int targetTime;
	private float toleranceRate;
	private boolean targetTempSet;
	private boolean targetTempReached;
	private long tempSetTime;
	private float tempChangeSpeed;
	
	RequestHandler(Socket socket) {
		this.socket = socket;
		this.slots = new String[20];
		for (int i = 0; i < 20; i++) {
			this.slots[i] = null;
		}
		this.awaited = MessageType.STRT;
		this.initPhase = false;
		this.examinees = new Hashtable<>();
	}

	@Override
	public void run() {
		try {
			CabinetMock.log("Received a connection");

			// Get input and output streams
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
			// Write out our header to the client
			CabinetMock.log("Cabinet started. Waiting client to connect...");

			// Echo lines back to the client until the client closes the connection or we
			// receive an empty line
			String line;
			boolean cont = true;
			do {
				line = fromClient.readLine();
				if (line != null) {
					cont = this.processMessage(line, toClient);
				}
			} while (cont);

			// Close our connection
			fromClient.close();
			toClient.close();
			socket.close();
			CabinetMock.log("Connection closed");
			CabinetMock.log("Server exiting with status <<0>>");
			System.exit(0);
		} catch (Exception e) {
			CabinetMock.log("Communication to CabinetControl lost. Waiting for new connection....");
		}
	}

	private boolean processMessage(String line, PrintWriter toClient) throws InterruptedException {
		boolean retVal = true;
		if (line.length() <= 0)
			return false;

		CabinetMock.log("Message from client received: " + line);
		StringTokenizer tokenizer = new StringTokenizer(line, "|");
		String msgIdentifier = tokenizer.nextToken();
		switch (MessageType.getType(msgIdentifier)) {
		case STRT: {
			if (this.awaited != MessageType.STRT) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			CabinetMock.log("SENDING - Cabinet will startup soon...");
			toClient.println(this.processStartMessage(tokenizer));
			this.awaited = MessageType.INIT;
			this.actTemp = Float.MIN_VALUE;
			this.targetTempSet = false;
			this.targetTempReached = false;
			break;
		}
		case INIT: {
			if (this.awaited != MessageType.INIT) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			if (!this.initPhase) {
				CabinetMock.log("SENDING - Initialisation of examinees started...");
				this.initPhase = true;
				Thread.sleep(500);
			}
			String answer = this.processInitMessage(tokenizer);
			CabinetMock.log("SENDING - " + answer);
			toClient.println(answer);
			break;
		}
		case ENDINIT: {
			if (this.awaited != MessageType.INIT) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			String answer = this.processEndInitMessage();
			CabinetMock.log("SENDING - " + answer);
			toClient.println(answer);
			this.awaited = MessageType.STRTPRE;
			break;
		}
		case STRTPRE: {
			if (this.awaited != MessageType.STRTPRE) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			CabinetMock.log("Starting PRETEST");
			toClient.println("Starting PRETEST");
			this.failureRatePreTest = Integer.parseInt(tokenizer.nextToken());
			this.preTestCnt = 0;
			this.random = new Random();
			this.awaited = MessageType.PRETST;
			break;
		}
		case PRETST: {
			if (this.awaited != MessageType.PRETST) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			if (this.failureRatePreTest == 0) {
				CabinetMock.log("No failure allowed");
			}
			else if (this.failureRatePreTest >= 100) {
				CabinetMock.log("All must fail");
				Thread.sleep(this.maxMinsWait + 10);
			}
			else {
				if (!((this.preTestCnt % this.failureRatePreTest) == 0)) {
					Thread.sleep(this.random.nextInt(this.maxMinsWait + 1));
				}
			}
			int slotNumber = Integer.parseInt(tokenizer.nextToken()) - 1;
			if (this.slots[slotNumber] != null) {
				CabinetMock.log("SENDING - Slot[" + (slotNumber + 1) + "] OK");
				toClient.println("Slot[" + (slotNumber + 1) + "] OK");
			}
			else {
				CabinetMock.log("SENDING - Slot[" + (slotNumber + 1) + "] NOK - No or faulty examinee in slot <<<" + (slotNumber + 1) + ">>>");
				toClient.println("Slot[" + (slotNumber + 1) + "] NOK - No or faulty examinee in slot <<<" + (slotNumber + 1) + ">>>");
			}
			this.preTestCnt++;
			this.awaited = MessageType.PRETST;
			break;
		}
		case ENDPRE: {
			CabinetMock.log("SENDING - Ready to start Burn-In Test");
			toClient.println("Ready to start Burn-In Test");
			this.awaited = MessageType.STRTBURNIN;
			break;
		}
		case STRTBURNIN: {
			if (this.awaited != MessageType.STRTBURNIN) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
			}
			this.getActualTemp();
			CabinetMock.log("SENDING - Starting BURN-IN Test with actual room temperature of <<<"+this.actTemp+">>>");
			toClient.println("Starting BURN-IN Test with actual room temperature of <<<"+this.actTemp+">>>");
			this.awaited = MessageType.BURNIN;
			break;
		}
		case OPERTEMP: {
			if (this.targetTempSet && !this.targetTempReached) {
				long actTime = System.currentTimeMillis();
				long elapsedTime = actTime - this.tempSetTime;
				this.tempSetTime = actTime;
				float tempChange = elapsedTime * this.tempChangeSpeed;
				this.actTemp += tempChange;
				if(this.actTemp >= 0) {
					if (this.actTemp >= (this.targetTemp-this.targetTemp*this.toleranceRate) && this.actTemp <= (this.targetTemp + this.targetTemp*this.toleranceRate)) {
						this.targetTempReached = true;
					}
				}
				else {
					if (this.actTemp <= (this.targetTemp-this.targetTemp*this.toleranceRate) && this.actTemp >= (this.targetTemp + this.targetTemp*this.toleranceRate)) {
						this.targetTempReached = true;
					}
				}
				if (targetTempReached) {
					this.awaited = MessageType.STRTPING;
				}
			}
			else {
				this.getActualTemp();
			}
			CabinetMock.log("SENDING - OPERTEMP-RESP:"+this.actTemp);
			toClient.println("OPERTEMP-RESP:"+this.actTemp);
			break;
		}
		case SETTARGET: {
//			messages.add("SETTARGET|70.5|180|3|5"); //Target Temperature | TimeFrame[secs] | ToleranceRate[%]

			this.setTargetTemp((String)tokenizer.nextElement(), (String)tokenizer.nextElement(), (String)tokenizer.nextElement(), (String)tokenizer.nextElement());
			CabinetMock.log("SENDING - SETTARGET-RESP:"+this.actTemp);
			toClient.println("SETTARGET-RESP:"+this.actTemp);
			this.targetTempSet = true;
			this.targetTempReached = false;
			this.tempSetTime = System.currentTimeMillis();
			this.tempChangeSpeed = (this.targetTemp - this.actTemp) / (this.targetTime*1000);
			break;
		}
		case STRTPING: {
			if (this.awaited != MessageType.STRTPING) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			CabinetMock.log("SENDING - Starting STRTPING");
			toClient.println("Starting STRTPING");
			this.failureRatePreTest = Integer.parseInt(tokenizer.nextToken());
			this.preTestCnt = 0;
			this.random = new Random();
			this.awaited = MessageType.PING;
			break;
		}
		case PING: {
			if (this.awaited != MessageType.PING) {
				CabinetMock.log("SENDING - Wrong ordered message arrived. Ignoring and waiting for the next one...");
				toClient.println("Wrong ordered message arrived. Ignoring and waiting for the next one...");
				break;
			}
			if (this.failureRatePreTest == 0) {
				CabinetMock.log("No failure allowed");
			}
			else if (this.failureRatePreTest >= 100) {
				CabinetMock.log("All must fail");
				Thread.sleep(this.maxMinsWait + 10);
			}
			else {
				if (!((this.preTestCnt % this.failureRatePreTest) == 0)) {
					Thread.sleep(this.random.nextInt(this.maxMinsWait + 1));
				}
			}
			int slotNumber = Integer.parseInt(tokenizer.nextToken()) - 1;
			if (this.slots[slotNumber] != null) {
				CabinetMock.log("SENDING - Slot[" + (slotNumber + 1) + "] OK");
				toClient.println("Slot[" + (slotNumber + 1) + "] OK");
			}
			else {
				CabinetMock.log("SENDING - Slot[" + (slotNumber + 1) + "] NOK - No or faulty examinee in slot <<<" + (slotNumber + 1) + ">>>");
				toClient.println("Slot[" + (slotNumber + 1) + "] NOK - No or faulty examinee in slot <<<" + (slotNumber + 1) + ">>>");
			}
			this.preTestCnt++;
			break;
		}
		case STOPPING: {
			CabinetMock.log("SENDING -Ready for the next test step");
			toClient.println("Ready for the next test step");
			this.awaited = MessageType.SETTARGET;
			break;
		}
		case STOP: {
			CabinetMock.log("SENDING - STOPPING|Cabinet will shutdown in a few moments. Goodbye <<<" + this.user
					+ ">>>. Hope to see you again!");
			toClient.println("STOPPING - Cabinet will shutdown in a few moments. Goodbye <<<" + this.user
					+ ">>>. Hope to see you again!");
			retVal = false;
			break;
		}
		default:
			CabinetMock.log("SENDING - Unrecognized message type received. Ignoring and waiting for the next one...");
			toClient.println("Unrecognized message type received. Ignoring and waiting for the next one...");
		}
		return retVal;
	}

	private void getActualTemp() {
		if (this.actTemp == Float.MIN_VALUE) {
			int temp = new Random().ints(12, 26).limit(3).toArray()[2];
			this.actTemp = temp + new Random().nextFloat();
		}
	}

	private void setTargetTemp(String targetTemp, String targetTime, String tolerance, String tempFailureRate) {
		this.targetTemp = Float.parseFloat(targetTemp);
		this.targetTime = Integer.parseInt(targetTime);
		this.toleranceRate = Float.parseFloat(tolerance)/100;
//		this.tempFailureRate = Float.parseFloat(tempFailureRate);
	}

	private String processEndInitMessage() {
		// Expected message: Empty message
		String retVal = "";
		for (int i = 0; i < 20; i++) {
			if (this.slots[i] == null) {
				retVal += "\tSLOT-" + (i + 1);
			} else {
				retVal += "\tSLOT-" + (i + 1) + "\t: " + this.slots[i];
			}
		}
		return retVal;
	}

	private String processInitMessage(StringTokenizer tokenizer) {
		// Expected message: "1|1545678"; // SlotNumber | Examinee-ID
		int slot = Integer.parseInt(tokenizer.nextToken());
		slot--;
		String examinee = tokenizer.nextToken();
		if (slot < 0 || slot >= 20) {
			return "Wrong slot number sent. Ignoring and waiting for the next one...";
		}
		if (this.slots[slot] != null) {
			return "Cannot register >>" + examinee + ">>. Slot <<" + (slot + 1)
					+ ">> occupied. Ignoring and waiting for the next one...";
		}
		Integer temp = this.examinees.get(examinee);
		if (temp == null) {
			this.examinees.put(examinee, slot);
		} else {
			return "Cannot register >>" + examinee + ">> more than once. It is already registered in slot <<"
					+ (slot + 1) + ">>. Ignoring and waiting for the next one...";
		}
		this.slots[slot] = examinee;
		return "Examinee <<" + examinee + ">>> is registered in slot <<" + (slot + 1) + ">>";
	}

	private String processStartMessage(StringTokenizer tokenizer) {
		// Expected message: "CabinetControl1|Armin Müllner|Admin|10";
		String retVal = "Cabinet is connected to <<";
		this.station = tokenizer.nextToken();
		retVal += this.station + ">> operated by <<";
		this.user = tokenizer.nextToken();
		retVal += this.user + ">> with <<";
		this.role = tokenizer.nextToken();
		retVal += this.role + ">> privileges. The simulation will work with <<";
		this.failureRate = Integer.parseInt(tokenizer.nextToken());
		retVal += this.failureRate + "%>> failure rate and <<<";
		this.maxMinsWait = Integer.parseInt(tokenizer.nextToken());
		retVal += this.maxMinsWait + "[ms] ping time>>";
		retVal += " - Waiting for initialisation of examinees...";
		return retVal;
	}
}
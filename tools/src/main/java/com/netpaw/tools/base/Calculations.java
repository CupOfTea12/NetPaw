package com.netpaw.tools.base;
import java.util.Scanner;

import com.netpaw.tools.base.IpAdressCalculator;

public class Calculations {

	private IpAdressCalculator ipAdressCalc = IpAdressCalculator.getInstance();
    Scanner scan = new Scanner(System.in);

	public void runCalculations() {
        System.out.println("Enter IP address in decimal numbers (e.g., 192.168.0.1): ");
        String ipAddress = scan.nextLine();

        String binaryIp = ipAdressCalc.decimalToBinary(ipAddress);
        System.out.println("IP address in binary: " + binaryIp);

        System.out.println("Do you want to calculate the subnet and broadcast addresses? (yes/no)");
        String response = scan.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter subnet mask in decimal numbers (e.g., 255.255.255.0): ");
            String subnetMask = scan.nextLine();

            String networkAddress = ipAdressCalc.calculateNetworkAdress(ipAddress, subnetMask);
            String broadcastAddress = ipAdressCalc.calculateBroadCastAdress(ipAddress, subnetMask);

            System.out.println("Network Address: " + networkAddress);
            System.out.println("Broadcast Address: " + broadcastAddress);
        }
		
	}
	
	public static void main(String[] args) {
		Calculations calculate = new Calculations();
		calculate.runCalculations();
	}
}

package com.netpaw.tools.base;

import java.util.Scanner;

public class IpAdressCalculator {

	private static IpAdressCalculator instance;
    Scanner scan = new Scanner(System.in);
    
    /*
     * return instance of IpAdressCalculator
     */
    public static IpAdressCalculator getInstance() {
        if (instance == null) {
            instance = new IpAdressCalculator();
        }
        return instance;
    }
    
    /**
     * converts decimal numbers to binary
     */
    public String decimalToBinary(String ipAdress) {
		String[] octets = ipAdress.split("\\.");
		
		if(octets.length !=4) {
			return "Invalid IP adress.";
		}
    	
		StringBuilder binaryIp = new StringBuilder();
		
		for (String octet : octets) {
			int decimal = Integer.parseInt(octet);
			String binaryOctet = Integer.toBinaryString(decimal);
			while(binaryOctet.length() < 8) {
			  binaryOctet = "0" + binaryOctet;
		  }
			binaryIp.append(binaryOctet).append(".");
			
		}
		
    	binaryIp.setLength(binaryIp.length() - 1);
    	return binaryIp.toString();
    	
    }
    /**
     * calculates the network address
     */
    public String calculateNetworkAdress(String ipAddress, String subnetMask) {
    	String[] ipOctets = ipAddress.split("\\.");
    	String[] maskOctets = subnetMask.split("\\.");
    	
    	if(ipOctets.length != 4|| maskOctets.length !=4  ) {
    		return "Invalid input";
    	}
    	
    	StringBuilder networkAddress = new StringBuilder();
    	
    	for(int i =0; i<4; i++) {
    		int ipOctet = Integer.parseInt(ipOctets[i]);
    		int maskOctet = Integer.parseInt(maskOctets[i]);
    		int networkOctet = ipOctet & maskOctet;
            networkAddress.append(networkOctet).append(".");
    		
    	}
    	networkAddress.setLength(networkAddress.length() -1);
    	return networkAddress.toString();
    }
    
    /**
     * Calculates the broadcast address
     */
    public String calculateBroadCastAdress(String ipAddress, String subnetMask ) {
    	String[] ipOctets = ipAddress.split("\\.");
    	String[] maskOctets = subnetMask.split("\\.");
    	
    	if(ipOctets.length != 4|| maskOctets.length !=4  ) {
    		return "Invalid input";
    	}
    	
    	StringBuilder broadcastAdress = new StringBuilder();
    	
    	for(int i =0; i<4; i++) {
    		int ipOctet = Integer.parseInt(ipOctets[i]);
    		int maskOctet = Integer.parseInt(maskOctets[i]);
    		int invertedMaskOctet = ~maskOctet & 0xFF;
            int broadcastOctet = ipOctet | invertedMaskOctet;
            broadcastAdress.append(broadcastOctet).append(".");
            
    	}
    	
    	broadcastAdress.setLength(broadcastAdress.length()-1);	
    	return broadcastAdress.toString(); 
    }

}

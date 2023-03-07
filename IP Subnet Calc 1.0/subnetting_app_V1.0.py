import tkinter as tk
from tkinter import messagebox

class SubnettingApp:
    def __init__(self, master):
        self.master = master
        master.title("Subnetting App")

        # Create input fields
        self.ip_label = tk.Label(master, text="IP Address:")
        self.ip_label.grid(row=0, column=0)
        self.ip_entry = tk.Entry(master)
        self.ip_entry.grid(row=0, column=1)

        self.subnet_mask_label = tk.Label(master, text="Subnet Mask:")
        self.subnet_mask_label.grid(row=1, column=0)
        self.subnet_mask_entry = tk.Entry(master)
        self.subnet_mask_entry.grid(row=1, column=1)

        # Create Calculate button
        self.calculate_button = tk.Button(master, text="Calculate", command=self.calculate_subnet)
        self.calculate_button.grid(row=2, column=0, columnspan=2)

        # Create Result label
        self.result_label = tk.Label(master, text="")
        self.result_label.grid(row=3, column=0, columnspan=2)

    def calculate_subnet(self):
        # Get input values
        ip_address = self.ip_entry.get()
        subnet_mask = self.subnet_mask_entry.get()

        # Perform subnetting calculation
        try:
            # Convert IP address to binary
            ip_binary = ''.join([bin(int(x)+256)[3:] for x in ip_address.split('.')])

            # Convert subnet mask to binary
            subnet_mask_binary = ''.join([bin(int(x)+256)[3:] for x in subnet_mask.split('.')])

            # Calculate network and broadcast addresses
            network_address_binary = ''.join([str(int(ip_binary[i]) & int(subnet_mask_binary[i])) for i in range(32)])
            broadcast_address_binary = ''.join([str(int(ip_binary[i]) | (int(subnet_mask_binary[i]) ^ 1)) for i in range(32)])

            # Convert network and broadcast addresses to dotted decimal notation
            network_address = '.'.join([str(int(network_address_binary[i:i+8], 2)) for i in range(0, 32, 8)])
            broadcast_address = '.'.join([str(int(broadcast_address_binary[i:i+8], 2)) for i in range(0, 32, 8)])

            # Display results
            self.result_label.config(text=f"Network Address: {network_address}\nBroadcast Address: {broadcast_address}")
        except ValueError:
            messagebox.showerror("Error", "Invalid IP address or subnet mask")

# Create GUI instance
root = tk.Tk()
app = SubnettingApp(root)

# Run GUI
root.mainloop()

import tkinter as tk
import requests

class IpRequestApp:
    def __init__(self, master):
        self.master = master
        master.title("IP Tools - Requesting")

        # Create input field
        self.ip_label = tk.Label(master, text="Enter IP Address:")
        self.ip_label.grid(row=0, column=0)
        self.ip_entry = tk.Entry(master)
        self.ip_entry.grid(row=0, column=1)

        # Create Request button
        self.request_button = tk.Button(master, text="Request", command=self.request_ip)
        self.request_button.grid(row=1, column=0, columnspan=2)

        # Create Result label
        self.result_label = tk.Label(master, text="")
        self.result_label.grid(row=2, column=0, columnspan=2)

    def request_ip(self):
        # Get input value
        ip_address = self.ip_entry.get()

        # Send request to ipify API
        try:
            response = requests.get(f"https://api.ipify.org/?format=json&ipAddress={ip_address}")
            data = response.json()

            # Display IP address
            self.result_label.config(text=f"Your IP address is: {data['ip']}")
        except requests.exceptions.RequestException as e:
            self.result_label.config(text=f"Error: {e}")

# Create GUI instance
root = tk.Tk()
app = IpRequestApp(root)

# Run GUI
root.mainloop()

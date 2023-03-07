import tkinter as tk
import socket

# Define the function to perform the reverse DNS lookup
def reverse_dns_lookup():
    # Get the IP address from the entry field
    ip_address = ip_entry.get()

    # Perform the reverse DNS lookup
    try:
        hostname = socket.gethostbyaddr(ip_address)[0]
        result_label.config(text=f"Hostname: {hostname}")
    except socket.herror:
        result_label.config(text="No hostname found")

# Create the GUI window
root = tk.Tk()
root.title("Reverse DNS Lookup")

# Create the label and entry field for the IP address
ip_label = tk.Label(root, text="IP Address:")
ip_label.pack(side=tk.LEFT)
ip_entry = tk.Entry(root)
ip_entry.pack(side=tk.LEFT)

# Create the button to perform the reverse DNS lookup
lookup_button = tk.Button(root, text="Lookup", command=reverse_dns_lookup)
lookup_button.pack(side=tk.LEFT)

# Create the label to display the result
result_label = tk.Label(root, text="")
result_label.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

import tkinter as tk
from ipwhois import IPWhois

# Define the function to retrieve IP tracking information
def track_ip():
    # Get the IP address from the entry field
    ip_address = ip_entry.get()

    # Retrieve the IP tracking information
    ip = IPWhois(ip_address)
    results = ip.lookup_rdap(depth=1)

    # Format the results as a string
    result_str = ""
    for key, value in results.items():
        result_str += f"{key}: {value}\n"

    # Display the results in the text box
    result_box.delete(1.0, tk.END)
    result_box.insert(tk.END, result_str)

# Create the GUI window
root = tk.Tk()
root.title("IP Tracker")

# Create the label and entry field for the IP address
ip_label = tk.Label(root, text="IP Address:")
ip_label.pack(side=tk.LEFT)
ip_entry = tk.Entry(root)
ip_entry.pack(side=tk.LEFT)

# Create the button to initiate the IP tracking request
track_button = tk.Button(root, text="Track IP", command=track_ip)
track_button.pack(side=tk.LEFT)

# Create the text box to display the IP tracking results
result_box = tk.Text(root)
result_box.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

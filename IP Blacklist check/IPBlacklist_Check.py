import tkinter as tk
import requests

# Define the function to check if the IP address is blacklisted
def check_blacklist():
    # Get the IP address from the entry field
    ip_address = ip_entry.get()

    # Send a GET request to the blacklists API with the IP address as a parameter
    url = f'https://api.ipvoid.com/ip-blacklist-check/v1/{ip_address}'
    response = requests.get(url)

    # Extract the response data as a dictionary
    data = response.json()

    # Display the blacklist status in the label
    if data['blacklists']:
        status_label.config(text=f"Blacklisted on {len(data['blacklists'])} lists!")
    else:
        status_label.config(text="Not blacklisted")

# Create the GUI window
root = tk.Tk()
root.title("IP Blacklist Check")

# Create the label and entry field for the IP address
ip_label = tk.Label(root, text="IP Address:")
ip_label.pack(side=tk.LEFT)
ip_entry = tk.Entry(root)
ip_entry.pack(side=tk.LEFT)

# Create the button to check the IP address against blacklists
check_button = tk.Button(root, text="Check", command=check_blacklist)
check_button.pack(side=tk.LEFT)

# Create the label to display the blacklist status
status_label = tk.Label(root, text="")
status_label.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

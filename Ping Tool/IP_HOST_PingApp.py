import tkinter as tk
import subprocess

# Define the function to execute the ping command
def ping(host):
    # Execute the ping command
    process = subprocess.Popen(["ping", host], stdout=subprocess.PIPE)
    output = process.communicate()[0]

    # Decode the output and return it
    return output.decode("utf-8")

# Define the function to handle the ping request
def ping_host():
    # Get the host name or IP address from the entry field
    host = host_entry.get()

    # Send the ping request and get the result
    result = ping(host)

    # Display the result in the text box
    result_box.delete(1.0, tk.END)
    result_box.insert(tk.END, result)

# Create the GUI window
root = tk.Tk()
root.title("Ping Tool")

# Create the label and entry field for the host
host_label = tk.Label(root, text="Host:")
host_label.pack(side=tk.LEFT)
host_entry = tk.Entry(root)
host_entry.pack(side=tk.LEFT)

# Create the button to initiate the ping request
ping_button = tk.Button(root, text="Ping", command=ping_host)
ping_button.pack(side=tk.LEFT)

# Create the text box to display the ping results
result_box = tk.Text(root)
result_box.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

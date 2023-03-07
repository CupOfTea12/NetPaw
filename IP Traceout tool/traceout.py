import tkinter as tk
import subprocess

# Define the function to execute the traceroute command
def traceroute(host):
    # Execute the traceroute command
    process = subprocess.Popen(["traceroute", host], stdout=subprocess.PIPE)
    output = process.communicate()[0]

    # Decode the output and return it
    return output.decode("utf-8")

# Define the function to handle the traceroute request
def trace_route():
    # Get the host name or IP address from the entry field
    host = host_entry.get()

    # Send the traceroute request and get the result
    result = traceroute(host)

    # Display the result in the text box
    result_box.delete(1.0, tk.END)
    result_box.insert(tk.END, result)

# Create the GUI window
root = tk.Tk()
root.title("Traceroute Tool")

# Create the label and entry field for the host
host_label = tk.Label(root, text="Host:")
host_label.pack(side=tk.LEFT)
host_entry = tk.Entry(root)
host_entry.pack(side=tk.LEFT)

# Create the button to initiate the traceroute request
trace_button = tk.Button(root, text="Trace Route", command=trace_route)
trace_button.pack(side=tk.LEFT)

# Create the text box to display the traceroute results
result_box = tk.Text(root)
result_box.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

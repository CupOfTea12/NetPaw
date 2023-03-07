import tkinter as tk
import requests
from bs4 import BeautifulSoup

# Define the function to check the proxy
def check_proxy():
    # Get the proxy from the entry field
    proxy = proxy_entry.get()

    # Send a GET request to the IPVoid proxy checker with the proxy as a parameter
    url = f'https://www.ipvoid.com/proxy-check/{proxy}/'
    response = requests.get(url)

    # Extract the response data using BeautifulSoup
    soup = BeautifulSoup(response.text, 'html.parser')
    result = soup.find_all('div', {'class': 'panel-body'})[1].text.strip()

    # Display the result in the label
    result_label.config(text=result)

# Create the GUI window
root = tk.Tk()
root.title("Advanced Proxy Check")

# Create the label and entry field for the proxy
proxy_label = tk.Label(root, text="Proxy:")
proxy_label.pack(side=tk.LEFT)
proxy_entry = tk.Entry(root)
proxy_entry.pack(side=tk.LEFT)

# Create the button to check the proxy
check_button = tk.Button(root, text="Check", command=check_proxy)
check_button.pack(side=tk.LEFT)

# Create the label to display the result
result_label = tk.Label(root, text="")
result_label.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

# ADVANCED PROXY CHECK
- When you run this code, it will create a GUI window with a label and an entry field for the proxy, a button to check the proxy, and a label to display the result.
To use this app, enter a proxy in the entry field (in the format IP:Port) and click the "Check" button. The app will send a GET request to the IPVoid proxy checker to check the proxy. The app will then extract the result from the HTML response using BeautifulSoup and display it in the label.
The result will include information about the proxy type, anonymity level, location, and whether or not it is blacklisted.

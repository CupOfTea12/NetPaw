import tkinter as tk
import requests

# Define the function to perform the breach check
def breach_check():
    # Get the email address from the entry field
    email = email_entry.get()

    # Send a GET request to the Have I Been Pwned API with the email as a parameter
    url = f'https://haveibeenpwned.com/api/v3/breachedaccount/{email}'
    headers = {'User-Agent': 'Breach Check GUI App'}
    response = requests.get(url, headers=headers)

    # Check the response status code to see if the email has been breached
    if response.status_code == 200:
        # If the email has been breached, extract the breach information from the JSON response
        breaches = response.json()
        breach_info = '\n\n'.join([f'{breach["Name"]}\n{breach["Description"]}\n{breach["BreachDate"]}\n{breach["DataClasses"]}' for breach in breaches])
        result_label.config(text=f"Your email address has been breached in the following:\n\n{breach_info}")
    elif response.status_code == 404:
        # If the email has not been breached, display a message saying so
        result_label.config(text="Your email address has not been breached.")
    else:
        # If there is an error with the request, display an error message
        result_label.config(text="An error occurred while checking your email address. Please try again later.")

# Create the GUI window
root = tk.Tk()
root.title("Breach Check")

# Create the label and entry field for the email address
email_label = tk.Label(root, text="Email Address:")
email_label.pack(side=tk.LEFT)
email_entry = tk.Entry(root)
email_entry.pack(side=tk.LEFT)

# Create the button to perform the breach check
check_button = tk.Button(root, text="Check", command=breach_check)
check_button.pack(side=tk.LEFT)

# Create the label to display the result
result_label = tk.Label(root, text="")
result_label.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()

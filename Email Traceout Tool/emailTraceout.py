import tkinter as tk
import smtplib
import dns.resolver
import email.header
import email.utils

# Define the function to retrieve email headers
def get_email_headers():
    # Get the email address from the entry field
    email_address = email_entry.get()

    # Open a connection to the email server and retrieve the email headers
    with smtplib.SMTP('localhost') as smtp:
        headers = smtp.sendmail(email_address, email_address, '')

    # Decode the headers and return them
    decoded_headers = {}
    for key, value in headers.items():
        decoded_headers[key.decode('utf-8')] = value.decode('utf-8')

    return decoded_headers

# Define the function to resolve DNS records
def resolve_dns():
    # Get the email address from the entry field
    email_address = email_entry.get()

    # Get the domain name from the email address
    domain_name = email_address.split('@')[-1]

    # Retrieve the DNS records for the domain name
    records = []
    for record_type in ['A', 'MX', 'NS', 'TXT']:
        try:
            record = dns.resolver.query(domain_name, record_type)
            records.append(f"{record_type}: {', '.join([str(rdata) for rdata in record])}")
        except dns.resolver.NoAnswer:
            records.append(f"{record_type}: No records found")
        except dns.resolver.NXDOMAIN:
            records.append(f"{record_type}: Domain does not exist")

    # Return the DNS records
    return '\n'.join(records)

# Define the function to handle the email tracing request
def trace_email():
    # Get the email headers and display them in the text box
    headers = get_email_headers()
    headers_str = ""
    for key, value in headers.items():
        headers_str += f"{key}: {value}\n"
    headers_box.delete(1.0, tk.END)
    headers_box.insert(tk.END, headers_str)

    # Resolve the DNS records for the email domain and display them in the text box
    dns_records = resolve_dns()
    dns_box.delete(1.0, tk.END)
    dns_box.insert(tk.END, dns_records)

# Create the GUI window
root = tk.Tk()
root.title("Trace Email Analyzer")

# Create the label and entry field for the email address
email_label = tk.Label(root, text="Email Address:")
email_label.pack(side=tk.LEFT)
email_entry = tk.Entry(root)
email_entry.pack(side=tk.LEFT)

# Create the button to initiate the email tracing request
trace_button = tk.Button(root, text="Trace Email", command=trace_email)
trace_button.pack(side=tk.LEFT)

# Create the text box to display the email headers
headers_box = tk.Text(root)
headers_box.pack(side=tk.LEFT)

# Create the text box to display the DNS records
dns_box = tk.Text(root)
dns_box.pack(side=tk.BOTTOM)

# Start the GUI event loop
root.mainloop()
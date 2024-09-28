import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Customer {
    String accountId;
    String name;
    String address;
    String dob;
    String phoneNumber;
    double accountBalance;
    String accountType;
    double fixedDailyInterest;

    public Customer(String accountId, String name, String address, String dob, String phoneNumber,
                    double accountBalance, String accountType, double fixedDailyInterest) {
        this.accountId = accountId;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
        this.fixedDailyInterest = fixedDailyInterest;
    }

    @Override
    public String toString() {
        return "Account Id = " + accountId + "\n" +
               "Name = " + name + "\n" +
               "Address = " + address + "\n" +
               "DOB = " + dob + "\n" +
               "Phone Number = " + phoneNumber + "\n" +
               "Account Balance = " + accountBalance + "\n" +
               "Account Type = " + accountType + "\n" +
               "Fixed Daily Interest = " + fixedDailyInterest + "\n";
    }
}

class Node {
    Customer data;
    Node prev;
    Node next;

    public Node(Customer data) {
        this.data = data;
    }
}

class DoublyLinkedList {
    private Node head;
    private Node tail;

    public void addCustomer(Customer customer) {
        Node newNode = new Node(customer);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void displayCustomers() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public Customer findCustomerById(String id) {
        Node current = head;
        while (current != null) {
            if (current.data.accountId.equals(id)) {
                return current.data;
            }
            current = current.next;
        }
        return null; // Not found
    }

    public void deleteCustomerById(String id) {
        Node current = head;
        while (current != null) {
            if (current.data.accountId.equals(id)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next; // Deleting head
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev; // Deleting tail
                }
                return; // Deleted
            }
            current = current.next;
        }
    }

    public void findCustomersByAccountType(String type) {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.data.accountType.equalsIgnoreCase(type)) {
                System.out.println(current.data);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No customers found with account type: " + type);
        }
    }
    

    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        Node current = head;
        while (current != null) {
            customers.add(current.data);
            current = current.next;
        }
        return customers;
    }
}

class FileManager {
    public static ArrayList<Customer> readCustomersFromFile(String filename) throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Account Id")) {
                String accountId = line.split("=")[1].trim();
                String name = reader.readLine().split("=")[1].trim();
                String address = reader.readLine().split("=")[1].trim();
                String dob = reader.readLine().split("=")[1].trim();
                String phoneNumber = reader.readLine().split("=")[1].trim();
                double accountBalance = Double.parseDouble(reader.readLine().split("=")[1].trim());
                String accountType = reader.readLine().split("=")[1].trim();
                double fixedDailyInterest = accountType.equalsIgnoreCase("Fixed") ? 
                    Double.parseDouble(reader.readLine().split("=")[1].trim()) : 0;

                customers.add(new Customer(accountId, name, address, dob, phoneNumber,
                                            accountBalance, accountType, fixedDailyInterest));
            }
        }
        reader.close();
        return customers;
    }

    public static void writeCustomersToFile(String filename, ArrayList<Customer> customers) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Customer customer : customers) {
            writer.write(customer.toString());
            writer.write("\n");
        }
        writer.close();
    }
}

public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoublyLinkedList customerList = new DoublyLinkedList();
        String filename = "customers.txt";

        try {
            // Load initial customer records
            for (Customer customer : FileManager.readCustomersFromFile(filename)) {
                customerList.addCustomer(customer);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Read Customer Records");
            System.out.println("2. Display Customer Records");
            System.out.println("3. Find Customer Records Based On Id");
            System.out.println("4. Find All Customer Records Having Account Type");
            System.out.println("5. Delete One Customer Record Based On Id");
            System.out.println("6. Add Customer Records");
            System.out.println("7. Write The Final Customer Records");
            System.out.println("8. Exit App");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Read records (already done at the start)
                    System.out.println("Customer records loaded.");
                    break;
                case 2:
                    customerList.displayCustomers();
                    break;
                case 3:
                    System.out.print("Enter Account ID to find: ");
                    String idToFind = scanner.nextLine();
                    Customer foundCustomer = customerList.findCustomerById(idToFind);
                    if (foundCustomer != null) {
                        System.out.println(foundCustomer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Account Type (Fixed/Saving/Checking): ");
                    String accountType = scanner.nextLine();
                    customerList.findCustomersByAccountType(accountType);
                    break;
                case 5:
                    System.out.print("Enter Account ID to delete: ");
                    String idToDelete = scanner.nextLine();
                    customerList.deleteCustomerById(idToDelete);
                    System.out.println("Customer deleted.");
                    break;
                    case 6:
                    System.out.print("Enter Account ID: ");
                    String newAccountId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String newAddress = scanner.nextLine();
                    System.out.print("Enter DOB: ");
                    String newDob = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter Account Balance: ");
                    double newAccountBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Account Type (Fixed/Saving/Checking): ");
                    String newAccountType = scanner.nextLine();
                    double newFixedDailyInterest = 0;
                    if (newAccountType.equalsIgnoreCase("Fixed")) {
                        System.out.print("Enter Fixed Daily Interest: ");
                        newFixedDailyInterest = scanner.nextDouble();
                    }
                    Customer newCustomer = new Customer(newAccountId, newName, newAddress, newDob, 
                                                        newPhoneNumber, newAccountBalance, 
                                                        newAccountType, newFixedDailyInterest);
                    customerList.addCustomer(newCustomer);
                    System.out.println("Customer added.");
                
                    // Save the updated customer list to the file
                    try {
                        FileManager.writeCustomersToFile(filename, customerList.getCustomers());
                        System.out.println("Records updated successfully in the file.");
                    } catch (IOException e) {
                        System.out.println("Error updating records in the file: " + e.getMessage());
                    }
                    break;                
                case 7:
                    try {
                        FileManager.writeCustomersToFile(filename, customerList.getCustomers());
                        System.out.println("Records saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving records: " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Do you want to save changes? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        try {
                            FileManager.writeCustomersToFile(filename, customerList.getCustomers());
                            System.out.println("Records saved successfully.");
                        } catch (IOException e) {
                            System.out.println("Error saving records: " + e.getMessage());
                        }
                    }
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
        
        scanner.close();
    }
}

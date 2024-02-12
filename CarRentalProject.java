package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carid;
    private String brand;
    private String model;
    private double basepriceperday;
    private boolean isavailable;
    public Car(String carid, String brand, String model, double basepriceperday){
        this.carid = carid;
        this.brand = brand;
        this.model = model;
        this.basepriceperday = basepriceperday;
        this.isavailable = true;
    }
    public String getCarid(){
        return carid;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double CalculatePrice(int rentalDays){
        return basepriceperday*rentalDays;
    }
    public boolean isavailable(){
        return isavailable;
    }
    public void rent(){
        isavailable = false;
    }
    public void returncar(){
        isavailable = true;
    }
}
class Customer{
    private String customerid;
    private String name;
    public Customer(String customerid, String name){
        this.customerid = customerid;
        this.name = name;
    }
    public String getCustomerid(){
        return customerid;
    }
    public String getName(){
        return name;
    }
}
class Rental{
    private Car car;
    private Customer customer;
    private int days;
    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void addcustomer(Customer customer){
        customers.add(customer);
    }
    public void rentcar(Car car, Customer customer, int days){
        if(car.isavailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else {
            System.out.println("car you are looking for is not available for rent");
        }
    }
    public void returncar(Car car){
        car.returncar();
        Rental rentaltoremove = null;
        for (Rental rental : rentals){
            if(rental.getCar() == car){
                rentaltoremove = rental;
                break;
            }
        }
        if (rentaltoremove != null){
            rentals.remove(rentaltoremove);
        }
        else {
            System.out.println("car was not rented");
        }
    }
    public void menu(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("********Car Rental System********");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1){
                System.out.println("\n*****Rent a car*****\n");
                System.out.println("Enter your name");
                String customername = scanner.nextLine();
                System.out.println("\nAvailable car");
                for(Car car:cars){
                    if(car.isavailable()){
                        System.out.println(car.getCarid() + "-" + car.getBrand() + "-" + car.getModel());
                    }
                }

                System.out.println("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();
                System.out.println("Enter the number of days for rental: ");
                int rentaldays = scanner.nextInt();
                scanner.nextLine();
                Customer newcustomer = new Customer("CUS" + (customers.size()+1), customername);
                addcustomer(newcustomer);

                Car selectedcar = null;
                for(Car car:cars){
                    if(car.getCarid().equals(carId) && car.isavailable()){
                        selectedcar = car;
                        break;
                    }
                }
                if(selectedcar != null){
                    double totalprice = selectedcar.CalculatePrice(rentaldays);
                    System.out.println("\n*****Rental information*****\n");
                    System.out.println("Customer ID: "+ newcustomer.getCustomerid());
                    System.out.println("Customer name:"+ newcustomer.getCustomerid());
                    System.out.println("Car"+ selectedcar.getBrand()+" "+selectedcar.getModel()+" "+selectedcar.getCarid());
                    System.out.println("Rental Days: "+ rentaldays);
                    System.out.printf("Total Price: $%.2f%n", totalprice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();
                    if(confirm.equalsIgnoreCase("Y")){
                        rentcar(selectedcar, newcustomer, rentaldays);
                        System.out.println("\nCar rented succesfully.");
                    }
                    else{
                        System.out.println("\nRental canceled.");
                    }
                }
                else{
                    System.out.println("\nInvalid car selection or selected car is not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n*****Return a car*****\n");
                System.out.println("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car cartoreturn = null;
                for(Car car:cars){
                    if(car.getCarid().equals(carId) && !car.isavailable()){
                        cartoreturn = car;
                        break;
                    }
                }
                if(cartoreturn != null){
                    Customer customer = null;
                    for(Rental rental:rentals){
                        if(rental.getCar() == cartoreturn);{
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null){
                        returncar(cartoreturn);
                        System.out.println("Car returned successfully by "+ customer.getName());
                    }
                    else {
                        System.out.println("Car was not rented or some information of rental is missing");
                    }
                }
                else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            }
            else {
                System.out.println("Invalid choice please enter a valid option");
            }
        }
        System.out.println("\nThank you for using Car Rental System.");
    }
}
public class CarRentalProject {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("COO1", "Toyota", "Camry", 60.0);
        Car car2 = new Car("COO2", "Mahindra", "Thar", 150.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.menu();
    }
}

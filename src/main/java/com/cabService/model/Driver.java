package com.cabService.model;

public class Driver {
    private int driverId;
    private String nic;
    private String name;
    private String email;
    private String phone;
    private String licenseNumber;
    private String vehicleType;
    private String vehicleModel;
    private String status;

    // Constructor
    public Driver(int driverId, String nic, String name, String email, String phone, String licenseNumber, String vehicleType, String vehicleModel, String status) {
        this.driverId = driverId;
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.status = status;
    }

    // Getters
    public int getDriverId() { return driverId; }
    public String getNic() { return nic; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getVehicleModel() { return vehicleModel; }
    public String getStatus() { return status; }

    // Setters
    public void setDriverId(int driverId) { this.driverId = driverId; }
    public void setNic(String nic) { this.nic = nic; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
    public void setStatus(String status) { this.status = status; }
}

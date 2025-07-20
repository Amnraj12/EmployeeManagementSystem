package com.company.employeemanagement.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Employee extends User {
    private String qualification;
    private boolean approved = false;

    private String additionalEmail;
    private String additionalPhone;
    private String gender;
    private String maritalStatus;
    private String bloodGroup;
    
    @Lob
    private byte[] profilePicture;

    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    // Getters and Setters for additional fields
    public String getAdditionalEmail() {
        return additionalEmail;
    }
    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }
    
    public String getAdditionalPhone() {
        return additionalPhone;
    }
    public void setAdditionalPhone(String additionalPhone) {
        this.additionalPhone = additionalPhone;
    }
    
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
package com.company.employeemanagement.model;

public class RankingRecord {
    private Employee employee;
    private int totalPoints;
    private int priorityCount;
    private int hoursSpent;

    public RankingRecord(Employee employee, int totalPoints, int priorityCount, int hoursSpent) {
        this.employee = employee;
        this.totalPoints = totalPoints;
        this.priorityCount = priorityCount;
        this.hoursSpent = hoursSpent;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public int getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    public int getPriorityCount() {
        return priorityCount;
    }
    public void setPriorityCount(int priorityCount) {
        this.priorityCount = priorityCount;
    }
    public int getHoursSpent() {
        return hoursSpent;
    }
    public void setHoursSpent(int hoursSpent) {
        this.hoursSpent = hoursSpent;
    }
}
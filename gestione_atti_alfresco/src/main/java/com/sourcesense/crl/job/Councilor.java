package com.sourcesense.crl.job;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Councilor {

    private String firstName;
    private String lastName;
    private String legislatureNumber;
    private String groupName;
    private List<String> committeeNames;

    public Councilor() {
        committeeNames = new ArrayList<String>();
    }

    public Councilor(String firstName, String lastName, String legislatureNumber, String groupName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.legislatureNumber = legislatureNumber;
        this.groupName = groupName;
        this.committeeNames = new ArrayList<String>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLegislatureNumber() {
        return legislatureNumber;
    }

    public void setLegislatureNumber(String legislatureNumber) {
        this.legislatureNumber = legislatureNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Collection<String> getCommitteeNames() {
        return committeeNames;
    }

    public void setCommitteeNames(List<String> committeeNames) {
        this.committeeNames = committeeNames;
    }

    public void addCommittee(String committee) {
        this.committeeNames.add(committee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Councilor)) return false;

        Councilor that = (Councilor) o;

        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Councilor");
        sb.append("{firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", legislatureNumber='").append(legislatureNumber).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", committeeNames=").append(committeeNames);
        sb.append('}');
        return sb.toString();
    }

}

package com.sourcesense.crl.job.anagrafica;


import java.util.ArrayList;
import java.util.List;


public class Councilor {

	private int id;
    private String firstName;
    private String lastName;
    private String legislatureNumber;
    private String groupName;
    private String codeGroupName;
    private List<Committee> committees;

    public Councilor() {
        committees = new ArrayList<Committee>();
    }

    public Councilor(String firstName, String lastName, String legislatureNumber, String groupName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.legislatureNumber = legislatureNumber;
        this.groupName = groupName;
        this.committees = new ArrayList<Committee>();
    }

    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

    public String getCodeGroupName() {
		return codeGroupName;
	}

	public void setCodeGroupName(String codeGroupName) {
		this.codeGroupName = codeGroupName;
	}

	public List<Committee> getCommittees() {
        return committees;
    }

    public void setCommittees(List<Committee> committees) {
        this.committees = committees;
    }

    public void addCommittee(Committee committee) {
        this.committees.add(committee);
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
       // sb.append(", committeeNames=").append(committeeNames);
        sb.append('}');
        return sb.toString();
    }

}

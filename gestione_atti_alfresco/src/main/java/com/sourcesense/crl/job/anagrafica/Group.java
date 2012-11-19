package com.sourcesense.crl.job.anagrafica;


public class Group {

    private int id;
    private String code;
    private String name;


    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Committee");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name);
        sb.append('}');
        return sb.toString();
    }

}

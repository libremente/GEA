package com.sourcesense.crl.job.anagrafica;


public class Committee {

    private int id;
    private String name;
    private int order;

    public Committee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
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

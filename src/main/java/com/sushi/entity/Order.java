package com.sushi.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sushi_order")
public class Order {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    @Column(name = "status_id", nullable = false)
	private Integer statusId;
	
    @Column(name = "sushi_id", nullable = false)
	private Integer sushiId;
	
    @Column(name = "created_at", nullable = false)
	private Timestamp createdAt;
    
    @Transient
    @JsonIgnore
    private int code = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getSushiId() {
		return sushiId;
	}

	public void setSushiId(Integer sushiId) {
		this.sushiId = sushiId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", statusId=" + statusId + ", sushiId=" 
				+ sushiId + ", createdAt=" + createdAt + "]";
	}

}

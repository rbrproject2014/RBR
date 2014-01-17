/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.essentials.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * User entity
 */
@Entity
@Table(name="APPUSER")
public class User implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ACCOUNT", nullable=false,length=32)
	String account;
	
	@Column(name="FULLNAME", nullable=false,length=128)
	String fullName;
	
	@Column(name="PASSWORD",nullable=false,length=255)
	String password;
	
	@Column(name="EMAIL",nullable=false,length=255)
	String email;
	
	@Column(name="BIRTHDATE",nullable = true)
	@Temporal( TemporalType.DATE)
	Date birthday;

    @Column(name="ROLE",nullable=true,length=20)
	String role;

    @Column(name = "BIO", nullable = true)
    String bio;

    @Column(name = "COUNTRY", nullable = true)
    String country;
	
	public User(){}
	
	public User(String account, String password, String fullName,String email) {
		this.account = account;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
	}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public String getAccount() {
		return account;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
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
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	public static User clone(User user){
		try {
			return (User)user.clone();
		} catch (CloneNotSupportedException e) {
			//not possible
		}
		return null;
	}

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

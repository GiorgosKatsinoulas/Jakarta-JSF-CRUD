package org.eclipse.jakarta.model;

//User.java
public class User {
    // Instance variables
    private int id;
    private String name;
    private String surname;
    private String email;
    private String major;
    private String phone;
    private int age;
    
    public User() {
	}
    
	public User(int id, String name, String surname, String email,String major,String phone,int age) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.major = major;
		this.phone = phone;
		this.age = age;
	}

    // Getters and Setters
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // toString method for easy display of user details
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", major=" + major
                + ", phone=" + phone + ", age=" + age + "]";
    }
}

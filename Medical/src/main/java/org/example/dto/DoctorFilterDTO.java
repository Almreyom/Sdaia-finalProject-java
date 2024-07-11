package org.example.dto;

import jakarta.ws.rs.QueryParam;

public class DoctorFilterDTO {
    @QueryParam("specialty")
    private String specialty;

    @QueryParam("rating")
    private Integer rating;

    @QueryParam("availability")
    private Boolean availability;

    @QueryParam("id")
    private Integer id;

    @QueryParam("name")
    private String name;

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

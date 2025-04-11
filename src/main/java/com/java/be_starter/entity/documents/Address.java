package com.java.be_starter.entity.documents;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String state;
    private String city;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

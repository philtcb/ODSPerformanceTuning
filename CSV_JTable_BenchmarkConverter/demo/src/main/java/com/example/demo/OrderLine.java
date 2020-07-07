package com.example.demo;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
class OrderLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long itemId;
    private String addressLine;
    private Integer quantity;

    public void setAddressLine(String s) {
        addressLine = s;
    }

    public void setItemId(long l) {
        itemId = l;
    }

    public void setQuantity(int i) {
        quantity = i;
    }
}
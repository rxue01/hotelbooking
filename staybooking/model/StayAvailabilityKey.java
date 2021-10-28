package com.laioffer.staybooking.model;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import java.time.LocalDate;
@Embeddable

public class StayAvailabilityKey implements Serializable {

    private static final long SerialVersionUID = 1L;
    private Long stay_id;
    private LocalDate date;

    public StayAvailabilityKey(){

    }// hibernate 只能用不带参数的构造函数（此class 和hibernate有关）

    public StayAvailabilityKey(Long stay_id, LocalDate date) {
        this.stay_id = stay_id;
        this.date = date;
    }

    public Long getStay_id() {
        return stay_id;
    }

    public StayAvailabilityKey setStay_id(Long stay_id) {
        this.stay_id = stay_id;
        return this;
    }
    public LocalDate getDate() {
        return date;
    }

    public StayAvailabilityKey setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StayAvailabilityKey that = (StayAvailabilityKey) o;
        return stay_id.equals(that.stay_id) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stay_id, date);
    }

}

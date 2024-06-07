package org.hibernate.bugs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Access(AccessType.PROPERTY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailSingleId implements Serializable {

    private Long id;
    private Company company;
    private EventSingleId event;
    @Id
    @Column(name = "EVENT_DETAIL_SEQ")
    public Long getId() {
        return id;
    }


    @JoinColumn(name = "COMPANY_FK")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = true)
    public Company getCompany() {
        return company;
    }


    public void setEvent(EventSingleId event) {
        this.event = event;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "EVENT_FK")
    public EventSingleId getEvent() {
        return event;
    }
}

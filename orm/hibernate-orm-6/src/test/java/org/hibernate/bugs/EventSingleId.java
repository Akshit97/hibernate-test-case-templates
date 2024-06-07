package org.hibernate.bugs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSingleId implements Serializable {

    private Long id = new Random().nextLong();
    private Company company;
    private Set<EventDetailSingleId> eventDetailSet;
    private String eventType;
    @Id
    @Column(name = "EVENT_SEQ")
    public Long getId() {
        return id;
    }

    @JoinColumn(name = "COMPANY_FK")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
    public Company getCompany() {
        return company;
    }

    @Column(name = "EVENT_TYPE")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @OneToMany(cascade =  CascadeType.MERGE  , mappedBy = "event" )
    Set<EventDetailSingleId> getEventDetailSet()
    {
        return eventDetailSet;
    }
}

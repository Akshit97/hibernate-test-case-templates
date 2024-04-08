package org.hibernate.bugs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    private Long id;
    private Company company;
    private String eventType;

    private Event oldEvent;

    private Long oldEventSeq;

    @Id
    @Column(name = "EVENT_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @JoinColumn(name = "COMPANY_FK")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @Id
    public Company getCompany() {
        return company;
    }

    @Column(name = "EVENT_TYPE")
    public String getEventType() {
        return eventType;
    }

    @Column(name = "OLD_EVENT_FK")
    public Long getOldEventSeq() {
        return oldEventSeq;
    }

    public void setOldEventSeq(Long oldEventSeq) {
        this.oldEventSeq = oldEventSeq;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "OLD_EVENT_FK", referencedColumnName = "EVENT_SEQ", insertable=false, updatable = false),
            @JoinColumn(name = "COMPANY_FK", referencedColumnName = "COMPANY_FK", insertable=false, updatable = false)
    })
    public Event getOldEvent() {
        return oldEvent;
    }

    public void setOldEvent(Event oldEvent) {
        this.oldEvent = oldEvent;
        this.oldEventSeq = oldEvent == null ? null : oldEvent.getId();
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

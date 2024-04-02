package org.hibernate.bugs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.PROPERTY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDetail implements Serializable {

    private Long id;
    private Company company;
    private Event event;
    @Id
    @Column(name = "EVENT_DETAIL_SEQ")
    public Long getId() {
        return id;
    }


    @JoinColumn(name = "COMPANY_FK")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @Id
    public Company getCompany() {
        return company;
    }

    private Long eventSeq;

    @Column(name = "EVENT_FK")
    public Long getEventSeq() {
        return eventSeq;
    }

    public void setEventSeq(Long eventSeq) {
        this.eventSeq = eventSeq;
    }

    public void setEvent(Event event) {
        this.event = event;
        this.eventSeq = event == null ? null : event.getId();
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumns({
                    @JoinColumn(name = "EVENT_FK", referencedColumnName = "EVENT_SEQ", insertable = false, updatable = false),
                    @JoinColumn(name = "COMPANY_FK", referencedColumnName = "COMPANY_FK", insertable = false, updatable = false)
            })
    public Event getEvent() {
        return event;
    }
}

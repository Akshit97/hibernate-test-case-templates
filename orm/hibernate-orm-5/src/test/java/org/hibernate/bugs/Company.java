package org.hibernate.bugs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long id;

    @Id
    @Column(name = "COMPANY_SEQ")
    public Long getId() {
        return id;
    }


}

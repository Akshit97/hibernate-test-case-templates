package org.hibernate.bugs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityA implements Serializable {

    private Long id1;
    private Long id2;
    private List<EntityB> entityBList;

    @Id
    @Column(name = "A_SEQ", unique = true)
    public Long getId1() {
        return id1;
    }

    @Id
    @Column(name = "A_SEQ_1")
    public Long getId2() {
        return id2;
    }

    @ManyToMany
//    @JoinTable(name = "A_B_Table", joinColumns =
//            {@JoinColumn(name = "A_SEQ", referencedColumnName = "A_SEQ"),
//                    @JoinColumn(name = "A_SEQ_1", referencedColumnName = "A_SEQ_1")},
//            inverseJoinColumns = @JoinColumn(name = "B_SEQ"))
    @JoinTable(name = "A_B_Table", joinColumns = @JoinColumn(name = "A_SEQ", referencedColumnName = "A_SEQ"),
    inverseJoinColumns = @JoinColumn(name = "B_SEQ", referencedColumnName = "B_SEQ"))
    public List<EntityB> getEntityBList() {
        return entityBList;
    }



}

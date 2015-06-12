package com.ozstrategy.model.credits;

import com.ozstrategy.model.BaseEntity;
import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.userrole.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lihao1 on 5/24/15.
 */
@Entity
public class UserCredits extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    @Column
    private Double total=new Double(0);
    @Column
    private Integer lastType;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "credits")
    private Set<CreditsDetail> details=new HashSet<CreditsDetail>();
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;


    public UserCredits() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getLastType() {
        return lastType;
    }

    public void setLastType(Integer lastType) {
        this.lastType = lastType;
    }

    public Set<CreditsDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<CreditsDetail> details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserCredits user = (UserCredits) o;
        return new EqualsBuilder()
                .append(id, user.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .hashCode();

    }
}

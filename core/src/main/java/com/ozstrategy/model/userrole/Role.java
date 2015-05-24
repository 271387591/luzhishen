package com.ozstrategy.model.userrole;

import com.ozstrategy.model.BaseEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role extends BaseEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 32)
    private String name;
    @Column
    private String description;
    @Column
    private String displayName;
    @Column(columnDefinition = "char",length = 1)
    @Type(type = "yes_no")
    private Boolean enabled=Boolean.TRUE;
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "roles")
    private Set<User> users=new HashSet<User>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name               = "RoleFeature",
            joinColumns        = { @JoinColumn(name = "roleId") },
            inverseJoinColumns = @JoinColumn(name = "featureId")
    )
    private Set<Feature> features=new HashSet<Feature>();
    
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAuthority() {
        return getName();
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Role role = (Role) o;
        return new EqualsBuilder()
                .append(id,role.id)
                .append(name,role.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .hashCode();
    }
} 

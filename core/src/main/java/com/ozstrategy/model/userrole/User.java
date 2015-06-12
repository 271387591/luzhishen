package com.ozstrategy.model.userrole;

import com.ozstrategy.Constants;
import com.ozstrategy.model.BaseEntity;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.money.UserMoney;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
@Entity
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    @Column(columnDefinition = "char",length = 1)
    @Type(type = "yes_no")
    private Boolean  accountExpired=Boolean.FALSE;
    @Column(columnDefinition = "char",length = 1)
    @Type(type = "yes_no")
    private Boolean  accountLocked=Boolean.FALSE;
    @Column(columnDefinition = "char",length = 1)
    @Type(type = "yes_no")
    private Boolean  credentialsExpired=Boolean.FALSE;
    @Column(columnDefinition = "char",length = 1)
    @Type(type = "yes_no")
    private Boolean  enabled=Boolean.TRUE;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Column
    private String   email;
    @Column
    private String   password;
    @Column(unique = true)
    @Index(columnNames = {"username"},name = "usernameIndex")
    private String   username;
    @Column
    private String   gender;
    @Column
    private String   mobile;
    @JoinTable(
            name               = "UserRole",
            joinColumns        = { @JoinColumn(name = "userId") },
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    @ManyToMany(fetch   = FetchType.LAZY)
    private Set<Role> roles              = new HashSet<Role>();
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    @Column
    private String referee;
    @Column
    private String hotPass;
    @OneToOne(mappedBy = "user")
    private UserMoney userMoney;
    @OneToOne(mappedBy = "user")
    private UserCredits userCredits;


    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public void addRole(Role role) {
        getRoles().add(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getHotPass() {
        return hotPass;
    }

    public void setHotPass(String hotPass) {
        this.hotPass = hotPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll(roles);

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Transient 
    public boolean isAccountNonExpired() {
        return !getAccountExpired();
    }
    @Transient 
    public boolean isAccountNonLocked() {
        return !getAccountLocked();
    }
    @Transient 
    public boolean isCredentialsNonExpired() {
        return !getCredentialsExpired();
    }
    @Transient
    public boolean isEnabled() {
        return enabled;
    }
    @Transient
    public boolean isAdmin() {
        for (Object obj : getRoles()) {
            Role role = (Role) obj;

            if (Constants.ADMIN_ROLE.equals(role.getName())) {
                return true;
            }
        }
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserMoney getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(UserMoney userMoney) {
        this.userMoney = userMoney;
    }

    public UserCredits getUserCredits() {
        return userCredits;
    }

    public void setUserCredits(UserCredits userCredits) {
        this.userCredits = userCredits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;
        return new EqualsBuilder()
                .append(id,user.id)
                .append(username,user.username)
                .append(mobile,user.mobile)
                .append(email,user.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(username)
                .append(mobile)
                .append(email)
                .hashCode();
        
    }
} 

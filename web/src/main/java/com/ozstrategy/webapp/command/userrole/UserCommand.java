package com.ozstrategy.webapp.command.userrole;

import com.ozstrategy.model.userrole.Feature;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.User;

import javax.persistence.Column;
import java.util.*;


public class UserCommand {
    private boolean accountLocked;
    private Date createDate;
    private boolean enabled;
    private Long id;
    private String password;
    private String roleDisplayName;
    private Long roleId;
    private List<Long> roleIds = new ArrayList<Long>();
    private String roleName;
    private List<SimpleRoleCommand> simpleRoles = new ArrayList<SimpleRoleCommand>();
    private String username;
    private Boolean admin = false;
    private Set<String> features = new LinkedHashSet<String>();
    private String email;
    private String mobile;
    private String gender;
    private String referee;
    private String hotPass;


    public UserCommand() {
    }

    public UserCommand(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.enabled = user.isEnabled();
        this.createDate = user.getCreateDate();
        this.accountLocked = user.getAccountLocked();
        this.admin = user.isAdmin();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.gender = user.getGender();
        this.referee=user.getReferee();
        this.hotPass=user.getHotPass();
        Role defRole=user.getRole();
        if(defRole!=null){
            this.roleId=defRole.getId();
            this.roleName=defRole.getName();
            this.roleDisplayName=defRole.getDisplayName();
        }

        Set<Feature> featureList=new HashSet<Feature>();
        if ((user.getRoles() != null) && (user.getRoles().size() > 0)) {
            for (Role role : user.getRoles()) {
                SimpleRoleCommand simpleRoleCommand = new SimpleRoleCommand();
                simpleRoleCommand.setId(role.getId());
                simpleRoleCommand.setDisplayName(role.getDisplayName());
                simpleRoleCommand.setName(role.getName());
                if(role.getId()==roleId){
                    simpleRoleCommand.setIsDefault(true);
                }
                this.simpleRoles.add(simpleRoleCommand);
                featureList.addAll(role.getFeatures());
            }
        }
        for(Feature feature:featureList){
            this.features.add(feature.getName());
        }
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleDisplayName() {
        return roleDisplayName;
    }

    public void setRoleDisplayName(String roleDisplayName) {
        this.roleDisplayName = roleDisplayName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<SimpleRoleCommand> getSimpleRoles() {
        return simpleRoles;
    }

    public void setSimpleRoles(List<SimpleRoleCommand> simpleRoles) {
        this.simpleRoles = simpleRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}

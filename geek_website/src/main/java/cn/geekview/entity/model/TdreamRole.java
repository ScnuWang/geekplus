package cn.geekview.entity.model;

public class TdreamRole {
    private Integer pkId;

    private String roleName;

    private String roleSecurity;

    private Integer roleStatus;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleSecurity() {
        return roleSecurity;
    }

    public void setRoleSecurity(String roleSecurity) {
        this.roleSecurity = roleSecurity == null ? null : roleSecurity.trim();
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}
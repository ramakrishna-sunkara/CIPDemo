package poc.cip.cgs.com.cip_poc;

import java.io.Serializable;

/**
 * Created by Rohini on 5/19/2015.
 */
public class UserDto implements Serializable {

    private String isauthenticated;
    private String username;
    private String empid;
    private String authentication;
    private String otp;
    private String agentmobile;
    private String userid;
    private String password;
    private String active;
    private String empname;
    private String rolename;
    private String branchname;
    private String lobname;

    public String getIsauthenticated() {
        return isauthenticated;
    }

    public void setIsauthenticated(String isauthenticated) {
        this.isauthenticated = isauthenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAgentmobile() {
        return agentmobile;
    }

    public void setAgentmobile(String agentmobile) {
        this.agentmobile = agentmobile;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getLobname() {
        return lobname;
    }

    public void setLobname(String lobname) {
        this.lobname = lobname;
    }



}

package poc.cip.cgs.com.cip_poc;

import java.io.Serializable;

/**
 * Created by Rohini on 6/3/2015.
 */
public class CusDto implements Serializable {
    private String ClientInfoId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Mobile;
    private String isSuccess;
    private String SuccessMessage;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }


    public String getClientInfoId() {
        return ClientInfoId;
    }

    public void setClientInfoId(String ClientInfoId) {
        ClientInfoId = ClientInfoId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }


}

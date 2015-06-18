package poc.cip.cgs.com.cip_poc.model;

/**
 * Created by Niaz on 5/29/2015.
 */
public class MyAppConstants {

    public static final String API = "http://appserver.constient.com/KYCDiss/api/";
    public static final String LOGIN_API = API + "Login/Authentication";
    public static final String GET_REF_API = API + "DocumentTracking/GetReferenceNumberByAgent";
    public static final String UPD_REF_API = API + "DocumentTracking/UpdateDocumentTrackingStatusByRefNo";
    public static final String CUSTOMER_API = API + "Profile/GetProfileInfoByReferenceNumber";
    public static final String CUSTOMER_UPDATE =API +"DocumentTracking/UpdateDocumentTrackingByStatus";
    public static final String APK_VERSION = "3200";
}

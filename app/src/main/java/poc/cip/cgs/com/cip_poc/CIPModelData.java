package poc.cip.cgs.com.cip_poc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public class CIPModelData {

    public static ArrayList<ItemData> items = new ArrayList<ItemData>();

    public static ArrayList<ItemData> getItems() {
        return items;
    }

    public static void persistRefNum(String ref, int serverStatus) {
        items.add(new ItemData(ref, serverStatus));
//        items.add(new ItemData("WB60002364", 0));
    }

    public static void removeItems(String title) {
//        ArrayList<ItemData> items_new = items.;
//        for (ItemData itemData : items_new) {
//            if (itemData.getTitle().equals(title)) {
//                items.remove(itemData);
//            }
//        }
        for (Iterator<ItemData> iterator = items.iterator(); iterator.hasNext(); ) {
            ItemData itemData = iterator.next();
            if (itemData.getTitle().equals(title)) {
                // Remove the current element from the iterator and the list.
                iterator.remove();
            }
        }
    }

    public static void clearListItems() {
        items.clear();
    }

    public static void updateScanStatus(String title, boolean status) {
        for (ItemData itemData : items) {
            if (itemData.getTitle().equals(title)) {
                itemData.setScanStatus(status);
                return;
            }
        }
    }

    public static void persistLoginInfo(Activity activity, UserDto userDto) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity
                        .getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("UserId", userDto.getUserid());
        prefsEditor.putString("UserName", userDto.getUsername());
        prefsEditor.putString("EmployeeId", userDto.getEmpid() + "");
        prefsEditor.putString("EmployeeName", userDto.getEmpname());
        prefsEditor.putString("RoleName", userDto.getRolename());
        prefsEditor.putString("Branch", userDto.getBranchname());
        prefsEditor.putString("LOBName", userDto.getLobname());
        prefsEditor.commit();
    }

    public static void persistCustomerInfo(Activity activity, CusDto CusDto) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity
                        .getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("ClientInfoId", CusDto.getClientInfoId());
        prefsEditor.putString("FirstName", CusDto.getFirstName());
        prefsEditor.putString("LastName", CusDto.getLastName() + "");
        prefsEditor.putString("Email", CusDto.getEmail());
        prefsEditor.putString("Mobile", CusDto.getMobile());
        prefsEditor.commit();
    }

    public static void persistCustomerUpdate(Activity activity, CusDto CusDto) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity
                        .getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("IsSuccess", CusDto.getIsSuccess());
        prefsEditor.putString("SuccessMessage", CusDto.getSuccessMessage());

        prefsEditor.commit();
    }
}

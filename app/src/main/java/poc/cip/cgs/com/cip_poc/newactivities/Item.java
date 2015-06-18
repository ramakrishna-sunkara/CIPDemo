package poc.cip.cgs.com.cip_poc.newactivities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import poc.cip.cgs.com.cip_poc.AppAsyncTask;
import poc.cip.cgs.com.cip_poc.CIPModelData;
import poc.cip.cgs.com.cip_poc.CustomerActivity;
import poc.cip.cgs.com.cip_poc.R;
import poc.cip.cgs.com.cip_poc.UserDto;
import poc.cip.cgs.com.cip_poc.model.MyAppConstants;

/**
 * Created by ramakrishna.s on 6/12/2015.
 */
public class Item implements Serializable{
    public void setTitle(String title) {
        this.title = title;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private String title;
    private boolean active;

//    Item(String title, boolean active) {
//        this.title = title;
//        this.active = active;
//    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }
}
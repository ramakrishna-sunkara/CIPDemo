/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package poc.cip.cgs.com.cip_poc.newactivities;

import java.util.Random;

import poc.cip.cgs.com.cip_poc.R;

public class CIPConstants {

    private static final Random RANDOM = new Random();

    public static int getRandomCheeseDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.ic_accept;
            case 1:
                return R.drawable.ic_accept;
            case 2:
                return R.drawable.ic_accept;
            case 3:
                return R.drawable.ic_accept;
            case 4:
                return R.drawable.ic_accept;
        }
    }

    public static final String[] sCheeseStrings = {
            "WB60002536", "WB60002537","WB60002538","WB60002539","WB60002540","WB60002541","WB60002542",
            "WB60002543","WB60002544","WB60002545","WB60002546","WB60002547","WB60002548","WB60002549",
    };

    public static final String INTENT_ACTOIN_FROM = "intent from";
    public static final String KEY_SCAN_ID = "scan id";
    public static final String KEY_SCAN_LIST = "scan ids list";
    public static final String KEY_ASSN_CUST_ID_LIST = "assign ids list";

}

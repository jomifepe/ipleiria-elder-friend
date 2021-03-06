package com.example.tsngapp.helpers;

public class Constants {
    public static final String DEBUG_TAG = "DEBUG_TSNGApp";

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_TIME_FORMAT = "HH:mm:ss";
    public static final String SHORT_TIME_FORMAT = "HH:mm";

    public static final int REQUEST_READ_TIMEOUT = 10000;
    public static final int REQUEST_CONNECT_TIMEOUT = 15000;

    //SHARED_PREFS
    public static final String APP_TAG = "TSNGApp";
    public static final String SP_TOKEN_KEY = APP_TAG + "_token";
    public static final String SP_USER_KEY = APP_TAG + "_user";
    public static final String SP_ELDER_KEY = APP_TAG + "_elder";

    //API
    private static final String BASE_URL = "http://smartaal.dei.estg.ipleiria.pt/";
    private static final String BASE_API_URL = BASE_URL + "api/";

    public static final String STORAGE_URL = BASE_URL + "storage/";
    public static final String LOGIN_URL = BASE_API_URL + "login";
    public static final String USERS_ME_URL = BASE_API_URL + "users/me";
    // parameters: treater/user id
    public static final String ELDER_INFO_URL = BASE_API_URL + "elder/treater/%d";
    public static final String REGISTER_URL = BASE_API_URL + "register";
    public static final String LOGOUT_URL = BASE_API_URL + "logout";

    // last valye/state format: elderid
    public static final String DOOR_STATE_URL = BASE_API_URL + "door/%d/last";
    public static final String BED_STATE_URL = BASE_API_URL + "bed/%d/last";
    public static final String TEMPERATURE_VALUE_URL = BASE_API_URL + "temperature/%d/last";
    public static final String SOS_VALUE_URL = BASE_API_URL + "sos/%d/all";
    public static final String GAS_EMISSION_VALUE_URL = BASE_API_URL + "gas/%d/last";
    public static final String UNREAD_NOTIFICATIONS_URL = BASE_API_URL + "notifications/%d/unread";

    // last values format order: elderid, number of values
    public static final String DOOR_LAST_VALUES_URL = BASE_API_URL + "door/%d/lastValues/%d";
    public static final String LIGHT_LAST_VALUES_URL = BASE_API_URL + "light/%d/lastValues/%d";
    public static final String WINDOW_LAST_VALUES_URL = BASE_API_URL + "window/%d/lastValues/%d";

    // division last values arguments: elderid, division (all, bedroom, kitchen, bathroom, attic & livingroom)
    public static final String WINDOW_DIVISION_VALUES_URL = BASE_API_URL + "window/%d/division/%s/all";
    public static final String LIGHT_DIVISION_LAST_VALUES_URL = BASE_API_URL + "light/%d/division/%s/all";

    public static final String BED_LAST_VALUES_URL = BASE_API_URL + "bed/%d/lastValues/%d";
    public static final String INTERNAL_TEMP_LAST_VALUES_URL = BASE_API_URL + "internaltemp/%d/lastValues/%d";
    public static final String ELECTRICAL_CURRENT_LAST_VALUES_URL = BASE_API_URL + "current/%d/lastValues/%d";
    public static final String CURRENT_SENSOR_VALUES_CHART = BASE_API_URL + "correntSensorValuesChart";

    // arguments: elderid, division (all, bedroom, kitchen, bathroom, attic & livingroom)
    public static final String DIVISION_VALUES_URL = BASE_API_URL + "division/%d/division/%s/all";

    //Activities
    public static final int REGISTER_ACTIVITY_CODE = 1;
    public static final String INTENT_USER_KEY = "user";
    public static final String INTENT_PASSWORD_KEY = "password";
    public static final String INTENT_ELDER_KEY = "elder";

    //API_RESPONSES
    public static final String HTTP_OK = "Success";
    public static final String HTTP_ERROR = "Error";

    public static class Pusher {
        public static final String CHANNEL_CURRENT = "new.current.value";
        public static final String CHANNEL_INTERNAL_TEMP = "new.internaltemp.value";
        public static final String CHANNEL_DOOR_VALUE = "new.door.value";
        public static final String CHANNEL_LIGHT_VALUE = "new.light.value";
        public static final String CHANNEL_BED_VALUE = "new.bed.value";
        public static final String CHANNEL_WINDOW_VALUE = "new.window.value";
        public static final String CHANNEL_DIVISION_VALUE = "new.division.value";
        public static final String CHANNEL_CURRENT_HOUR_VALUE = "hour.graph.update";
        public static final String CHANNEL_CURRENT_DAY_VALUE = "day.graph.update";
        public static final String CHANNEL_CURRENT_MONT_VALUE = "month.graph.update";
        public static final String CHANNEL_DOOR_ANOMALY_VALUE = "door.anomaly";
        public static final String CHANNEL_SLEEP_ANOMALY_VALUE = "sleep.anomaly";

        public static final String EVENT_NEW_CURRENT_VALUE = "App\\Events\\NewCurrentValueEvent";
        public static final String EVENT_NEW_INTERNAL_TEMP_VALUE = "App\\Events\\NewInternalTemperatureValueEvent";
        public static final String EVENT_NEW_DOOR_VALUE = "App\\Events\\NewDoorValueEvent";
        public static final String EVENT_NEW_LIGHT_VALUE = "App\\Events\\NewLightValueEvent";
        public static final String EVENT_NEW_BED_VALUE = "App\\Events\\NewBedValueEvent";
        public static final String EVENT_NEW_WINDOW_VALUE = "App\\Events\\NewWindowValueEvent";
        public static final String EVENT_NEW_DIVISION_VALUE = "App\\Events\\NewDivisionValueEvent";
        public static final String EVENT_NEW_CURRENT_HOUR_VALUES = "App\\Events\\HourGraphEvent";
        public static final String EVENT_NEW_CURRENT_DAY_VALUES = "App\\Events\\DayGraphEvent";
        public static final String EVENT_NEW_CURRENT_MONTH_VALUES = "App\\Events\\MonthGraphEvent";
        public static final String EVENT_NEW_DOOR_ANOMALY = "App\\Events\\DoorAnomalyEvent";
        public static final String EVENT_SLEEP_DOOR_ANOMALY = "App\\Events\\SleepAnomalyEvent";
    }

    public static final String STORAGE_DASHBOARD_FILENAME = APP_TAG + "_" + "Dashboard";
    public static final String STORAGE_ELDER_PROFILE_PICTURE_FILENAME = APP_TAG + "_" + "elderProfilePicture";

    public static final int RNG_BOUND = 1000000;

    public static final String GENERAL_NOTIFICATIONS_CHANNEL_ID = "com.example.tsngapp.general_notifications";
    public static final String GENERAL_NOTIFICATIONS_CHANNEL_NAME = "General";
    public static final String URGENT_NOTIFICATIONS_CHANNEL_ID = "com.example.tsngapp.urgent_notifications";
    public static final String URGENT_NOTIFICATIONS_CHANNEL_NAME = "Urgent";
}



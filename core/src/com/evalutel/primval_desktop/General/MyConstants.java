package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MyConstants
{
    public static int SCREENWIDTH = Gdx.graphics.getWidth();
    public static int SCREENHEIGHT = Gdx.graphics.getHeight();


    public static int screenWidthQuarter = SCREENWIDTH / 4;

    public static FreeTypeFontGenerator FONT_ZAP = new FreeTypeFontGenerator(Gdx.files.internal("font/Zapf Humanist 601 BT.ttf"));
    public static FreeTypeFontGenerator FONT_FRHND = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
    public static FreeTypeFontGenerator FONT_COMICI = new FreeTypeFontGenerator(Gdx.files.internal("font/comici.ttf"));


    public static Color bluePrimval = new Color(Color.valueOf("004ec0"));
    public static Color redPrimval = new Color(Color.valueOf("a40b00"));


    public static String DATABASE_TIMESTAMP = "timestamp";
    public static String DATABASE_DOCUMENT_ID = "document_id";

// USERS

    public static String DATABASE_PATH_USERS = "users";
    public static String DATABASE_USER_ID = "user_id";
    public static String DATABASE_USER_PHONE = "phone";
    public static String DATABASE_USER_CREATE_TIME = "user_create_time";
    public static String DATABASE_USER_NAME = "user_name";
    public static String DATABASE_USER_IMAGE_REF = "image_ref";
    public static String DATABASE_USER_TOKEN_NOTIF = "token_notif";
    public static String DATABASE_PATH_ALL_PHONES = "all_phones";
    public static String DATABASE_USER_TOPCIS = "user_topics";
//public static String DATABASE_PHONES_IS_PRESENT = "is_present";


// Event

    public static String DATABASE_PATH_EVENTS = "events";
    public static String DATABASE_EVENT_ID = "event_id";
    public static String DATABASE_EVENT_NAME = "event_name";
    public static String DATABASE_EVENT_DESCRIPTION = "event_description";
    public static String DATABASE_EVENT_IS_CHAT_UNACTIVATE = "is_chat_unactivate";
    public static String DATABASE_EVENT_CREATEUR = "event_createur";
    public static String DATABASE_EVENT_ADMINS = "event_admins";
    public static String DATABASE_EVENT_IMAGE_REF = "event_image_ref";
    public static String DATABASE_EVENT_START_DATE = "event_start_date";
    public static String DATABASE_EVENT_END_DATE = "event_end_date";
    public static String DATABASE_EVENT_PHONES = "event_phones";
    public static String DATABASE_EVENT_PENDINGS_PHONES = "event_pendings_phones";
    public static String DATABASE_EVENT_PENDINGS_USER_SRC = "event_pendings_user_src";
    public static String DATABASE_EVENT_CREATION_TIMESTAMP = "event_timestamp_creation";
    public static String DATABASE_EVENT_UPDATE_TIMESTAMP = "event_timestamp_update";
    public static String DATABASE_EVENT_LOCATION = "event_location";
    public static String DATABASE_EVENT_LOCATION_TITLE = "location_title";
    public static String DATABASE_EVENT_LOCATION_SUBTITLE = "location_subtitle";
    public static String DATABASE_EVENT_LOCATION_LATITUDE = "location_latitude";
    public static String DATABASE_EVENT_LOCATION_LONGITUDE = "event_longitude";

    public static String DATABASE_EVENT_USERS_TOKENS = "event_users_tokens";
    public static String DATABASE_EVENT_PARTICIPANTS = "event_participants";
    public static String DATABASE_EVENT_USERS_ID = "event_users_ids";
    public static String DATABASE_EVENT_PARTICIPE = "participe";
    public static String DATABASE_EVENT_PARTICIPANT_YES = "yes";
    public static String DATABASE_EVENT_PARTICIPANT_NO = "no";
    public static String DATABASE_EVENT_PARTICIPANT_MAYBE = "maybe";
    public static String DATABASE_EVENT_PARTICIPANT_NOMBRE = "nombre";
    public static String DATABASE_EVENT_PARTICIPANT_NOTIFICATION = "notification";
    public static String DATABASE_EVENT_CANCEL = "event_canceled";

    public static String DATABASE_EVENT_USERS_NOTIFICATION = "event_users_notifications";

    public static String DATABASE_EVENT_SONDAGES = "sondages";
    public static String DATABASE_EVENT_SONDAGE_ID = "id_sondage";
    public static String DATABASE_EVENT_SONDAGE_USERS = "users";
    public static String DATABASE_EVENT_SONDAGE_QUESTION = "question";
    public static String DATABASE_EVENT_SONDAGE_REPONSES = "reponses";
    public static String DATABASE_EVENT_SONDAGE_USERS_REPONSES = "users_reponses";
    public static String DATABASE_EVENT_SONDAGE_FINAL_REPONSE_INDICE = "sondage_final_reponse_indice";

// MESSAGES EVENTS


    public static String DATABASE_EVENT_MESSAGES = "messages";
    public static String DATABASE_EVENT_MESSAGE_ID = "message_id";
    public static String DATABASE_EVENT_MESSAGE_TYPE = "message_type";
    public static String DATABASE_EVENT_MESSAGE_TYPE_FIRST = "message_first";
    public static String DATABASE_EVENT_MESSAGE_TYPE_TEXT = "message_type_text";
    public static String DATABASE_EVENT_MESSAGE_TYPE_IMAGE = "message_type_image";
    public static String DATABASE_EVENT_MESSAGE_TYPE_MULTIPLE_IMAGES = "message_type_multiples_images";
    public static String DATABASE_EVENT_MESSAGE_TYPE_SOUND_RECORD = "message_type_sound_recording";
    public static String DATABASE_EVENT_MESSAGE_TYPE_VIDEO_SOUVENIR = "message_type_video_souvenir";
    public static String DATABASE_EVENT_MESSAGE_TYPE_VIDEO = "message_type_video";
    public static String DATABASE_EVENT_MESSAGE_TYPE_CONTACT = "message_type_contact";
    public static String DATABASE_EVENT_MESSAGE_TYPE_NEW_SONDAGE = "message_type_new_sondage";
    public static String DATABASE_EVENT_MESSAGE_TYPE_PARTICIPATE = "message_type_participate";
    public static String DATABASE_EVENT_MESSAGE_TYPE_INFO_CHANGED = "message_type_info_changed";
    public static String DATABASE_EVENT_MESSAGE_TYPE_QUIT_GROUP = "message_type_quit_group";
    public static String DATABASE_EVENT_MESSAGE_TYPE_DELETED = "message_type_deleted";
    public static String DATABASE_EVENT_MESSAGE_TYPE_ADMIN_DELETED = "message_type_admin_deleted";
    public static String DATABASE_EVENT_MESSAGE_TYPE_ADMIN_USER_REMOVED = "message_type_admin_user_removed"; //NEW
    public static String DATABASE_EVENT_MESSAGE_TYPE_EVENT_CANCELED = "message_type_vent_canceled";
    public static String DATABASE_EVENT_MESSAGE_TYPE_EVENT_ACTIVATED = "message_type_vent_activated";
    public static String DATABASE_EVENT_MESSAGE_TYPE_LIKE = "message_type_like";
    public static String DATABASE_EVENT_MESSAGE_TYPE_COMMENT = "message_type_comment";
    public static String DATABASE_EVENT_MESSAGE_TYPE_SOUS_COMMENT = "message_type_sous_comment";
    public static String DATABASE_EVENT_MESSAGE_USER_NAME = "message_user_name";
    public static String DATABASE_EVENT_MESSAGE_USER_TOKEN = "message_user_token";
    public static String DATABASE_EVENT_MESSAGE_ID_COMMENT = "message_id_comment";
    public static String DATABASE_EVENT_MESSAGE_ID_SOUS_COMMENT = "message_id_sous_comment";
    //public static String DATABASE_EVENT_MESSAGE_EVENT_NAME = "message_event_name";
    public static String DATABASE_EVENT_MESSAGE_TEXT = "message_text";
    public static String DATABASE_EVENT_MESSAGE_TEXT2 = "message_text2";
    public static String DATABASE_EVENT_MESSAGE_USERS_CITES = "message_users_cites";
    public static String DATABASE_EVENT_MESSAGE_USERS_NAME_CITES = "message_users_names_cites";
    public static String DATABASE_EVENT_MESSAGE_USER = "message_user";
    public static String DATABASE_EVENT_MESSAGE_TIMESTAMP = "message_timestamp";
    public static String DATABASE_EVENT_MESSAGE_TIMESTAMP_START = "message_timestamp_start";
    public static String DATABASE_EVENT_MESSAGE_IS_UPLOAD = "message_is_upload";
    public static String DATABASE_EVENT_MESSAGE_IS_UPDATE = "message_is_update";
    public static String DATABASE_EVENT_MESSAGE_MEDIA_FIRBASE_REF = "image_firebase_ref";
    public static String DATABASE_EVENT_MESSAGE_TUMBNAIL_FIRBASE_REF = "tumbnail_firebase_ref";
    public static String DATABASE_EVENT_MESSAGE_IMAGES = "message_images";
    public static String DATABASE_EVENT_IMAGE_ID = "image_id";
    public static String DATABASE_IMAGE_WIDTH = "image_width";
    public static String DATABASE_IMAGE_HEIGHT = "image_height";

// MESSAGES NOTIFICATIONS


    public static String DATABASE_EVENT_NOTIFICATIONS = "notifications";
    public static String DATABASE_EVENT_NOTIFICATION_TITLE = "notif_title";
    public static String DATABASE_EVENT_NOTIFICATION_BODY = "notif_body";
    public static String DATABASE_EVENT_NOTIFICATION_DEST_TOKKEN = "notif_dest_tokken";

// IMAGES USERS

    public static String DATABASE_TIMELINES = "timelines";
    public static String DATABASE_TIMELINES_IMAGES = "images";
    public static String DATABASE_TIMELINES_IMAGE_TIME_ADDED = "timestamp_added";
}

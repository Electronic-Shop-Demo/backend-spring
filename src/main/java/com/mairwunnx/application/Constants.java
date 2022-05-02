package com.mairwunnx.application;

public final class Constants {

    public static final class Errors {

        public static final int WTF_ERROR = -1;
        public static final int UUID_IS_INCORRECT = 1;
        public static final int ITEM_NOT_FOUND = 2;
        public static final int NO_EXTENSION = 3;
        public static final int NO_IMAGE_EXTENSION = 4;
        public static final int INTERNAL_FILE_SAVING_ERROR = 5;
        public static final int INTERNAL_BD_CONFIG_ERROR = 6;
        public static final int INTERNAL_COMPRESSING_IMAGE_ERROR = 7;
        public static final int UNSUPPORTED_IMAGE_EXTENSION = 8;
        public static final int IMAGE_NOT_FOUND = 9;
        public static final int USER_NOT_FOUND = 10;
        public static final int INCORRECT_PASSWORD = 11;
        public static final int USER_REGISTERED = 12;
        public static final int USER_INCORRECT_NAME = 13;
        public static final int USER_INCORRECT_PASSWORD = 14;
        public static final int USER_INCORRECT_EMAIL = 15;
        public static final int INTERNAL_USER_ID_NOT_REGISTERED = 16;
        public static final int INTERNAL_JWT_KEYSTORE_ERROR = 17;
        public static final int INTERNAL_JWT_SIGN_ERROR = 18;
        public static final int INTERNAL_JWT_VALIDATION_ERROR = 19;
        public static final int INVALID_JWT_TOKEN = 20;
        public static final int OUTDATED_JWT_TOKEN = 21;
        public static final int INTERNAL_JWT_REFRESH_ERROR = 22;
        public static final int INTERNAL_SMS_SEND_ERROR = 23;
        public static final int INTERNAL_SMS_SAVE_ERROR = 24;
        public static final int WRONG_CONFIRMATION_CODE = 25;
        public static final int USER_INCORRECT_PHONE = 26;

    }

    public static final class Files {

        public static final long COMPRESSION_SIZE_THRESHOLD = 80000;
        public static final float COMPRESSION_QUALITY = 0.3f;

    }

    public static final class Api {

        public static final String ENDPOINT_BASE = "/api/v1";

    }

    public static final class Jwt {

        public static final String CLAIM_USER_ID = "userId";
        public static final String CLAIM_USER_SCOPE = "scope";

        public static final class Authorities {

            public static final String USER = "user";
            public static final String ROOT = "root";

        }

    }

    public static final class Validations {

        public static final int USERNAME_MIN_LENGTH_THRESHOLD = 3;
        public static final int USERNAME_MAX_LENGTH_THRESHOLD = 18;
        public static final int USER_PASSWORD_MIN_LENGTH_THRESHOLD = 6;
        public static final int USER_PASSWORD_MAX_LENGTH_THRESHOLD = 32;
        public static final int USER_EMAIL_MIN_LENGTH_THRESHOLD = 5;
        public static final String[] DENIED_USERNAMES = {"admin", "administrator", "root"};

    }

    public static final class MongoDb {

        public static final String APP = "ElectronicShop";
        public static final String ID_NAME = "_id";
        public static final String DB = "shop";
        public static final String CONNECTION_STRING_REF = "mongodb_files_db_url";

        public static final class Collections {

            public static final String PRODUCT_IMAGES = "products_images";
            public static final String USERS = "users";

        }

        public static final class Authors {

            public static final String PAVEL_EROKHIN = "Pavel Erokhin";

        }

    }

    public static final class Redis {

        public static final String NULL = "nil";

    }

}

package cn.edu.thssdb.exception;


public class DuplicateDatabaseException extends RuntimeException{
        private String key;

        public DuplicateDatabaseException()
        {
            super();
            key = null;
        }

        public DuplicateDatabaseException(String key)
        {
            super();
            this.key = key;
        }
        @Override
        public String getMessage() {
            if (key == null)
                return "Exception: create database caused duplicated database!";
            else
                return "Exception: create database \"" + key + "\" caused duplicated database!";
        }
    }


package org.example.intuitetllapplication.model;

public enum SourceType {
    CSV_FILE,
    AWS_S3_CSV,
    DATABASE;

    public static SourceType fromInteger(int x) {
        switch (x) {
            case 0:
                return CSV_FILE;
            case 1:
                return AWS_S3_CSV;
            case 2:
                return DATABASE;
        }
        return null;
    }
}

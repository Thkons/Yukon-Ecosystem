package hos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileRecord {
    private final SimpleIntegerProperty fileId;
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty fileType;
    private final SimpleStringProperty uploadDate;

    public FileRecord(int fileId, String fileName, String fileType, String uploadDate) {
        this.fileId = new SimpleIntegerProperty(fileId);
        this.fileName = new SimpleStringProperty(fileName);
        this.fileType = new SimpleStringProperty(fileType);
        this.uploadDate = new SimpleStringProperty(uploadDate);
    }

    public int getFileId() {
        return fileId.get();
    }
    

    public String getFileName() {
        return fileName.get();
    }

    public String getFileType() {
        return fileType.get();
    }

    public String getUploadDate() {
        return uploadDate.get();
    }

    public SimpleIntegerProperty fileIdProperty() {
        return fileId;
    }


    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public SimpleStringProperty fileTypeProperty() {
        return fileType;
    }

    public SimpleStringProperty uploadDateProperty() {
        return uploadDate;
    }
}

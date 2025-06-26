package hos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Files {
    private final SimpleIntegerProperty fileId;
    private final SimpleIntegerProperty patientId;
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty fileType;
    private final SimpleStringProperty uploadDate;
    private final SimpleStringProperty filePath;

    public Files(int fileId, int patientId, String fileName, String fileType, String uploadDate, String filePath) {
        this.fileId = new SimpleIntegerProperty(fileId);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.fileName = new SimpleStringProperty(fileName);
        this.fileType = new SimpleStringProperty(fileType);
        this.uploadDate = new SimpleStringProperty(uploadDate);
        this.filePath = new SimpleStringProperty(filePath);
    }

    public int getFileId() {
        return fileId.get();
    }
    
    public int getPatientId() {
        return patientId.get();
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
    
     public String getFilePath() {
        return filePath.get();
    }

    public SimpleIntegerProperty fileIdProperty() {
        return fileId;
    }
    
    public SimpleIntegerProperty patientIdProperty() {
        return patientId;
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
    
    public SimpleStringProperty filePathProperty() {
        return filePath;
    }
}

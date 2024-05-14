package ch.zhaw.deeplearningjava;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class UploadToAzureBlobStorage {

    public static void main(String[] args) {
        String connectionString = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
        String containerName = "ulricdo1emotiondetection";
        String sourceDir = "models";

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        uploadFiles(containerClient, sourceDir);
    }

    private static void uploadFiles(BlobContainerClient containerClient, String sourceDir) {
        File dir = new File(sourceDir);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".params") || name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                BlobClient blobClient = containerClient.getBlobClient(file.getName());
                blobClient.uploadFromFile(file.getAbsolutePath(), true);
                System.out.println("Uploaded file: " + file.getName());
            }
        }
    }
}

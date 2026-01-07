package app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseConfig {

    public static void initialize() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("firebase-key.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("🔥 Firebase Connected Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

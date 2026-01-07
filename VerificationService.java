package app;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.awt.Toolkit;

public class VerificationService {

    public static void verifyWorker(String id, String name, String state) {

        Firestore db = FirestoreClient.getFirestore();

        try {
            var query = db.collection("workers")
                    .whereEqualTo("idNumber", id)
                    .whereEqualTo("fullName", name)
                    .whereEqualTo("migrantStateFrom", state)
                    .get()
                    .get();

            if (query.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("🚨 VERIFICATION FAILED");

                db.collection("police_alerts").add(
                        new java.util.HashMap<String, Object>() {{
                            put("idNumber", id);
                            put("name", name);
                            put("state", state);
                            put("reason", "Data mismatch");
                            put("timestamp", System.currentTimeMillis());
                        }}
                );

            } else {
                System.out.println("✅ VERIFIED SUCCESSFULLY");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

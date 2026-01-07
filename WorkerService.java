package app;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;

public class WorkerService {

    public static void registerWorker(
            String name,
            String id,
            String guardian,
            String district,
            String from,
            String to,
            String job,
            String skills
    ) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            Map<String, Object> worker = new HashMap<>();
            worker.put("fullName", name);
            worker.put("idNumber", id);
            worker.put("guardianName", guardian);
            worker.put("district", district);
            worker.put("migrantStateFrom", from);
            worker.put("destinationState", to);
            worker.put("jobTitle", job);
            worker.put("skillSet", Arrays.asList(skills.split(",")));
            worker.put("verified", true);
            worker.put("createdAt", System.currentTimeMillis());

            db.collection("workers").add(worker);

            System.out.println("✅ Worker Stored in Firebase");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

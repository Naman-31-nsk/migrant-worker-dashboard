package app;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;

public class LandlordService {

    public static void registerLandlord(
            String name,
            String house,
            String district,
            String state,
            String rent
    ) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            Map<String, Object> landlord = new HashMap<>();
            landlord.put("fullName", name);
            landlord.put("propertyNumber", house);
            landlord.put("district", district);
            landlord.put("state", state);
            landlord.put("monthlyRent", rent);
            landlord.put("createdAt", System.currentTimeMillis());

            db.collection("landlords").add(landlord);

            System.out.println("🏠 Landlord Stored in Firebase");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

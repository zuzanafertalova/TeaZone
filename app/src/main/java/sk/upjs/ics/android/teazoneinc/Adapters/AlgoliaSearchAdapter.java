package sk.upjs.ics.android.teazoneinc.Adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.algolia.search.saas.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma;
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser;

public class AlgoliaSearchAdapter {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Client client = new Client("085AYVSODT", "22c915636c1f40328cbb89a1da7a531a");
    Index index = client.getIndex("FirmaUsers_Users");

    public void transferCollectionToAlgolia(String collectionName){
        final ArrayList<DocumentSnapshot> list = new ArrayList<>();

        db.collection(collectionName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    index.addObjectAsync(new JSONObject()
                                            .put("objectID",document.getId())
                                            .put("email", document.getString("email"))
                                            .put("ico", document.getString("ico"))
                                            .put("username", document.getString("username")), null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addUserUserToAlgolia(DataUser map){
        try {
            index.addObjectAsync(new JSONObject()
                    .put("objectID",map.getDocID())
                    .put("username",map.getUsername())
                    .put("email", map.getEmail()), null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addFirmaUserToAlgolia(DataFirma map){
        try {
            index.addObjectAsync(new JSONObject()
                    .put("objectID",map.getDocID())
                    .put("email", map.getEmail())
                    .put("username",map.getUsername())
                    .put("typPodniku", map.getTypPodniku())
                    .put("ico", map.getICO()), null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void search(final String content){
        Query query = new Query(content)
                .setAttributesToRetrieve("username")
                .setHitsPerPage(50);
        index.searchAsync(query, new CompletionHandler() {
            @Override
            public void requestCompleted(@Nullable JSONObject jsonObject, @Nullable AlgoliaException e) {
                try {
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    setValuesToList(hits);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setValuesToList(JSONArray hits) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i =0; i<hits.length();i++){
            JSONObject jsonObject = hits.getJSONObject(i);
            String username = jsonObject.getString("username");
            list.add(username);
        }
    }

    public void updateUsername(String username, String objectID) throws JSONException, AlgoliaException {
        index.partialUpdateObjectAsync(new JSONObject().put("username",username), objectID,null);
    }

    public void deleteObject(String objectID) {
//        try {
//            RequestOptions requestOptions = new RequestOptions();
//            index.deleteObject(objectID,requestOptions);
//        }
//        catch (AlgoliaException e) {
//            e.printStackTrace();
//        }
    }



}

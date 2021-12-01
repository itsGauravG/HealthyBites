package com.example.healthybites.Partner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthybites.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddFoodItemActivity extends AppCompatActivity {

    ImageView img;
    Button upload;
    EditText itemname , itemprice , itemdesc;
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference("Partner");
    //private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth auth;
    private Uri imageUri ;
    private static String mobile,txt_itemname,txt_itemprice,txt_itemdesc;
    public static String[] child_email;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        Intent i = getIntent();

        img = findViewById(R.id.itemimg);
        upload = findViewById(R.id.food_upld);
        itemname = findViewById(R.id.food_name);
        itemprice = findViewById(R.id.food_price);
        itemdesc = findViewById(R.id.food_desc);
        auth = FirebaseAuth.getInstance();
        mobile = i.getStringExtra("mobile");





        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);



            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_itemname = itemname.getText().toString();
                txt_itemprice = itemprice.getText().toString();
                txt_itemdesc = itemdesc.getText().toString();
                String email = auth.getCurrentUser().getEmail();
                child_email = email.split("@",5);


                if(imageUri!=null && !(txt_itemname.isEmpty()||txt_itemprice.isEmpty()||txt_itemdesc.isEmpty()) ){
                    uploadtoFirebase(imageUri);

                }

                else {
                    Toast.makeText(AddFoodItemActivity.this,"Please select an image or fill all the fields ",Toast.LENGTH_SHORT).show();
                }

            }
    });

  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            img.setImageURI(imageUri);




        }
    }
    final String timestamp= ""+System.currentTimeMillis();

    private void uploadtoFirebase(Uri uri){


        String filepath = "foodimage/"+""+timestamp;
        StorageReference reference = FirebaseStorage.getInstance().getReference(filepath);
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri downloadImageUri = uriTask.getResult();

                if(uriTask.isSuccessful()){
                    HashMap<String,Object> hashMap = new HashMap<>();

                    hashMap.put("imageUrl",""+downloadImageUri);
                    hashMap.put("itemName",txt_itemname);
                    hashMap.put("itemPrice",txt_itemprice);
                    hashMap.put("itemDesc",txt_itemdesc);

                    root.child(child_email[0]).child("Items").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddFoodItemActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
        });


    }

}